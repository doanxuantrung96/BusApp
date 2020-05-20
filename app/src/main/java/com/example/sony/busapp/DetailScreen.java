package com.example.sony.busapp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sony.busapp.Util.ConnectivityReceiver;
import com.example.sony.busapp.adapter.ListAdapter;
import com.example.sony.busapp.fragment.Fragment1;
import com.example.sony.busapp.model.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DetailScreen extends ActionBarActivity {
    TextView tvMaso, tvTen, tvLuotdi, tvLuotve;
    int id;
    String name, start, end;
    ArrayList<Bus> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller =
                callerIntent.getBundleExtra("Mypack");
        id = packageFromCaller.getInt("id");
        name = packageFromCaller.getString("name");
        start = packageFromCaller.getString("start");
        end = packageFromCaller.getString("end");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();
        TextView tvTitle = (TextView) findViewById(R.id.tvName);
        if (id < 9) {
            tvTitle.setText("0" + id + " " + name);
        } else {
            tvTitle.setText(id + " " + name);
        }

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arrayList = new ArrayList<Bus>();

        tvMaso = (TextView) findViewById(R.id.tvMaso);
        tvTen = (TextView) findViewById(R.id.tvTen);
        tvLuotdi = (TextView) findViewById(R.id.tvLuotdi);
        tvLuotve = (TextView) findViewById(R.id.tvLuotve);


        new ParseJSON().execute();

    }

    class ParseJSON extends AsyncTask<String, Integer, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(DetailScreen.this);
            Dialog.setTitle("Please Wait");
            Dialog.setMessage("Loading...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (id < 9) {
                tvMaso.setText("0" + String.valueOf(id));
            } else {
                tvMaso.setText(String.valueOf(id));
            }
            tvTen.setText(name);
            tvLuotdi.setText(start);
            tvLuotve.setText(end);
            Dialog.cancel();
        }
    }

}
