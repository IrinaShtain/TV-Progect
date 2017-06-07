package com.shtainyky.tvproject.data.models.star;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Bell on 02.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class StarItem implements Parcelable {
    public String profile_path;
    public String name;
    public float popularity;
    public int id;
    public ArrayList<FamousForItem> known_for;

    @JsonIgnore
    public String avatarUrl;
    @OnJsonParseComplete
    void onParseComplete() {
        avatarUrl = Constants.IMAGE_BASE + profile_path;
    }

    public StarItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profile_path);
        dest.writeString(this.name);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.id);
        dest.writeTypedList(this.known_for);
        dest.writeString(this.avatarUrl);
    }

    protected StarItem(Parcel in) {
        this.profile_path = in.readString();
        this.name = in.readString();
        this.popularity = in.readFloat();
        this.id = in.readInt();
        this.known_for = in.createTypedArrayList(FamousForItem.CREATOR);
        this.avatarUrl = in.readString();
    }

    public static final Creator<StarItem> CREATOR = new Creator<StarItem>() {
        @Override
        public StarItem createFromParcel(Parcel source) {
            return new StarItem(source);
        }

        @Override
        public StarItem[] newArray(int size) {
            return new StarItem[size];
        }
    };
}
