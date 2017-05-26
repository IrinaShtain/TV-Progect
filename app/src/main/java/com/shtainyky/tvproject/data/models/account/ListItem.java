package com.shtainyky.tvproject.data.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 25.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ListItem implements Parcelable {
    public int id;
    public int item_count;
    public int favorite_count;
    public String description;
    public String name;
    public String list_type;
    @JsonField(name = "iso_639_1")
    public String language;
    public String poster_path;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.item_count);
        dest.writeInt(this.favorite_count);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeString(this.list_type);
        dest.writeString(this.language);
        dest.writeString(this.poster_path);
    }

    public ListItem() {
    }

    protected ListItem(Parcel in) {
        this.id = in.readInt();
        this.item_count = in.readInt();
        this.favorite_count = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
        this.list_type = in.readString();
        this.language = in.readString();
        this.poster_path = in.readString();
    }

    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel source) {
            return new ListItem(source);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
