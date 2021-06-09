package com.example.whatscooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class SearchFrag extends Fragment {
    final static String url = "https://www.themealdb.com/api/json/v1/1/";
    private EditText searchBar;
    private Button searchButton;
    private ScrollView scrollView;
    private LinearLayout searchList;
    private RequestQueue q;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        q = Volley.newRequestQueue(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        searchBar = view.findViewById(R.id.search_bar);
        searchButton = view.findViewById(R.id.search_button);
        scrollView = view.findViewById(R.id.search_scroll_view);
    }

    private CardView createCardView(String name){
        // TODO: fill in context and customize card
        return new CardView(this.getContext());
    }
    private void processResponse(JSONObject res){
        ArrayList<String> meal_names = new ArrayList<>();
        try{
            JSONArray results = (JSONArray) res.get("meals");
            // TODO: only fetches one result
            for(int i=0; i<results.length(); i++) {
                JSONObject recipeItem = (JSONObject) results.get(i);
                meal_names.add(recipeItem.get("strMeal").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(meal_names.size() > 0){
            for(String name : meal_names) {
                CardView cv = createCardView(name);
                searchList.addView(cv);
            }
        }
    }

    private void search(View view){
        String query = url + this.searchBar.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, query, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: make toast
                        System.out.println("Error");
                    }
                });
        q.add(jsonObjectRequest);
        // TODO: make toast
    }
}
