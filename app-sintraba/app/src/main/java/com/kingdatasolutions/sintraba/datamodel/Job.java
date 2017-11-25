package com.kingdatasolutions.sintraba.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class Job implements Parcelable {

    private int id;
    private int id_category;
    private int id_department;
    private int id_company;
    private String name;
    private String description;

    public Job() {

    }

    public Job(Parcel input) {
        id = input.readInt();
        id_category = input.readInt();
        id_department = input.readInt();
        id_company = input.readInt();
        name = input.readString();
        description = input.readString();
    }

    public Job(int id,
               int id_category,
               int id_department,
               int id_company,
               String name,
               String description) {
        this.id = id;
        this.id_category = id_category;
        this.id_department = id_department;
        this.id_company = id_company;
        this.name = name;
        this.description = description;
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

    public int getIdCompany() {
        return id_company;
    }

    public void setIdCompany(int id_company) {
        this.id_company = id_company;
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
        parcel.writeInt(id_category);
        parcel.writeInt(id_department);
        parcel.writeInt(id_company);
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
