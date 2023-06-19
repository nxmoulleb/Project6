package com.example.project6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences themePref = getSharedPreferences("THEME", Context.MODE_PRIVATE);
        SettingsFragment.decideTheme(themePref.getString("current", "Light"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goSignIn(View view) {
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        SharedPreferences fragPref = getSharedPreferences("FRAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fragPref.edit();
        editor.putInt("current", 0);
        editor.commit();
        startActivity(intent);
    }

    public void goRegister(View view) {
        Toast.makeText(this, "Ur r registered yayy", Toast.LENGTH_SHORT).show();
    }
}