package com.example.meditreatambulance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.meditreatambulance.Common.Common;
import com.example.meditreatambulance.Interface.ItemClickListner;
import com.example.meditreatambulance.Model.Ambulance;
import com.example.meditreatambulance.Model.PlaceAmbulanceTo;
import com.example.meditreatambulance.ViewHolder.AmbulanceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AmbulanceStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference reference;

    Spinner spinner;
    FirebaseRecyclerAdapter<PlaceAmbulanceTo, AmbulanceViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_status);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Request");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_ambulancestatus);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();
    }

    private void loadOrder() {

        adapter = new FirebaseRecyclerAdapter<PlaceAmbulanceTo, AmbulanceViewHolder>(
                PlaceAmbulanceTo.class,
                R.layout.astatus_layout,
                AmbulanceViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(AmbulanceViewHolder viewHolder, final PlaceAmbulanceTo model, int position) {
                viewHolder.t3.setText(adapter.getRef(position).getKey());
                viewHolder.t1.setText(model.getAddress());
                viewHolder.t2.setText(model.getStatus());


                viewHolder.setItemClickListener(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Common.currentAmbulance = model;
                        Intent tracking = new Intent(AmbulanceStatus.this,TrackingAmbulance.class);

                        startActivity(tracking);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Update")){
            showAcceptDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if(item.getTitle().equals("Delete")){
            showowpDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }

    private void showAcceptDialog(String key, final PlaceAmbulanceTo item) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(AmbulanceStatus.this);
        alert.setTitle("Check the Function");
        alert.setMessage("Select Status");
        LayoutInflater inflater  = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_ambulance_status_layout,null);
        spinner = (Spinner)view.findViewById(R.id.spiiner1);
        String[] arraySpinner = new String[] {
                "Accept", "On the way to Patient", "On the way to Hospital"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        alert.setView(view);
        final String localKey = key;
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedItem()));
                reference.child(localKey).setValue(item);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alert.show();
    }

    private void showowpDialog(String key, PlaceAmbulanceTo item) {
        reference.child(key).removeValue();
    }

}
