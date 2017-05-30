package com.shtainyky.tvproject.data.models.request_body;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 29.05.2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ActionRequest implements Parcelable {

    public int media_id;

    public ActionRequest(int media_id) {
        this.media_id = media_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.media_id);
    }

    public ActionRequest() {
    }

    protected ActionRequest(Parcel in) {
        this.media_id = in.readInt();
    }

    public static final Parcelable.Creator<ActionRequest> CREATOR = new Parcelable.Creator<ActionRequest>() {
        @Override
        public ActionRequest createFromParcel(Parcel source) {
            return new ActionRequest(source);
        }

        @Override
        public ActionRequest[] newArray(int size) {
            return new ActionRequest[size];
        }
    };
}
