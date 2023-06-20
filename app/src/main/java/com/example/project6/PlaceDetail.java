package com.example.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceDetail extends AppCompatActivity {

    PlaceDatabase database;
    PlaceDao placeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        database = Room.databaseBuilder(this, PlaceDatabase.class, "placesDb")
                .allowMainThreadQueries()
                .build();
        placeDao = database.getPlaceDao();

        Place place = placeDao.getPlaceByName(name, email).get(0);

        TextView nameTextView = findViewById(R.id.detailsPlaceNameTextView);
        nameTextView.setText(name);
        TextView addressTextView = findViewById(R.id.detailsPlaceAddressTextView);
        addressTextView.setText(place.getAddress());
        TextView descTextView = findViewById(R.id.detailsPlaceDescTextView);
        descTextView.setText(place.getDesc());
        TextView ratingTextView = findViewById(R.id.detailsPlaceRatingTextView);
        ratingTextView.setText("Rating: " + place.getRating() + "/10");

        Button favorite = findViewById(R.id.favoriteButton);

        if(place.isFavorite()) {
             favorite.setText("Unfavorite");
        } else {
            favorite.setText("Favorite");
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(place.isFavorite()) {
                    place.setFavorite(false);
                    favorite.setText("Favorite");
                } else {
                    place.setFavorite(true);
                    favorite.setText("Unfavorite");
                }

                placeDao.update(place);
            }
        });

        Button visited = findViewById(R.id.visitedButton);
        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.setVisited(true);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                place.setVisitDate(formatter.format(date));
                placeDao.update(place);
            }
        });

        Button done = findViewById(R.id.backButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button remind = findViewById(R.id.remindMeButton);
        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.setRemind(true);
                placeDao.update(place);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(PlaceDetail.this);
                ComponentName thisWidget = new ComponentName(PlaceDetail.this, NewAppWidget.class);
                RemoteViews views = new RemoteViews(PlaceDetail.this.getPackageName(), R.layout.new_app_widget);
                views.setTextViewText(R.id.placetoVisitText, place.getName());
                appWidgetManager.updateAppWidget(thisWidget, views);
            }
        });
    }
}