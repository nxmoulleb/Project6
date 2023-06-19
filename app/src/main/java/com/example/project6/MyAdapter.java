package com.example.project6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    ArrayList<Place> places;
    Context context;

    public MyAdapter(ArrayList<Place> places, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.places = places;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_places, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, recyclerViewInterface);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.name.setText(places.get(position).getName());
        holder.address.setText(places.get(position).getAddress());
        holder.desc.setText(places.get(position).getDesc());
        holder.rating.setText(places.get(position).getRating()+"/10");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, address, desc, rating;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.placeNameTextView);
            address = (TextView) itemView.findViewById(R.id.placeAddressTextView);
            desc = (TextView) itemView.findViewById(R.id.placeDescTextView);
            rating = (TextView) itemView.findViewById(R.id.placeRatingTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
