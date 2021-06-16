package com.example.whatscooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
    private EditText search_bar;
    private Button search_button;
    private ListView search_list;
    private RequestQueue q;
    private ArrayList<String> recipes;
    private ArrayAdapter<String> recipe_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        q = ((MainActivity) getActivity()).q;
        recipes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        search_bar = view.findViewById(R.id.search_bar);
        search_button = view.findViewById(R.id.search_button);
        search_list = view.findViewById(R.id.search_list);

        recipe_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recipes);
        search_list.setAdapter(recipe_adapter);
        search_button.setOnClickListener(this::search);
    }

    private void processResponse(JSONObject res){
        recipes.clear();
        try{
            JSONArray results = res.getJSONArray("meals");
            for(int i=0; i<results.length(); i++) {
                JSONObject recipeItem = (JSONObject) results.get(i);
                recipes.add(recipeItem.get("strMeal").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void search(View view){
        // TODO: When replace API, should abstract this process
        String query = url + "search.php?s=" + this.search_bar.getText().toString();
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
    }
}
