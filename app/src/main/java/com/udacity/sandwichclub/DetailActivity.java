package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView alsoKnowsAsTV = findViewById(R.id.also_known_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        TextView alsoKnowsAsTVLabel = findViewById(R.id.textView3);
        TextView placeOfOriginLabel = findViewById(R.id.textView2);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json,this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


        List<String> alsoKnowsAsList = sandwich.getAlsoKnownAs();
        String alsoKnownsAs = TextUtils.join(", ",alsoKnowsAsList);

        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients = TextUtils.join(", ",ingredientsList);


        if (alsoKnownsAs.equals("")) {
            alsoKnowsAsTV.setVisibility(View.INVISIBLE);
            alsoKnowsAsTVLabel.setVisibility(View.INVISIBLE);
            alsoKnowsAsTV.setHeight(0);
            alsoKnowsAsTVLabel.setHeight(0);

        }
        alsoKnowsAsTV.setText(alsoKnownsAs);
        descriptionTV.setText(sandwich.getDescription());
        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOrigin.setVisibility(View.INVISIBLE);
            placeOfOrigin.setHeight(0);
            placeOfOriginLabel.setVisibility(View.INVISIBLE);
            placeOfOriginLabel.setHeight(0);
        }
        else {
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        ingredientsTV.setText(ingredients);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
