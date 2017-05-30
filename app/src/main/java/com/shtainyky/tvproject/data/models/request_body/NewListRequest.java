package com.shtainyky.tvproject.data.models.request_body;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 30.05.2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class NewListRequest implements Parcelable {
    public String name;
    public String description;

    public NewListRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    public NewListRequest() {
    }

    protected NewListRequest(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<NewListRequest> CREATOR = new Parcelable.Creator<NewListRequest>() {
        @Override
        public NewListRequest createFromParcel(Parcel source) {
            return new NewListRequest(source);
        }

        @Override
        public NewListRequest[] newArray(int size) {
            return new NewListRequest[size];
        }
    };
}
