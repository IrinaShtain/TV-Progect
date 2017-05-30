package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Bell on 29.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class GenresResponse implements Parcelable {
    public ArrayList<GenreItem> genres;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.genres);
    }

    public GenresResponse() {
    }

    protected GenresResponse(Parcel in) {
        this.genres = in.createTypedArrayList(GenreItem.CREATOR);
    }

    public static final Parcelable.Creator<GenresResponse> CREATOR = new Parcelable.Creator<GenresResponse>() {
        @Override
        public GenresResponse createFromParcel(Parcel source) {
            return new GenresResponse(source);
        }

        @Override
        public GenresResponse[] newArray(int size) {
            return new GenresResponse[size];
        }
    };
}
