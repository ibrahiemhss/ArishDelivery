package com.delivery.arish.arishdelivery.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsModel implements Parcelable {

   private String name;
    private int image;

    public DetailsModel() {
    }

    protected DetailsModel(Parcel in) {
        name = in.readString();
        image = in.readInt();
    }

    public static final Creator<DetailsModel> CREATOR = new Creator<DetailsModel>() {
        @Override
        public DetailsModel createFromParcel(Parcel in) {
            return new DetailsModel(in);
        }

        @Override
        public DetailsModel[] newArray(int size) {
            return new DetailsModel[size];
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
