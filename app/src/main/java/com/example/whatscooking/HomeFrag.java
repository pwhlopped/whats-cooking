package com.example.whatscooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFrag extends Fragment {
    private TextView textview;
    private Button button;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        textview = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button2);
        view.findViewById(R.id.button2).setOnClickListener(v -> NavHostFragment.findNavController(HomeFrag.this)
                .navigate(R.id.action_home_to_inven));
    }
}
