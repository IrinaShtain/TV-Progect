package com.shtainyky.tvproject.data.models.star;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.shtainyky.tvproject.utils.Constants;

/**
 * Created by Bell on 02.06.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FamousForItem implements Parcelable {
    public String poster_path;
    public String media_type;

    @JsonField(name = "title")
    public String movie_title;
    public String release_date;

    @JsonField(name = "name")
    public String tv_name;
    public String first_air_date;

    @JsonIgnore
    public String avatarUrl;
    @OnJsonParseComplete
    void onParseComplete() {
        avatarUrl = Constants.IMAGE_BASE + poster_path;
    }


    public FamousForItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeString(this.media_type);
        dest.writeString(this.movie_title);
        dest.writeString(this.release_date);
        dest.writeString(this.tv_name);
        dest.writeString(this.first_air_date);
        dest.writeString(this.avatarUrl);
    }

    protected FamousForItem(Parcel in) {
        this.poster_path = in.readString();
        this.media_type = in.readString();
        this.movie_title = in.readString();
        this.release_date = in.readString();
        this.tv_name = in.readString();
        this.first_air_date = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Creator<FamousForItem> CREATOR = new Creator<FamousForItem>() {
        @Override
        public FamousForItem createFromParcel(Parcel source) {
            return new FamousForItem(source);
        }

        @Override
        public FamousForItem[] newArray(int size) {
            return new FamousForItem[size];
        }
    };
}
