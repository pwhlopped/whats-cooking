package com.example.whatscooking;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Objects;

public class AddIngredientActivity extends AppCompatActivity {
    private ArrayList<String> ing_list;
    private ArrayAdapter<String> ing_adpt;
    private ListView ingredient_list;
    private SearchView sv;
    private static String ingredient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        Toolbar t = findViewById(R.id.add_ingredient_toolbar);
        setSupportActionBar(t);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ing_list = getIntent().getExtras().getStringArrayList("ingredient_list");
        ing_adpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ing_list);
        ingredient_list = findViewById(R.id.ingredient_list);
        ingredient_list.setAdapter(ing_adpt);
        ingredient_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                AddIngredientDialogFragment df = new AddIngredientDialogFragment();
                df.show(getSupportFragmentManager(), name);
            }
        });

        sv = findViewById(R.id.ingredient_search);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ing_adpt.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ing_adpt.getFilter().filter(newText);
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static class AddIngredientDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            NumberPicker np = new NumberPicker(getActivity());
            np.setMaxValue(1000);
            np.setMinValue(0);
            AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Quantitiy");
            builder.setView(np);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });
            return builder.create();
        }
    }
}
