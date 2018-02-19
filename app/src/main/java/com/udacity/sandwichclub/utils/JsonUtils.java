package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json,Context context) {

        JSONObject jObject;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        String mainName = null;
        JSONArray alsoKnownAs = null;
        JSONArray ingredients = null;

        try {
            jObject = new JSONObject(json);
            placeOfOrigin = jObject.optString(context.getString(R.string.key_place_of_origin));
            description = jObject.optString(context.getString(R.string.key_description));
            image = jObject.optString(context.getString(R.string.key_image));
            JSONObject name = jObject.optJSONObject(context.getString(R.string.key_name));
            mainName = name.optString(context.getString(R.string.key_main_name));
            alsoKnownAs = name.optJSONArray(context.getString(R.string.key_alsoknownas));
            ingredients = jObject.optJSONArray(context.getString(R.string.key_ingredients));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> knownAs = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();

        if (alsoKnownAs !=null) {
            for (int i=0; i < alsoKnownAs.length() ; i++) {
                try {
                    knownAs.add(alsoKnownAs.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (ingredients !=null) {
            for (int i=0; i < ingredients.length() ; i++) {
                try {
                    ingredientsList.add(ingredients.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Sandwich(mainName, knownAs, placeOfOrigin, description, image, ingredientsList);
    }
}
