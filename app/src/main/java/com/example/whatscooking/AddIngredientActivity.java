package com.example.whatscooking;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class AddIngredientActivity extends AppCompatActivity {
    private ArrayList<String> ing_list;
    private LinearLayout ingredient_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        Toolbar t = findViewById(R.id.addIngredientToolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ing_list = getIntent().getExtras().getStringArrayList("ingredient_list");
        ingredient_list = findViewById(R.id.ingredient_list);
        createList();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createList(){
        for(int i=0; i<ing_list.size(); ++i) {
            ingredient_list.addView(createCardView(ing_list.get(i)));
        }
    }

    private CardView createCardView(String name){
        // TODO: fill in context and customize card
        CardView cv = new CardView(this);
        cv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        TextView tv = new TextView(this);
        tv.setText(name);
        tv.setGravity(Gravity.CENTER);
        int pad =  (int) getResources().getDimension(R.dimen.inventory_padding);
        tv.setPadding(pad, pad, pad, pad);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        tv.setTextSize(20);
        cv.addView(tv);
        return cv;
    }
}
