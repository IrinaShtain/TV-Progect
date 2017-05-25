package com.shtainyky.tvproject.data.models.loginization;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 24.05.2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class LoginSession implements Parcelable {

    @JsonField(name = "session_id")
    public String sessionID;
    @JsonField(name = "success")
    public boolean success;


    public LoginSession() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sessionID);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);

    }

    protected LoginSession(Parcel in) {
        this.sessionID = in.readString();
        this.success = in.readByte() != 0;

    }

    public static final Creator<LoginToken> CREATOR = new Creator<LoginToken>() {
        @Override
        public LoginToken createFromParcel(Parcel source) {
            return new LoginToken(source);
        }

        @Override
        public LoginToken[] newArray(int size) {
            return new LoginToken[size];
        }
    };
}
