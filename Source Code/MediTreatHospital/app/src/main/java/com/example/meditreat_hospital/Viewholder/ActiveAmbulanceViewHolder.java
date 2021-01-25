package com.example.meditreat_hospital.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meditreat_hospital.Interface.ItemClickListner;
import com.example.meditreat_hospital.R;

public class ActiveAmbulanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView t1,t2,t3;

    private ItemClickListner itemClickListener;


    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ActiveAmbulanceViewHolder(@NonNull View itemView) {
        super(itemView);
        t3 = (TextView)itemView.findViewById(R.id.AmbulanceID);
        t1 = (TextView)itemView.findViewById(R.id.Address);
        t2 = (TextView)itemView.findViewById(R.id.status);
        itemView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
