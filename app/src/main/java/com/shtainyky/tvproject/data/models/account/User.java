package com.shtainyky.tvproject.data.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 24.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class User implements Parcelable, Cloneable {

    public int id;
    public String name;
    public String username;
    @JsonField(name = "iso_639_1")
    public String language;
    public boolean include_adult;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.language);
        dest.writeByte(this.include_adult ? (byte) 1 : (byte) 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.username = in.readString();
        this.language = in.readString();
        this.include_adult = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User clone() {
        User clone = new User();

        clone.id = this.id;
        clone.name = this.name;
        clone.username = this.username;
        clone.language = this.language;
        clone.include_adult = this.include_adult;

        return clone;
    }
}
