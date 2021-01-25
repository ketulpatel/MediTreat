package com.example.meditreat_hospital.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditreat_hospital.Interface.ItemClickListner;
import com.example.meditreat_hospital.R;

public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtAName;
    public ImageView imageView;

    private ItemClickListner itemClickListner;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        txtAName =(TextView)itemView.findViewById(R.id.txt1);
        imageView = (ImageView)itemView.findViewById(R.id.img1);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View view){
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }
}
