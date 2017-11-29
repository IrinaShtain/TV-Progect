package com.shtainyky.tvproject.data.models.request_body;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Irina Shtain on 29.11.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class RateRequest implements Parcelable {

    public float value;

    public RateRequest(float media_id) {
        this.value = media_id;
    }

    public RateRequest() {
    }

    protected RateRequest(Parcel in) {
        value = in.readLong();
    }

    public static final Creator<RateRequest> CREATOR = new Creator<RateRequest>() {
        @Override
        public RateRequest createFromParcel(Parcel in) {
            return new RateRequest(in);
        }

        @Override
        public RateRequest[] newArray(int size) {
            return new RateRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(value);
    }
}
