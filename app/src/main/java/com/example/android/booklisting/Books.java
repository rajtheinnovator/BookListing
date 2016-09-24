package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 9/22/2016.
 */

public class Books implements Parcelable {
    /**Title of the book*/
    private final String mTitle;

    /* author of the book*/
    private final String mAuthor;

    /*publisher of the book*/
    private final String mPublisher;

//    /*Variables for handling list view click*/
//    private final String mPageCount;
//    private final String mDescription;

    /**
     * Constructs a new {@link Books}.
     *
     * @param bookTitle  is the title of the book
     * @param bookAuthor is the author
     * @param publisher  is the publisher of the book
     */
    public Books(String bookTitle, String bookAuthor, String publisher) {
        mTitle = bookTitle;
        mAuthor = bookAuthor;
        mPublisher = publisher;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mAuthor);
        out.writeString(mPublisher);
    }
    private Books(Parcel in){
        mTitle = in.readString();
        mAuthor = in.readString();
        mPublisher = in.readString();
    }
    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Books> CREATOR
            = new Parcelable.Creator<Books>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };
}