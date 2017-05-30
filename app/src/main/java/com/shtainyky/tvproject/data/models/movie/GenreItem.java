package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 26.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class GenreItem implements Parcelable {
    public int id;
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public GenreItem() {
    }

    protected GenreItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<GenreItem> CREATOR = new Parcelable.Creator<GenreItem>() {
        @Override
        public GenreItem createFromParcel(Parcel source) {
            return new GenreItem(source);
        }

        @Override
        public GenreItem[] newArray(int size) {
            return new GenreItem[size];
        }
    };
}
