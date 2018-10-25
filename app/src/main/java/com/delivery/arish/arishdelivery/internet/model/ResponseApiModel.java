package com.delivery.arish.arishdelivery.internet.model;

import com.delivery.arish.arishdelivery.data.Contract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ibrahim on 21/01/18.
 */

public class ResponseApiModel {
    @SerializedName(Contract.ERROR)
    String error;
    @SerializedName(Contract.ERROR_MSG)
    String error_msg;

    public String getEerror() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}