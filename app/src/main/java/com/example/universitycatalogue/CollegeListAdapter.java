package com.example.universitycatalogue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CollegeListAdapter extends RecyclerView.Adapter<CollegeViewHolder>{

    ArrayList<CollegeInfo> items = new ArrayList<>();
    ICollegeAdapter listener;

    public CollegeListAdapter(ICollegeAdapter listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_item_view, parent, false);
        final CollegeViewHolder viewHolder = new CollegeViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(items.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeViewHolder holder, int position) {
        holder.collegeName.setText(items.get(position).collegeName);
        holder.stateName.setText(items.get(position).state);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void updateColleges(ArrayList<CollegeInfo> updatedList){
        this.items.clear();
        this.items.addAll(updatedList);
        notifyDataSetChanged();
    }
}

class CollegeViewHolder extends RecyclerView.ViewHolder{

    public CollegeViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    TextView collegeName = itemView.findViewById(R.id.collegeName);
    TextView stateName = itemView.findViewById(R.id.stateName);
}

interface ICollegeAdapter{
    void onItemClicked(CollegeInfo collegeInfo);
}