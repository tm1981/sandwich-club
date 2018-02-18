package com.udacity.sandwichclub.utils;

import android.nfc.Tag;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject jObject;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        String mainName = null;
        JSONArray alsoKnownAs = null;
        JSONArray ingredients = null;

        try {
            jObject = new JSONObject(json);
            placeOfOrigin = jObject.getString("placeOfOrigin");
            description = jObject.getString("description");
            image = jObject.getString("image");
            JSONObject name = jObject.getJSONObject("name");
            mainName = name.getString("mainName");
            alsoKnownAs = name.getJSONArray("alsoKnownAs");
            ingredients = jObject.getJSONArray("ingredients");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> knownAs = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();

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

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mainName);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setAlsoKnownAs(knownAs);
        sandwich.setIngredients(ingredientsList);


        return sandwich;
    }
}
