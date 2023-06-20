package com.example.project6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements RecyclerViewInterface{

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    PlaceDatabase database;
    PlaceDao placeDao;
    ArrayList<Place> places;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        database = Room.databaseBuilder(getContext(), PlaceDatabase.class, "placesDb")
                .allowMainThreadQueries()
                .build();
        placeDao = database.getPlaceDao();

        places = (ArrayList<Place>) placeDao.getAllPlaces();

        recyclerView = view.findViewById(R.id.homeRecyclerView);
        myAdapter = new MyAdapter(places, getContext(), this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button addPlace = view.findViewById(R.id.addPlaceButton);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.homeEditText);
                String placeName = editText.getText().toString();
                if(!placeName.isEmpty() && placeDao.getPlaceByName(placeName).isEmpty()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                    LinearLayout lila1 = new LinearLayout(getContext());
                    lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                    EditText address = new EditText(getContext());
                    EditText desc = new EditText(getContext());
                    EditText rating = new EditText(getContext());
                    address.setHint("Address");
                    desc.setHint("Description");
                    rating.setHint("Rating out of 10");
                    rating.setInputType(InputType.TYPE_CLASS_NUMBER);
                    lila1.addView(address);
                    lila1.addView(desc);
                    lila1.addView(rating);
                    alert.setView(lila1);

                    alert.setTitle(placeName + " Details");

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value1 = address.getText().toString().trim();
                            String value2 = desc.getText().toString().trim();
                            String value3 = rating.getText().toString();

                            if(!value1.isEmpty() && !value2.isEmpty() && !value3.isEmpty()) {
                                double val3 = Double.parseDouble(value3);
                                if(val3 >= 0 && val3 <= 10) {
                                    Place place = new Place(placeName, value1, value2, val3);
                                    placeDao.insert(place);
                                }
                            } else {
                                Toast.makeText(getContext(), "Make sure all inputs are valid", Toast.LENGTH_SHORT).show();
                            }

                            updateRecyclerView();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });

                    alert.show();
                } else {
                    Toast.makeText(getContext(), "No empty or repeated names", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), PlaceDetail.class);
        intent.putExtra("NAME", places.get(position).getName());
        startActivity(intent);
    }

    private void updateRecyclerView(){
        places = (ArrayList<Place>) placeDao.getAllPlaces();
        myAdapter = new MyAdapter(places, getContext(), this);
        recyclerView.setAdapter(myAdapter);
    }
}