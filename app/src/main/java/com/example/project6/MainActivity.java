package com.example.project6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences themePref = getSharedPreferences("THEME", Context.MODE_PRIVATE);
        SettingsFragment.decideTheme(themePref.getString("current", "Light"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void goSignIn(View view) {
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        SharedPreferences fragPref = getSharedPreferences("FRAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fragPref.edit();
        editor.putInt("current", 0);

        EditText email = findViewById(R.id.emailEditText);
        String userString = email.getText().toString();
        EditText password = findViewById(R.id.passwordEditText);
        String passwordString = password.getText().toString();
        mAuth.signInWithEmailAndPassword(userString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i("LOG_IN", "log in success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "Log in successful!",
                                Toast.LENGTH_SHORT).show();
                        editor.commit();
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("LOG_IN", "log in failed", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
        });
    }

    public void goRegister(View view) {
        EditText email = findViewById(R.id.emailEditText);
        String userString = email.getText().toString();
        EditText password = findViewById(R.id.passwordEditText);
        String passwordString = password.getText().toString();
        if (userString.equals("")) {
            Toast.makeText(MainActivity.this, "Email is required",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (passwordString.equals("")) {
            Toast.makeText(MainActivity.this, "Password is required",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(userString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("LOG_IN", "create user success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Account created successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("LOG_IN", "creating user failed", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}