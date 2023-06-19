package com.example.project6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyExplorerAdapter extends RecyclerView.Adapter<MyExplorerAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    ArrayList<Place> places;
    Context context;

    public MyExplorerAdapter(ArrayList<Place> places, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.places = places;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyExplorerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_places_explore, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, recyclerViewInterface);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyExplorerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(places.get(position).getName());
        holder.address.setText(places.get(position).getAddress());
        holder.desc.setText(places.get(position).getDesc());
        holder.visitDate.setText(places.get(position).getVisitDate());
        holder.rating.setText(places.get(position).getRating()+"/10");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, address, desc, visitDate, rating;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.exNameTextView);
            address = (TextView) itemView.findViewById(R.id.exAddressTextView);
            desc = (TextView) itemView.findViewById(R.id.exDescTextView);
            visitDate = (TextView) itemView.findViewById(R.id.exDateTextView);
            rating = (TextView) itemView.findViewById(R.id.exRatingTextView);

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
