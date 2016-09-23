package com.example.android.booklisting;

/**
 * Created by ABHISHEK RAJ on 9/22/2016.
 */

public class Books {
    /** Title of the earthquake event */
    private final String title;

    /** Time that the earthquake happened (in milliseconds) */
    private final String author;

        /**
     * Constructs a new {@link Books}.
     *
     * @param eventTitle is the title of the earthquake event
     * @param eventAuthor is the time the earhtquake happened
     */
    public Books(String eventTitle, String eventAuthor) {
        title = eventTitle;
        author = eventAuthor;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
}