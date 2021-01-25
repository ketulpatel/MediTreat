package com.example.meditreat;

import android.app.Activity;
import android.os.Bundle;

public class OnInternetConnection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nointernetpage);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
