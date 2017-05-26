package com.shtainyky.tvproject.data.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by Bell on 26.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CreatedListsData implements Parcelable {
    @JsonField(name = "results")
    public ArrayList<ListItem> lists;
    public int total_pages;
    public int total_results;
    public int page;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.lists);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
        dest.writeInt(this.page);
    }

    public CreatedListsData() {
    }

    protected CreatedListsData(Parcel in) {
        this.lists = in.createTypedArrayList(ListItem.CREATOR);
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<CreatedListsData> CREATOR = new Parcelable.Creator<CreatedListsData>() {
        @Override
        public CreatedListsData createFromParcel(Parcel source) {
            return new CreatedListsData(source);
        }

        @Override
        public CreatedListsData[] newArray(int size) {
            return new CreatedListsData[size];
        }
    };
}
