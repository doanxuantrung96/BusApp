package com.example.sony.busapp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sony.busapp.DataBase.DatabaseHelper;
import com.example.sony.busapp.adapter.FilterWithSpaceAdapter;
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


public class Detail_Info_Screen extends AppCompatActivity {
    DatabaseHelper db;
    ListAdapter adapter;
    ListView lv;
    String start;
    ArrayList<Bus> arr = new ArrayList<Bus>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_screen);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();
        TextView tvTitle = (TextView) findViewById(R.id.tvName);
        tvTitle.setText("Các Tuyến Đi Qua");


        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent callerIntent = getIntent();
        Bundle packetFromCaller = callerIntent.getBundleExtra("Mypack");
        start = packetFromCaller.getString("Origin");
        lv = (ListView) findViewById(R.id.lvBus);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ParseJSON().execute("http://xebuyt.000webhostapp.com/json.php");
            }
        });
    }

    class ParseJSON extends AsyncTask<String, Integer, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(Detail_Info_Screen.this);
            Dialog.setTitle("Please Wait");
            Dialog.setMessage("Loading...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return GetJSON(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Log.d("rrrr", obj.getString("start"));
                    if (obj.getString("start").equals(start)) {
                        Bus list = new Bus();
                        list.setBus_id(obj.getInt("id"));
                        list.setBus_name(obj.getString("name"));
                        list.setBus_start(obj.getString("start"));
                        list.setBus_end(obj.getString("end"));
                        arr.add(list);
                    }
                }
                test();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ListAdapter(Detail_Info_Screen.this, R.layout.item_listview, arr);
            lv.setAdapter(adapter);

            Dialog.cancel();
        }
    }

    private static String GetJSON(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void test() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Detail_Info_Screen.this, DetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", arr.get(position).bus_id);
                bundle.putString("name", arr.get(position).bus_name);
                bundle.putString("start", arr.get(position).bus_start);
                bundle.putString("end", arr.get(position).bus_end);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
            }
        });
    }
}
