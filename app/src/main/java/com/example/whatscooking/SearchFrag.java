package com.example.whatscooking;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        q = ((MainActivity) getActivity()).q;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        searchBar = view.findViewById(R.id.search_bar);
        searchButton = view.findViewById(R.id.search_button);
        scrollView = view.findViewById(R.id.search_scroll_view);
        searchList = view.findViewById(R.id.search_list);
        searchButton.setOnClickListener(this::search);
    }

    private CardView createCardView(String name){
        // TODO: fill in context and customize card
        CardView cv = new CardView(this.getContext());
        cv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        TextView tv = new TextView(this.getContext());
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
    private void processResponse(JSONObject res){
        ArrayList<String> meal_names = new ArrayList<>();
        searchList.removeAllViews();
        try{
            JSONArray results = (JSONArray) res.get("meals");
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
        // TODO: When replace API, should abstract this process
        String query = url + "search.php?s=" + this.searchBar.getText().toString();
        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, query, null,
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
