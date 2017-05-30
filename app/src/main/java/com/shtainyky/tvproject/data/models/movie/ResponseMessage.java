package com.shtainyky.tvproject.data.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 29.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class ResponseMessage implements Parcelable {
    public int status_code;
    public String status_message;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status_code);
        dest.writeString(this.status_message);
    }

    public ResponseMessage() {
    }

    protected ResponseMessage(Parcel in) {
        this.status_code = in.readInt();
        this.status_message = in.readString();
    }

    public static final Parcelable.Creator<ResponseMessage> CREATOR = new Parcelable.Creator<ResponseMessage>() {
        @Override
        public ResponseMessage createFromParcel(Parcel source) {
            return new ResponseMessage(source);
        }

        @Override
        public ResponseMessage[] newArray(int size) {
            return new ResponseMessage[size];
        }
    };
}
