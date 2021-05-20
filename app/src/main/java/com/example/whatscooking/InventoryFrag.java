package com.example.whatscooking;

import android.content.res.Resources;
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
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class InventoryFrag extends Fragment {

    private ArrayList<String> ing_list = new ArrayList<String>();
    private LinearLayout ingredientList;
    private MaterialButton addIngredientButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ingredientList = getActivity().findViewById(R.id.ingredient_list);
        addIngredientButton = getActivity().findViewById(R.id.add_ingredient_button);

        addIngredientButton.setOnClickListener(v -> addIngredient(v));
    }

    private void addIngredient(View view) {
        // TODO: Create new activity to search for ingredients
    }
}