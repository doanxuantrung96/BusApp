package com.example.sony.busapp.maps;

import java.util.List;

/**
 * Created by dolouis on 4/23/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);

}
