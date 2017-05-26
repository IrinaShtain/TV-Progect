package com.shtainyky.tvproject.data.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Bell on 25.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ListResponse implements Parcelable {
    @JsonField(name = "results")
    public ArrayList<ListItem> mListItems;
    public int page;
    public int total_pages;
    public int total_results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mListItems);
        dest.writeInt(this.page);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
    }

    public ListResponse() {
    }

    protected ListResponse(Parcel in) {
        this.mListItems = in.createTypedArrayList(ListItem.CREATOR);
        this.page = in.readInt();
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
    }

    public static final Parcelable.Creator<ListResponse> CREATOR = new Parcelable.Creator<ListResponse>() {
        @Override
        public ListResponse createFromParcel(Parcel source) {
            return new ListResponse(source);
        }

        @Override
        public ListResponse[] newArray(int size) {
            return new ListResponse[size];
        }
    };
}
