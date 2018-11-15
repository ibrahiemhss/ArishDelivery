package com.delivery.arish.arishdelivery.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryModel implements Parcelable {

    private String ar_name, en_name, id, image_url;

    public CategoryModel() {
    }

    @SuppressWarnings("unused")
    protected CategoryModel(Parcel in) {

        ar_name = in.readString();
        en_name = in.readString();
        id = in.readString();
        image_url = in.readString();
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ar_name);
        parcel.writeString(en_name);
        parcel.writeString(id);
        parcel.writeString(image_url);
    }
}
