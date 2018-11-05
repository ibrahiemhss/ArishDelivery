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

    @SerializedName(Contract.NAME_COL)
    private
    String name;
    @SerializedName(Contract.EMAIL_COL)
    private
    String email;
    @SerializedName(Contract.PHONE_COL)
    private
    String phone;
    @SerializedName(Contract.IMAGE_COL)
    private
    String img_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

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