package com.example.sony.busapp.fragment;

import android.app.Fragment;
import android.app.job.JobInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.sony.busapp.DataBase.DatabaseHelper;
import com.example.sony.busapp.DetailScreen;
import com.example.sony.busapp.R;
import com.example.sony.busapp.adapter.FilterWithSpaceAdapter;
import com.example.sony.busapp.adapter.ListAdapter;
import com.example.sony.busapp.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    DatabaseHelper db;
    ListAdapter adapter;
    ListView lv;
    AutoCompleteTextView edtSearch;
    ArrayList<Bus> arr = new ArrayList<Bus>();
    ArrayList<String> name;
    FilterWithSpaceAdapter<String> adapterName;

    public Fragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tim_theo_tuyen, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvBus);
        edtSearch = (AutoCompleteTextView) rootView.findViewById(R.id.edtSearch);
        db = new DatabaseHelper(getActivity());
        name = new ArrayList<>();
        getList();
        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int i = name.indexOf(edtSearch.getText().toString());
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", arr.get(i).bus_id);
                bundle.putString("name", arr.get(i).bus_name);
                bundle.putString("start", arr.get(i).bus_start);
                bundle.putString("end", arr.get(i).bus_end);
                intent.putExtra("Mypack", bundle);
                startActivity(intent);
                edtSearch.setText("");
            }
        });
        return rootView;
    }

    public void test() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailScreen.class);
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

    public void getList() {
        List<Bus> info = db.getAllBus();
        for (Bus bus : info) {
            Bus tmp = new Bus();
            tmp.setBus_id(bus.getBus_id());
            tmp.setBus_name(bus.getBus_name());
            tmp.setBus_start(bus.getBus_start());
            tmp.setBus_end(bus.getBus_end());
            arr.add(tmp);
            name.add(bus.getBus_start());
        }
        adapterName = new FilterWithSpaceAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, name);
        edtSearch.setAdapter(adapterName);
        adapter = new ListAdapter(getActivity(), R.layout.item_listview, arr);
        lv.setAdapter(adapter);
        test();
    }

}
