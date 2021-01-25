package com.example.meditreat_hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.meditreat_hospital.Interface.ItemClickListner;
import com.example.meditreat_hospital.Model.Ambulance;
import com.example.meditreat_hospital.Viewholder.AmbulanceViewHolder;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AmbulanceList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference ambulanceList;
    FirebaseRecyclerAdapter<Ambulance, AmbulanceViewHolder> adapter;

    String CategoryID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_list);

        database = FirebaseDatabase.getInstance();
        ambulanceList = database.getReference("Ambulance");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_ambulance);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            CategoryID = getIntent().getStringExtra("CategoryID");
        }
        if(!CategoryID.isEmpty() && CategoryID != null){
            LoadListAmbulance(CategoryID);
        }

    }

    private void LoadListAmbulance(String CategoryID){
        adapter = new FirebaseRecyclerAdapter<Ambulance, AmbulanceViewHolder>(Ambulance.class,R.layout.ambulance_item,
                AmbulanceViewHolder.class,
                ambulanceList.orderByChild("MenuID").equalTo(CategoryID)
        ) {
            @Override
            protected void populateViewHolder(AmbulanceViewHolder viewHolder, Ambulance model, int position) {
                    viewHolder.AName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.img);
                    final Ambulance local = model;
                    viewHolder.setItemClickListener(new ItemClickListner() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent placeAmbulance = new Intent(AmbulanceList.this,PlaceAmbulance.class);
                            placeAmbulance.putExtra("AmbulanceID",adapter.getRef(position).getKey());
                            startActivity(placeAmbulance);

                        }
                    });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
