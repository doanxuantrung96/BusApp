package com.example.sony.busapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.sony.busapp.adapter.SlidingMenuAdapter;
import com.example.sony.busapp.fragment.Fragment1;
import com.example.sony.busapp.fragment.Fragment2;
import com.example.sony.busapp.fragment.Fragment3;
import com.example.sony.busapp.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get intent
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        boolean check = data.getBoolean("check");
        Log.d("test", "ban da nhan: " + check);
        //Init component
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //Add item for sliding list
        listSliding.add(new ItemSlideMenu(R.drawable.ic_search, "Tìm Theo Tuyến Online"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_search, "Tìm Theo Tuyến Offline"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_map, "Tìm Theo Đường Đi"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_about, "Phản Hồi"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);
        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set title

        //item selected
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);
        //Display fragment 1 when start
        if (check ==true) {
            setTitle(listSliding.get(0).getTitle());
            replaceFragment(0);
        } else if(check==false){
            setTitle(listSliding.get(1).getTitle());
            replaceFragment(1);
        }
        //Hanlde on item click
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                setTitle(listSliding.get(position).getTitle());
                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace fragment
                replaceFragment(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace fragment

    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                break;
            default:
                fragment = new Fragment3();
                break;
        }

        if (null != fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
