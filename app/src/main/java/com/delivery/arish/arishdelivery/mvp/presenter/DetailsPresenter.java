package com.delivery.arish.arishdelivery.mvp.presenter;

import android.content.Context;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;

public class DetailsPresenter {

    public static ArrayList<DetailsModel> getDetailsModel(Context context)
    {


        final String names_values[] = {
                "restaurant1",
                "restaurant2",
                "restaurant3",
                "restaurant4",
                "restaurant5",
                "restaurant6",
                "restaurant7",
                "restaurant8"
        };

        final int images_values[] = {
                R.drawable.menu,
                R.drawable.menu1,
                R.drawable.menu2,
                R.drawable.menu3,
                R.drawable.menu4,
                R.drawable.menu5,
                R.drawable.menu6,
                R.drawable.menu8,
        };
        ArrayList<DetailsModel> detailsModelArrayList=new ArrayList<>();


        for(int i=0;i<names_values.length;i++){
            DetailsModel s=new DetailsModel();
            s.setName(names_values[i]);
            s.setImage(images_values[i]);
            detailsModelArrayList.add(s);
        }
        return detailsModelArrayList;
    }
}
