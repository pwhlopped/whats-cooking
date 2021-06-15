package com.example.whatscooking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryFrag extends Fragment {

    private ArrayList<String> ing_list;
    private LinearLayout ingredientList;
    private MaterialButton addIngredientButton;
    private final String url = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
    public final static int ADD_INGREDIENT_ACTIVITY_CODE = 1;
    private RequestQueue q;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        q = ((MainActivity) getActivity()).q;
        ing_list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        ing_list.clear();
        try{
            JSONArray jsa = res.getJSONArray("meals");
            for(int i=0; i<jsa.length(); ++i){
                JSONObject jso = jsa.getJSONObject(i);
                ing_list.add(jso.getString("strIngredient"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addIngredient(View view) {
        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        processResponse(response);
                        callback();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        q.add(r);
    }
    private void callback(){
        Toast.makeText(getContext(), "Request Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), AddIngredientActivity.class);
        intent.putStringArrayListExtra("ingredient_list", this.ing_list);
        startActivityForResult(intent, ADD_INGREDIENT_ACTIVITY_CODE);
    }
}