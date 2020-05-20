package com.example.sony.busapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.busapp.R;
import com.example.sony.busapp.model.ItemSlideMenu;

import java.util.List;

public class SlidingMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemSlideMenu> lstItem;


    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> lstItem) {
        this.context = context;
        this.lstItem = lstItem;
    }

    @Override
    public int getCount() {
        return lstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.item_sliding_menu, null);
        ImageView img = (ImageView)v.findViewById(R.id.item_img);
        TextView tv = (TextView)v.findViewById(R.id.item_title);

        ItemSlideMenu item = lstItem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());

        return v;
    }
}
