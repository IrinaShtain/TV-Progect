package com.shtainyky.tvproject.data.models.star;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Bell on 02.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class StarResponse implements Parcelable {
    @JsonField(name = "results")
    public ArrayList<StarItem> stars;
    public int total_pages;
    public int total_results;
    public int page;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.stars);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
        dest.writeInt(this.page);
    }

    public StarResponse() {
    }

    protected StarResponse(Parcel in) {
        this.stars = in.createTypedArrayList(StarItem.CREATOR);
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<StarResponse> CREATOR = new Parcelable.Creator<StarResponse>() {
        @Override
        public StarResponse createFromParcel(Parcel source) {
            return new StarResponse(source);
        }

        @Override
        public StarResponse[] newArray(int size) {
            return new StarResponse[size];
        }
    };
}
