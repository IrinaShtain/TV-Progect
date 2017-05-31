package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Bell on 30.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SearchMovieResponse implements Parcelable {
    @JsonField(name = "results")
    public ArrayList<MovieItem> movies;
    public int total_pages;
    public int total_results;
    public int page;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movies);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
        dest.writeInt(this.page);
    }

    public SearchMovieResponse() {
    }

    protected SearchMovieResponse(Parcel in) {
        this.movies = in.createTypedArrayList(MovieItem.CREATOR);
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<SearchMovieResponse> CREATOR = new Parcelable.Creator<SearchMovieResponse>() {
        @Override
        public SearchMovieResponse createFromParcel(Parcel source) {
            return new SearchMovieResponse(source);
        }

        @Override
        public SearchMovieResponse[] newArray(int size) {
            return new SearchMovieResponse[size];
        }
    };
}
