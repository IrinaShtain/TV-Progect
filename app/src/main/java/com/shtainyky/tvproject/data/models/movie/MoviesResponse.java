package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.shtainyky.tvproject.data.models.account.ListItem;

import java.util.ArrayList;

/**
 * Created by Bell on 29.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class MoviesResponse implements Parcelable {
    @JsonField(name = "items")
    public ArrayList<MovieItem> movies;
    public String name;
    public String description;
    public String id;
    public String item_count;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movies);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.id);
        dest.writeString(this.item_count);
    }

    public MoviesResponse() {
    }

    protected MoviesResponse(Parcel in) {
        this.movies = in.createTypedArrayList(MovieItem.CREATOR);
        this.name = in.readString();
        this.description = in.readString();
        this.id = in.readString();
        this.item_count = in.readString();
    }

    public static final Parcelable.Creator<MoviesResponse> CREATOR = new Parcelable.Creator<MoviesResponse>() {
        @Override
        public MoviesResponse createFromParcel(Parcel source) {
            return new MoviesResponse(source);
        }

        @Override
        public MoviesResponse[] newArray(int size) {
            return new MoviesResponse[size];
        }
    };
}
