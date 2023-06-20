package com.example.project6;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    PlaceDatabase database;
    PlaceDao placeDao;
    ArrayList<Place> places;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        database = Room.databaseBuilder(getContext(), PlaceDatabase.class, "placesDb")
                .allowMainThreadQueries()
                .build();
        placeDao = database.getPlaceDao();

        places = (ArrayList<Place>) placeDao.getAllVisitedPlaces();

        places = sortPlacesByLastVisited(places);

        recyclerView = view.findViewById(R.id.historyRecyclerView);
        myAdapter = new MyAdapter(places, getContext(), this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private ArrayList<Place> sortPlacesByLastVisited(ArrayList<Place> places) {
        Collections.sort(places, new DateComparator());
        return places;
    }

    private class DateComparator implements Comparator<Place> {

        @Override
        public int compare(Place p1, Place p2) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            try {
                Date date1 = formatter.parse(p1.getVisitDate());
                Date date2 = formatter.parse(p2.getVisitDate());
                if (date1.before(date2)) {
                    return -1;
                } else if (date1.after(date2)) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (ParseException e) {
                Log.e("HISTORY", "Could not parse one or more dates when comparing places", e);
            }
            return 0;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), PlaceDetail.class);
        intent.putExtra("NAME", places.get(position).getName());
        startActivity(intent);
    }
}