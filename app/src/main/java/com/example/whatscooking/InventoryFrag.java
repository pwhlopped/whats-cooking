package com.example.whatscooking;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryFrag extends Fragment {

    private ArrayList<String> ing_list = new ArrayList<>();
    private LinearLayout ingredientList;
    private MaterialButton addIngredientButton;
    private RequestQueue q;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        q = ((MainActivity) getActivity()).q;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // TODO: Create search bar material ui
        ingredientList = getActivity().findViewById(R.id.ingredient_list);
        addIngredientButton = getActivity().findViewById(R.id.add_ingredient_button);

        addIngredientButton.setOnClickListener(this::addIngredient);
    }

    private void processResponse(JSONObject res){
        Toast.makeText(getContext(), res.toString(), Toast.LENGTH_LONG).show();
        // TODO: process ingredients for search bar
    }

    private void addIngredient(View view) {
        // TODO: Create new activity to search for ingredients
        String url = "https://foodb.ca/api/v1/foodreport/food";
        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        q.add(r);
        Toast.makeText(getContext(), "Request Success", Toast.LENGTH_SHORT).show();
    }
}