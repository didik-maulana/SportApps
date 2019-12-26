package com.codingtive.sportapps.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sport")
public class Sport implements Parcelable {
    public static final Parcelable.Creator<Sport> CREATOR = new Parcelable.Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel source) {
            return new Sport(source);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };
    @PrimaryKey
    @NonNull
    private String idSport = "";
    @ColumnInfo(name = "strSport")
    private String strSport;
    @ColumnInfo(name = "strSportThumb")
    private String strSportThumb;
    @ColumnInfo(name = "strSportDescription")
    private String strSportDescription;
    @ColumnInfo(name = "isFavorite")
    private Boolean isFavorite = false;

    public Sport() {
    }

    protected Sport(Parcel in) {
        this.idSport = in.readString();
        this.strSport = in.readString();
        this.strSportThumb = in.readString();
        this.strSportDescription = in.readString();
        this.isFavorite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public String getIdSport() {
        return idSport;
    }

    public void setIdSport(String idSport) {
        this.idSport = idSport;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrSportThumb() {
        return strSportThumb;
    }

    public void setStrSportThumb(String strSportThumb) {
        this.strSportThumb = strSportThumb;
    }

    public String getStrSportDescription() {
        return strSportDescription;
    }

    public void setStrSportDescription(String strSportDescription) {
        this.strSportDescription = strSportDescription;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idSport);
        dest.writeString(this.strSport);
        dest.writeString(this.strSportThumb);
        dest.writeString(this.strSportDescription);
        dest.writeValue(this.isFavorite);
    }
}
