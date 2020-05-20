package com.example.sony.busapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.busapp.R;
import com.example.sony.busapp.model.Bus;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Bus> {

    public ListAdapter(Context context, int resource, ArrayList<Bus> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.item_listview, null);
        }

        Bus p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri

            TextView txt1 = (TextView) view.findViewById(R.id.tvId);
            if (position < 9){
                txt1.setText("0" + String.valueOf(p.bus_id));
            }else {
                txt1.setText(String.valueOf(p.bus_id));
            }

            TextView txt2 = (TextView) view.findViewById(R.id.tvInfo);
            txt2.setText(p.bus_name);

        }
        return view;
    }
}