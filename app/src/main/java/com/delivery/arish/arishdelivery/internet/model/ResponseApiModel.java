package com.delivery.arish.arishdelivery.internet.model;

import com.delivery.arish.arishdelivery.data.Contract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ibrahim on 21/01/18.
 */

@SuppressWarnings("unused")
public class ResponseApiModel {
    @SerializedName(Contract.ERROR)
    private
    String error;
    @SerializedName(Contract.ERROR_MSG)
    private
    String error_msg;
    @SerializedName(Contract.SUCCESS_MSG)
    private
    String success_msg;

    public String getError() {
        return error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public String getSuccess_msg() {
        return success_msg;
    }

    @SerializedName(Contract.IMG_MSG)

    private
    String image_msg;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @SerializedName(Contract.LANG_COL)
    private

    String lang;

    public String getImage_msg() {
        return image_msg;
    }


}