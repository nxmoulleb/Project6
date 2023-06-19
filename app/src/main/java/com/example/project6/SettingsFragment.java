package com.example.project6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences fragPref = getActivity().getSharedPreferences("FRAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor fragEditor = fragPref.edit();
        fragEditor.putInt("current", 4).commit();

        SharedPreferences themePref = getActivity().getSharedPreferences("THEME", Context.MODE_PRIVATE);
        TextView textView = view.findViewById(R.id.themeTextBox);
        textView.setText("Current theme: " + themePref.getString("current", "Light"));

        view.findViewById(R.id.themeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = themePref.edit();
                String theme = themePref.getString("current", "Light");
                HashMap<String, String> opposite = new HashMap<>();
                opposite.put("Light", "Dark");
                opposite.put("Dark", "Light");

                editor.putString("current", opposite.get(theme));
                editor.commit();

                String newTheme = themePref.getString("current", "Light");

                textView.setText("Current theme: " + newTheme);
                decideTheme(newTheme);
            }
        });

        return view;
    }

    public static void decideTheme(String theme) {
        if (theme.equals("Light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}