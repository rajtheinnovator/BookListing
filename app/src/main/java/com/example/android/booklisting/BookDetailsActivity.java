package com.example.android.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 9/24/2016.
 */

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_listview);
        Bundle bundle ;
         bundle = getIntent().getBundleExtra("booksObjectBundle");
        BookDetailsAdapter bookDetailsAdapter = new BookDetailsAdapter(this, bundle);
        ListView listView = (ListView) findViewById(R.id.book_details_listview);
        listView.setAdapter(bookDetailsAdapter);
    }

//    Bundle bundle = getArguments();
//
//    if ((bundle != null)) {
//        ArrayList<CityDetails> cityDetail = getArguments().getParcelableArrayList("cityDetails");
//
//        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
//        // adapter knows how to create list items for each item in the list.
//        CityInformationAdapter adapter = new CityInformationAdapter(getActivity(), cityDetail);
//
//        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
//        // There should be a {@link ListView} with the view ID called list, which is declared in the
//        // word_list.xml layout file.
//        ListView listView = (ListView) rootView.findViewById(R.id.city_list);
//
//        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
//        // {@link ListView} will display list items for each {@link Word} in the list.
//        listView.setAdapter(adapter);
//    }
}
