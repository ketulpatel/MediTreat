package com.example.meditreat_hospital.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditreat_hospital.Interface.ItemClickListner;
import com.example.meditreat_hospital.R;

public class AmbulanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView AName;
    public ImageView img;
    private ItemClickListner itemClickListener;

    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AmbulanceViewHolder(@NonNull View itemView) {
        super(itemView);

        AName = (TextView)itemView.findViewById(R.id.name);
        img = (ImageView)itemView.findViewById(R.id.img1);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
            itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
