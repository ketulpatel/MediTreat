package com.example.meditreat_hospital;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meditreat_hospital.Common.Common;
import com.example.meditreat_hospital.Model.PlaceAmbulanceTo;
import com.example.meditreat_hospital.Viewholder.ActiveAmbulanceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AmbulanceStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<PlaceAmbulanceTo, ActiveAmbulanceViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_status);


        database = FirebaseDatabase.getInstance();
        request = database.getReference("Request");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_ambulancestatus);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() == null){
            LoadAmbulance();
        }
        else{
            LoadAmbulance();
        }


    }

    private void LoadAmbulance() {

        adapter = new FirebaseRecyclerAdapter<PlaceAmbulanceTo, ActiveAmbulanceViewHolder>(

                PlaceAmbulanceTo.class,
                R.layout.astatus_layout,
                ActiveAmbulanceViewHolder.class,
                request.orderByChild("AmbulanceID")
        ) {
            @Override
            protected void populateViewHolder(ActiveAmbulanceViewHolder viewHolder, PlaceAmbulanceTo model, int position) {
                viewHolder.t3.setText(adapter.getRef(position).getKey());
                viewHolder.t1.setText(model.getAddress());
                viewHolder.t2.setText(model.getStatus());

            }
        };

        recyclerView.setAdapter(adapter);
    }

   /* private String convertCodeToStatus(String status){
        if(status.equals(0)){
                status =  "Assigned";
        }
        if(status.equals(1)){
            status =  "Ambulance Going";
        }
        if(status.equals(2)){
            status = "Ambulance on the way to hospital";
        }
        return status;
    }*/
}
