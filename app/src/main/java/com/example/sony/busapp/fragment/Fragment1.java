package com.example.sony.busapp.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sony.busapp.DataBase.DatabaseHelper;
import com.example.sony.busapp.DetailScreen;
import com.example.sony.busapp.R;
import com.example.sony.busapp.adapter.FilterWithSpaceAdapter;
import com.example.sony.busapp.adapter.ListAdapter;
import com.example.sony.busapp.model.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment1 extends Fragment {
    ArrayList<String> name;
    FilterWithSpaceAdapter<String> adapterName;
    private static final String BUS_NAME = "^[á à ả ã ạ ă â ê ô ơ ư Ă Â Ê Ô Ơ Ư ắ ằ ẳ ẵ ặ ấ ầ ẩ ẫ ậ ế ề ể ễ ệ é è ẻ ẽ ẹ í ì ỉ ĩ ị ó ò ỏ õ ọ ố ồ ổ ỗ ộ ớ ờ ở ỡ ợ ú ù ủ ũ ụ ứ ừ ử ữ ự ý ỳ ỷ ỹ ỵ  đA-Za-z0-9]{0,50}$";
    private Pattern pattern;
    private Matcher matcher;
    AutoCompleteTextView edtSearch;
    ListAdapter adapter;
    ListView lvBus;
    ArrayList<Bus> arrayList;
    DatabaseHelper db;

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tim_theo_tuyen, container, false);
        db = new DatabaseHelper(getActivity());
        name = new ArrayList<>();
        edtSearch = (AutoCompleteTextView) rootView.findViewById(R.id.edtSearch);

        lvBus = (ListView) rootView.findViewById(R.id.lvBus);
        arrayList = new ArrayList<Bus>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ParseJSON().execute("http://xebuyt.000webhostapp.com/json.php");
            }
        });
        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int i = name.indexOf(edtSearch.getText().toString());
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", arrayList.get(i).bus_id);
                bundle.putString("name", arrayList.get(i).bus_name);
                bundle.putString("start", arrayList.get(i).bus_start);
                bundle.putString("end", arrayList.get(i).bus_end);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
                edtSearch.setText("");
            }
        });
        return rootView;
    }

    public void test() {
        lvBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", arrayList.get(position).bus_id);
                bundle.putString("name", arrayList.get(position).bus_name);
                bundle.putString("start", arrayList.get(position).bus_start);
                bundle.putString("end", arrayList.get(position).bus_end);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
            }
        });
    }

    class ParseJSON extends AsyncTask<String, Integer, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(getActivity());
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
                    Bus list = new Bus();
                    list.setBus_id(obj.getInt("id"));
                    list.setBus_name(obj.getString("name"));
                    list.setBus_start(obj.getString("start"));
                    list.setBus_end(obj.getString("end"));
                    arrayList.add(list);
                    db.addBus(list);
                    name.add(arrayList.get(i).bus_start);
                }
                adapterName = new FilterWithSpaceAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, name);
                edtSearch.setAdapter(adapterName);
                test();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ListAdapter(getActivity(), R.layout.item_listview, arrayList);
            lvBus.setAdapter(adapter);

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

}

