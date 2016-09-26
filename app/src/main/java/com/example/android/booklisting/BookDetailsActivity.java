package com.example.android.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        Books book = getIntent().getParcelableExtra("booksObjectBundle");
        ArrayList<Books> books = new ArrayList<Books>();
        books.add(new Books(book.getTitle(), book.getAuthor(), book.getPublisher(),
                book.getPageCount(), book.getDescription(), book.getRatings(),
                book.getPublishedDate(), book.getISBNType(), book.getISBNValue(), book.getBookImageResourceURL()));

        BookDetailsAdapter bookDetailsAdapter = new BookDetailsAdapter(BookDetailsActivity.this, books);
        ListView listView = (ListView) findViewById(R.id.book_details_listview);
        this.setTitle(book.getTitle());
        listView.setAdapter(bookDetailsAdapter);
    }
}
