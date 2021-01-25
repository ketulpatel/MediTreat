package com.example.meditreatambulance.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.meditreatambulance.Interface.ItemClickListner;
import com.example.meditreatambulance.R;


public class AmbulanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public TextView t1,t2,t3;

    private ItemClickListner itemClickListener;


    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AmbulanceViewHolder(@NonNull View itemView) {
        super(itemView);
        t3 = (TextView)itemView.findViewById(R.id.AmbulanceID);
        t1 = (TextView)itemView.findViewById(R.id.Address);
        t2 = (TextView)itemView.findViewById(R.id.status);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }





    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        menu.add(0,0,getAdapterPosition(),"Update");
        menu.add(0,1,getAdapterPosition(),"Delete");
    }
}

