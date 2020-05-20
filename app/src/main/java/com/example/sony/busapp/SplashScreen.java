package com.example.sony.busapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.sony.busapp.Util.ConnectivityReceiver;


public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    public static String message;
    private static int tmp_check;
    final Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityReceiver sconn = new ConnectivityReceiver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_screen_layout);

        boolean checkconn = sconn.isConnected(this);
        data.putBoolean("check", checkconn);
        if (checkconn == true) {
            Log.d("test", "connected");
        } else {
            Log.d("test", "not connected");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
