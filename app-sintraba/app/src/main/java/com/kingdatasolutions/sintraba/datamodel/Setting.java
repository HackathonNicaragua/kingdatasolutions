package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Setting implements Parcelable {

    private int id;
    private int id_order;
    private String name;
    private String value;

    public Setting() {

    }

    public Setting(Parcel input) {
        id = input.readInt();
        id_order = input.readInt();
        name = input.readString();
        value = input.readString();
    }

    public Setting(int id,
                   int id_order,
                   String name,
                   String value) {
        this.id = id;
        this.id_order = id_order;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return id_order;
    }

    public void setIdOrder(int id_order) {
        this.id_order = id_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(id_order);
        parcel.writeString(name);
        parcel.writeString(value);
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        public Setting createFromParcel(Parcel in) {
            return new Setting(in);
        }
        public Setting[] newArray(int size) {
            return new Setting[size];
        }
    };
}