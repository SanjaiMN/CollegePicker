package com.example.collegepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder>
{
    List<Collegedetails> list;
    RecyclerviewInterface recyclerviewInterface;
    collegelist collegelist;
    Context context;
    RecyclerAdaptor(List<Collegedetails> list, RecyclerviewInterface recyclerviewInterface,Context context)
    {
        this.list=list;
        this.recyclerviewInterface=recyclerviewInterface;
        collegelist=(collegelist)recyclerviewInterface;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rowitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,collegelist);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.collegename.setText(list.get(position).name+"");
        holder.code.setText(list.get(position).code+"");
        holder.mincutoff.setText("Min.Cutoff :"+list.get(position).mincutoff);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView collegename,code,mincutoff;
        collegelist collegelist;
        CardView cardView;
        @SuppressLint("ResourceAsColor")
        public ViewHolder(@NonNull View itemView, final collegelist collegelist)
        {
            super(itemView);
            collegename=itemView.findViewById(R.id.collegename);
            code=itemView.findViewById(R.id.code);
            mincutoff=itemView.findViewById(R.id.min);
            cardView=itemView.findViewById(R.id.cardView);
            this.collegelist=collegelist;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    recyclerviewInterface.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
    public void updatelist(List<Collegedetails> newlist)
    {
        list=new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }
}