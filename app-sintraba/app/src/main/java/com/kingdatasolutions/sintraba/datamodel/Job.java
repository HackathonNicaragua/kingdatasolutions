package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Job implements Parcelable {

    private int id;
    private int id_municipality;
    private String name;
    private String description;

    public Job() {

    }

    public Job(Parcel input) {
        id = input.readInt();
        id_municipality = input.readInt();
        name = input.readString();
        description = input.readString();
    }

    public Job(int id,
               int id_municipality,
               String name,
               String description) {
        this.id = id;
        this.id_municipality = id_municipality;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMunicipality() {
        return id_municipality;
    }

    public void setIdMunicipality(int id_municipality) {
        this.id_municipality = id_municipality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(id_municipality);
        parcel.writeString(name);
        parcel.writeString(description);
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
