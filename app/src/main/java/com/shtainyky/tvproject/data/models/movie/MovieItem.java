package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Bell on 26.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class MovieItem implements Parcelable {
    public int id;
    public int vote_count;
    public float popularity;
    public String title;
    public String release_date;
    public String overview;
    public String poster_path;
    public String media_type;
    public ArrayList<Integer> genre_ids;

    @JsonIgnore
    public String genres;

    @JsonIgnore
    public String avatarUrl;

    public MovieItem() {
    }

    @OnJsonParseComplete
    void onParseComplete() {
        avatarUrl = Constants.IMAGE_BASE + poster_path;
    }


    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.vote_count);
        dest.writeFloat(this.popularity);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.media_type);
        dest.writeList(this.genre_ids);
    }

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.popularity = in.readFloat();
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.media_type = in.readString();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
