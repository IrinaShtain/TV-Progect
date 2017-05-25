package com.shtainyky.tvproject.data.models.base;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Bell on 24.05.2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class GetResponse<T> {

    public T data;

}
