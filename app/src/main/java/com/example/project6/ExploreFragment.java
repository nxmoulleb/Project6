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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    MyExplorerAdapter myAdapter;

    PlaceDatabase database;
    PlaceDao placeDao;
    private FirebaseAuth mAuth;
    ArrayList<Place> places;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        database = Room.databaseBuilder(getContext(), PlaceDatabase.class, "placesDb")
                .allowMainThreadQueries()
                .build();
        placeDao = database.getPlaceDao();

        places = (ArrayList<Place>) placeDao.getAllPlaces();

        recyclerView = view.findViewById(R.id.exploreRecyclerView);
        myAdapter = new MyExplorerAdapter(places, getContext(), this);
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