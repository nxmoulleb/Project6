package com.example.project6;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    PlaceDatabase database;
    PlaceDao placeDao;
    ArrayList<Place> places;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        database = Room.databaseBuilder(getContext(), PlaceDatabase.class, "placesDb")
                .allowMainThreadQueries()
                .build();
        placeDao = database.getPlaceDao();

        places = (ArrayList<Place>) placeDao.getAllFavPlaces();

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        myAdapter = new MyAdapter(places, getContext(), this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), PlaceDetail.class);
        intent.putExtra("NAME", places.get(position).getName());
        startActivity(intent);
    }
}