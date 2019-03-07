package com.example.swiperecyclerview;

public class Item {
    private String header;
    private String description;

    public Item(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }
}
