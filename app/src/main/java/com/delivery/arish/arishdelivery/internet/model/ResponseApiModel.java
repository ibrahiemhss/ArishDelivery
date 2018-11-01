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
    @SerializedName(Contract.SUCESS_MSG)
    String success_msg;
    @SerializedName(Contract.IMG_MSG)
    String image_msg;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @SerializedName(Contract.LANG_COL)

    String lang;

    public String getImage_msg() {
        return image_msg;
    }

    public void setImage_msg(String image_msg) {
        this.image_msg = image_msg;
    }

    public String getSuccess_msg() {
        return success_msg;
    }

    public void setSuccess_msg(String success_msg) {
        this.success_msg = success_msg;
    }

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