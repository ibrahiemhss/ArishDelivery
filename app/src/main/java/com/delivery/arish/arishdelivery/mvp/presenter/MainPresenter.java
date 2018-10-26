package com.delivery.arish.arishdelivery.mvp.presenter;

import android.content.Context;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;

public class MainPresenter {



    public static ArrayList<MainModel> getMainModel(Context context)
    {


         final String names_values[] = {
                 context.getString(R.string.restaurant),
                 context.getString(R.string.cafes),
                 context.getString(R.string.pharmacies),

        };

         final int images_values[] = {
                 R.drawable.restaurant,
                 R.drawable.cafes,
                 R.drawable.pharmacies
        };
        ArrayList<MainModel> mainModelArrayList=new ArrayList<>();


        for(int i=0;i<names_values.length;i++){
            MainModel s=new MainModel();
            s.setName(names_values[i]);
            s.setImage(images_values[i]);
            mainModelArrayList.add(s);
        }
        return mainModelArrayList;
    }
}
