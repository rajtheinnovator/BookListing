package com.example.android.booklisting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 9/25/2016.
 */

public class BookDetailsAdapter extends ArrayAdapter<Books> {


    public BookDetailsAdapter(Context context, ArrayList<Books> bookses) {
        super(context, 0, bookses);
    }
//    public BookDetailsAdapter(Activity context, Books bookses) {
//         super(context, 0, bookses);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_details_items, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Books currentBook = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView title_book = (TextView) listItemView.findViewById(R.id.title_book);
        TextView author_book = (TextView) listItemView.findViewById(R.id.author_book);
        TextView publisher_book = (TextView) listItemView.findViewById(R.id.publisher_book);
        TextView page_count_book = (TextView) listItemView.findViewById(R.id.page_count_book);
        TextView description_book = (TextView) listItemView.findViewById(R.id.description_book);
        TextView ratings_book = (TextView) listItemView.findViewById(R.id.ratings_book);
        TextView publication_date_book = (TextView) listItemView.findViewById(R.id.publication_date_book);
        TextView isbn_type_book = (TextView) listItemView.findViewById(R.id.isbn_type_book);
        TextView isbn_value_book = (TextView) listItemView.findViewById(R.id.isbn_value_book);
        ImageView bookImageView = (ImageView) listItemView.findViewById(R.id.book_image);

             // set this text on these TextViews
        //bookImageView.setImageBitmap();
        title_book.setText(currentBook.getTitle());
        author_book.setText(currentBook.getAuthor());
        publisher_book.setText(currentBook.getPublisher());
        page_count_book.setText(currentBook.getPageCount());
        description_book.setText(currentBook.getDescription());
        ratings_book.setText((String.valueOf(currentBook.getRatings())));
        publication_date_book.setText(currentBook.getPublishedDate());
        isbn_type_book.setText(currentBook.getISBNType());
        isbn_value_book.setText(currentBook.getISBNValue());

        return listItemView;
    }
}
