package com.example.customworkouts.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customworkouts.R;

import java.security.acl.Group;
import java.util.ArrayList;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {


    private ArrayList<Integer> colors;
    private View lastViewTouched;
    private GroupWorkoutsAdapter adapter;

    public void setAdapter(GroupWorkoutsAdapter a) {
        adapter = a;
    }

    public void initData() {
        colors = new ArrayList<>();

         colors.add(R.color.red);
         colors.add(R.color.green);
         colors.add(R.color.blue);
         colors.add(R.color.yellow);
         colors.add(R.color.purple);
         colors.add(R.color.brown);
         colors.add(R.color.orange);
    }

    public ColorsAdapter() {
        initData();
    }

    @NonNull
    @Override
    public ColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsAdapter.ViewHolder holder, int position) {

        int color = colors.get(position);
        holder.colorCircle.setColorFilter(holder.itemView.getContext().getResources().getColor(color));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastViewTouched != v) {
                    if (lastViewTouched != null) {
                        //lastViewTouched.setLayoutParams(new RecyclerView.LayoutParams(30,30));
                    }
                    lastViewTouched = v;
                    //v.setLayoutParams(new RecyclerView.LayoutParams(50,50));
                    //adapter.setColor(color);
                    Toast.makeText(v.getContext(), "Color is : " + color, Toast.LENGTH_SHORT).show();
                }
                adapter.setColor(color);
                Toast.makeText(v.getContext(), "Color is : " + color, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView colorCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            colorCircle = itemView.findViewById(R.id.color_circle);
        }
    }
}
