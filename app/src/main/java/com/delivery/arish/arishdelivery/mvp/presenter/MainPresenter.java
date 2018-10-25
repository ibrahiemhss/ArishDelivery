package com.delivery.arish.arishdelivery.mvp.presenter;

import android.content.Context;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;

public class MainPresenter {

    public static ArrayList<MainModel> getMainModel(Context context)
    {
        ArrayList<MainModel> spaceships=new ArrayList<>();

        MainModel s=new MainModel();
        s.setName(context.getString(R.string.restaurant));
        s.setImage(R.drawable.restaurant);
        spaceships.add(s);

        s=new MainModel();
        s.setName(context.getString(R.string.cafes));
        s.setImage(R.drawable.cafes);
        spaceships.add(s);

        s=new MainModel();
        s.setName(context.getString(R.string.pharmacies));
        s.setImage(R.drawable.pharmacies);
        spaceships.add(s);

        return spaceships;
    }
}
