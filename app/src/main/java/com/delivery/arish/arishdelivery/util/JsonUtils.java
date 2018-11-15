package com.delivery.arish.arishdelivery.util;

import android.util.Log;

import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.model.CategoryModel;
import com.delivery.arish.arishdelivery.mvp.model.RestaurantModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {


    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<RestaurantModel> parseRestaurantModel(String json, String sort) {
        ArrayList<RestaurantModel> detailsModels = new ArrayList<>();

        try {
            JSONObject item_jsonOpj = new JSONObject(json);


    /*
           make JSONArray that get the value of sort[]
           from item_jsonOpj
*/
            JSONArray jsonArray_sorts = item_jsonOpj.getJSONArray(sort);

            if (jsonArray_sorts != null) {
                detailsModels.clear();
                for (int i = 0; i < jsonArray_sorts.length(); i++) {
                    RestaurantModel detailsModel = new RestaurantModel();
                    JSONObject json_data = jsonArray_sorts.getJSONObject(i);

                    detailsModel.setId(json_data.getString(Contract.ID_COL));
                    detailsModel.setAr_name(json_data.getString(Contract.AR_NAME_COL));
                    detailsModel.setEn_name(json_data.getString(Contract.EN_NAME_COL));
                    detailsModel.setImage_url(json_data.getString(Contract.IMAGE_COL));
                    detailsModels.add(detailsModel);


                }
            }
            Log.v(TAG, "MYJSON" + item_jsonOpj.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detailsModels;
    }

    public static ArrayList<CategoryModel> parseCategoryModels(String json, String sort) {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        try {
            JSONObject item_jsonOpj = new JSONObject(json);


    /*
           make JSONArray that get the value of sort[]
           from item_jsonOpj
*/
            JSONArray jsonArray_sorts = item_jsonOpj.getJSONArray(sort);

            if (jsonArray_sorts != null) {
                categoryModels.clear();
                for (int i = 0; i < jsonArray_sorts.length(); i++) {
                    CategoryModel categoryModel = new CategoryModel();
                    JSONObject json_data = jsonArray_sorts.getJSONObject(i);

                    categoryModel.setId(json_data.getString(Contract.ID_COL));
                    categoryModel.setAr_name(json_data.getString(Contract.AR_NAME_COL));
                    categoryModel.setEn_name(json_data.getString(Contract.EN_NAME_COL));
                    categoryModel.setImage_url(json_data.getString(Contract.IMAGE_COL));
                    categoryModels.add(categoryModel);


                }
            }
            Log.v(TAG, "MYJSON" + item_jsonOpj.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryModels;
    }

}