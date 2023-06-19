package com.example.project6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaceDao {

    @Insert
    public void insert(Place... places);

    @Update
    public void update(Place... places);

    @Delete
    public void delete(Place... places);

    @Query("SELECT * from places")
    public List<Place> getAllPlaces();

    @Query("SELECT * from places where name = :name")
    public List<Place> getPlaceByName(String name);

    @Query("SELECT * from places where favorite = 1")
    public List<Place> getAllFavPlaces();

    @Query("SELECT * from places where visited = 1")
    public List<Place> getAllVisitedPlaces();
}
