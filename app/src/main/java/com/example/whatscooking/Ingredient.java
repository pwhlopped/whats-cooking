package com.example.whatscooking;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ingredient extends Fragment {

    public ArrayList<String> ing_list = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Ingredient.this)
                        .navigate(R.id.ingredient_to_main);
            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addIngredient(v);
            }
        });
    }
    public void addIngredient(View view) {
        EditText et = getView().findViewById(R.id.ingredient_name);
        String item = et.getText().toString();
        ing_list.add(item);
        CardView cv = new CardView(getContext());
        ViewGroup.MarginLayoutParams cvl = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        Resources r = getContext().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16,
                r.getDisplayMetrics()
        );
        cvl.setMargins(px, px/2, px, px/2);
        cv.setLayoutParams(cvl);
        TextView tv = new TextView(getContext());
        tv.setText(item);
        tv.setTextSize(50);
        tv.setGravity(Gravity.CENTER);
        cv.addView(tv);
        LinearLayout ll = getView().findViewById(R.id.list);
        ll.addView(cv);
    }
}