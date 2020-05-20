package com.example.sony.busapp.model;

public class Bus {
    public int bus_id;
    public String bus_name;
    public String bus_start;
    public String bus_end;

    public Bus() {

    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_start() {
        return bus_start;
    }

    public void setBus_start(String bus_start) {
        this.bus_start = bus_start;
    }

    public String getBus_end() {
        return bus_end;
    }

    public void setBus_end(String bus_end) {
        this.bus_end = bus_end;
    }
}
