package com.codingtive.sportapps.data.response;

import com.codingtive.sportapps.data.model.Event;

import java.util.ArrayList;

public class EventResponse {
    private ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> getEvents() {
        return events;
    }
}
