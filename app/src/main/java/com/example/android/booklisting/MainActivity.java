package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchText;
    private String searchQuery;
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * URL to query the GoogleBook dataset for book's information
     */
    private static String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    //http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-12-01&minmagnitude=7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Find the edit text's actual text and make it compatible for a url search query
                searchQuery = ((EditText) findViewById(R.id.searchQuery)).getText().toString().replace(" ", "+");

                //Check if user input is empty or it contains some query text
                if (searchQuery.isEmpty()) {
                    Context context = getApplicationContext();
                    String text = "Nothing Entered in Search";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    // String to be attached to the BOOK_REQUEST_URL
                    String appendableQuery = searchQuery + "&key=AIzaSyAN16J8Zakn2RQbFV4iBB8JrFPnFIev2wE&maxResults=10&country=IN";
                    BOOK_REQUEST_URL += appendableQuery; //final value of "URL for Google Book API"
                    BookAsyncTask task = new BookAsyncTask();
                    //If network is available then perform the further task of AsynckTask calling
                    if (isNetworkAvailable()) {
                        // Kick off an {@link AsyncTask} to perform the network request
                        task.execute();
                    } else {
                        Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Check if network is available or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Update the screen to display information from the given {@link Books}.
     */
    private void updateUi(Books book) {
        final ArrayList<Books> books = new ArrayList<Books>();
        books.add(new Books(book.getTitle(), book.getAuthor()));
        ListView listView = (ListView) findViewById(R.id.list);
        BooksAdapter booksAdapter = new BooksAdapter(MainActivity.this, books);
        listView.setAdapter(booksAdapter);
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    private class BookAsyncTask extends AsyncTask<URL, Void, Books> {

        @Override
        protected Books doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(BOOK_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Books} object
            Books book = extractFeatureFromJson(jsonResponse);

            // Return the {@link Books} object as the result fo the {@link BookAsyncTask}
            return book;
        }

        /**
         * Update the screen with the given book (which was the result of the
         * {@link BookAsyncTask}).
         */
        @Override
        protected void onPostExecute(Books book) {
            if (book == null) {
                return;
            }
            updateUi(book);
            EditText editText = (EditText) findViewById(R.id.searchQuery);
            editText.setText(null);
            BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";  //Reset the to the original URL
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e("LOG_TAG", "Error Response Code: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e("IOException :", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Books} object by parsing out information
         * about the first earthquake from the input googleBooksJSON string.
         */
        private Books extractFeatureFromJson(String googleBooksJSON) {
            try {
                Log.e(LOG_TAG, googleBooksJSON);
                JSONObject baseJsonResponse = new JSONObject(googleBooksJSON);
                JSONArray itemsArray = baseJsonResponse.getJSONArray("items");
                if (itemsArray.length() > 0) {
                    JSONObject volumeInfo = itemsArray.getJSONObject(0).getJSONObject("volumeInfo");
//                    Log.v(LOG_TAG, "the volumeInfo is: " + volumeInfo.toString());
                    String title = volumeInfo.getString("title");
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    if (authors.length() > 0) {
                        String firstAuthor = authors.getString(0);

                        // Create a new {@link Books} object
                        return new Books(title, firstAuthor);
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the Google Books JSON results", e);
            }
            return null;
        }
    }
}
