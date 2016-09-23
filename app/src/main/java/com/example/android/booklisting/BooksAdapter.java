package com.example.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 9/23/2016.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView author;
    }

    public BooksAdapter(Context context, ArrayList<Books> books){
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Books currentBook = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_items, parent, false);
                        /*Find the TextView and ImageView and set them on the VIewHolder*/
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.author = (TextView) convertView.findViewById(R.id.author);
//            /* Check if the current position list item has a image, and if has then set the
//            * visibility to True otherwise make it disapper using View.GONE
//            */
//            if (currentBook.hasImage()) {
//                viewHolder.author.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.author.setVisibility(View.GONE);
//            }


            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.title.setText(currentBook.getTitle());
        viewHolder.author.setText(currentBook.getAuthor());
        return convertView;
    }
}
