package com.codingtive.sportapps.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    String strFilename;
    String strEvent;
    String dateEvent;

    public String getStrFilename() {
        return strFilename;
    }

    public void setStrFilename(String strFilename) {
        this.strFilename = strFilename;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public void setStrEvent(String strEvent) {
        this.strEvent = strEvent;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.strFilename);
        dest.writeString(this.strEvent);
        dest.writeString(this.dateEvent);
    }

    public Event() {
    }

    protected Event(Parcel in) {
        this.strFilename = in.readString();
        this.strEvent = in.readString();
        this.dateEvent = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
