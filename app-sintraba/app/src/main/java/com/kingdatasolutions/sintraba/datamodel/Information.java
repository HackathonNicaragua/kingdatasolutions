package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Information implements Parcelable {

    private int id;
    private int id_category;
    private int id_department;
    private String title;
    private String description;
    private String image_address;


    public Information() {

    }

    public Information(Parcel input) {
        id = input.readInt();
        id_category = input.readInt();
        id_department = input.readInt();
        title = input.readString();
        description = input.readString();
        image_address = input.readString();
    }

    public Information(int id,
                       int id_category,
                       int id_department,
                       String title,
                       String description,
                       String image_address) {
        this.id = id;
        this.id_category = id_category;
        this.id_department = id_department;
        this.title = title;
        this.description = description;
        this.image_address = image_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return id_category;
    }

    public void setIdCategory(int id_category) {
        this.id_category = id_category;
    }

    public int getIdDepartment() {
        return id_department;
    }

    public void setIdDepartment(int id_department) {
        this.id_department = id_department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        parcel.writeInt(id_category);
        parcel.writeInt(id_department);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image_address);
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };
}
