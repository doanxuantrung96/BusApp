package com.example.sony.busapp.maps;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by dolouis on 4/23/2017.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;

}
