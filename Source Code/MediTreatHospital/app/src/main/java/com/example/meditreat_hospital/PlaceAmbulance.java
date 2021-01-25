package com.example.meditreat_hospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meditreat_hospital.Model.Ambulance;
import com.example.meditreat_hospital.Model.PlaceAmbulanceTo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceAmbulance extends AppCompatActivity {

    EditText edt1;
    Button btn1;

    FirebaseDatabase database;
    DatabaseReference request;
    String AmbulanceID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ambulance);

        edt1 = (EditText)findViewById(R.id.edtadd);
        btn1 = (Button)findViewById(R.id.btn1);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Request");
        AmbulanceID = getIntent().getStringExtra("AmbulanceID");


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceAmbulanceTo placeAmbulanceTo = new PlaceAmbulanceTo(edt1.getText().toString());

                request.child(AmbulanceID).setValue(placeAmbulanceTo);

                finish();
            }
        });
    }
}
