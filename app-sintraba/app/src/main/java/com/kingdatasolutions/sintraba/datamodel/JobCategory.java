package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class JobCategory implements Parcelable {

    private int id;
    private String name;
    private String image_address;

    public JobCategory() {

    }

    public JobCategory(Parcel input) {
        id = input.readInt();
        name = input.readString();
        image_address = input.readString();
    }

    public JobCategory(int id,
                 String name,
                 String image_address) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
        parcel.writeString(name);
        parcel.writeString(image_address);
    }

    public static final Creator<JobCategory> CREATOR = new Creator<JobCategory>() {
        public JobCategory createFromParcel(Parcel in) {
            return new JobCategory(in);
        }
        public JobCategory[] newArray(int size) {
            return new JobCategory[size];
        }
    };
}