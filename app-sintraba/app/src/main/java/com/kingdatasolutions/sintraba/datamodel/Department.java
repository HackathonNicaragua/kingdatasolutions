package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Department implements Parcelable {

    private int id;
    private int id_order;
    private String name;
    private String image_address;

    public Department() {

    }

    public Department(Parcel input) {
        id = input.readInt();
        id_order = input.readInt();
        name = input.readString();
        image_address = input.readString();
    }

    public Department(int id,
                      int id_order,
                      String name,
                      String image_address) {
        this.id_order = id_order;
        this.id = id;
        this.name = name;
        this.image_address = image_address;
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

    public void setNameSpa(String name_spa) {
        this.name = name;
    }

    public String getImageAddress() {
        return image_address;
    }

    public void setImageAddress(String image_address) {
        this.image_address = image_address;
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
        parcel.writeString(image_address);
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };
}