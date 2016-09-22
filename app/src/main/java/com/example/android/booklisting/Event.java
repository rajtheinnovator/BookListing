package com.example.android.booklisting;

/**
 * Created by ABHISHEK RAJ on 9/22/2016.
 */

public class Event {
    /** Title of the earthquake event */
    public final String title;

    /** Time that the earthquake happened (in milliseconds) */
    public final String author;

        /**
     * Constructs a new {@link Event}.
     *
     * @param eventTitle is the title of the earthquake event
     * @param eventAuthor is the time the earhtquake happened
     */
    public Event(String eventTitle, String eventAuthor) {
        title = eventTitle;
        author = eventAuthor;
    }
}
