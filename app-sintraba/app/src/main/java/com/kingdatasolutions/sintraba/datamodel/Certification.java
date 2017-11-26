package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/26/17.
 */

public class Certification implements Parcelable {

    private int id;
    private String name;

    public Certification() {

    }

    public Certification(Parcel input) {
        id = input.readInt();
        name = input.readString();
    }

    public Certification(int id,
                      String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Creator<Certification> CREATOR = new Creator<Certification>() {
        public Certification createFromParcel(Parcel in) {
            return new Certification(in);
        }
        public Certification[] newArray(int size) {
            return new Certification[size];
        }
    };
}