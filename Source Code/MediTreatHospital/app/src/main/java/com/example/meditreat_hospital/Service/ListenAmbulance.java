package com.example.meditreat_hospital.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.meditreat_hospital.AmbulanceStatus;
import com.example.meditreat_hospital.Model.PlaceAmbulanceTo;
import com.example.meditreat_hospital.PlaceAmbulance;
import com.example.meditreat_hospital.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenAmbulance extends Service implements ChildEventListener {

    FirebaseDatabase database;
    DatabaseReference reference;

    public ListenAmbulance() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Request");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reference.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        PlaceAmbulanceTo placeAmbulanceTo = dataSnapshot.getValue(PlaceAmbulanceTo.class);
        showNotification(dataSnapshot.getKey(),placeAmbulanceTo);
    }

    private void showNotification(String key, PlaceAmbulanceTo reference) {
        Intent intent = new Intent(getBaseContext(), PlaceAmbulanceTo.class);
        intent.putExtra("AmbulanceID",reference.getAddress());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_ALL)
        .setWhen(System.currentTimeMillis())
        .setTicker("EDMTDev")
        .setContentInfo("Ambulance Responded")
        .setContentText("Ambulance #"+key+" was updated to "+ reference.getStatus() )
        .setContentIntent(contentIntent)
        .setContentInfo("Info")
        .setSmallIcon(R.mipmap.ic_launcher_round)
        ;

        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
