package com.example.sony.busapp.maps;

/**
 * Created by dolouis on 4/23/2017.
 */

public class Distance {

    private String text;
    private int value;

    public Distance(String text, int value) {
        this.setText(text);
        this.setValue(value);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
