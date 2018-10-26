package com.delivery.arish.arishdelivery.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MainModel implements Parcelable{

    private String name;
    private int image;
    public MainModel() {

    }
    public MainModel(Parcel in) {
        name = in.readString();
        image = in.readInt();
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(image);
    }
}