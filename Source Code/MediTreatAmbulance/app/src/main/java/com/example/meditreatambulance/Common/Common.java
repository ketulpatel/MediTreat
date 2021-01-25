package com.example.meditreatambulance.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.meditreatambulance.Model.PlaceAmbulanceTo;
import com.example.meditreatambulance.Model.User;
import com.example.meditreatambulance.Remote.IGeoCoordinates;
import com.example.meditreatambulance.Remote.RetrofitClient;

public class Common {
    public static User currentUser;
    public static PlaceAmbulanceTo currentAmbulance;

  //  public static final int PICK_IMAGE_REQUEST = 71;
    public static final String baseURL = "https://maps.googleapis.com";
    public static IGeoCoordinates getGeoCodeService(){
        return RetrofitClient.getClient(baseURL).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap,int newWidth,int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);

        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotX=0,pivotY=0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }





}
