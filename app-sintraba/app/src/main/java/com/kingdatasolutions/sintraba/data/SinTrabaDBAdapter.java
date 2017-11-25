package com.kingdatasolutions.sintraba.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.datamodel.Department;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class SinTrabaDBAdapter {

    private SinTrabaDBHelper mHelper;
    private SQLiteDatabase mDatabase;

    public SinTrabaDBAdapter(Context context) {
        mHelper = new SinTrabaDBHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    //------------------------------->DEPARTMENT

    public void fillDepartment(ArrayList<Department> data) {
        deleteDepartment();
        String sql = "INSERT INTO " + SinTrabaDBHelper.TABLE_DEPARTMENT + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < data.size(); i++) {
            Department item = data.get(i);
            statement.clearBindings();
            statement.bindLong(1, item.getId());
            statement.bindLong(2, item.getIdOrder());
            statement.bindString(3, item.getNameSpa());
            statement.bindString(4, item.getNameEng());
            statement.bindString(5, item.getImageAddress());
            statement.execute();
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Department> getDepartmentList() {
        ArrayList<Department> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.DEPARTMENT_ID,
                SinTrabaDBHelper.DEPARTMENT_ID_ORDER,
                SinTrabaDBHelper.DEPARTMENT_NAME,
                SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS
        };

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_DEPARTMENT, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Department item = new Department();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID)));
                item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID_ORDER)));
                item.setNameSpa(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
                item.setImageAddress(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS)));
                listItem.add(item);
            }
            while (cursor.moveToNext());
        }
        return listItem;
    }

    public ArrayList<String> getDepartmentStringList() {
        ArrayList<String> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.DEPARTMENT_ID,
                SinTrabaDBHelper.DEPARTMENT_ID_ORDER,
                SinTrabaDBHelper.DEPARTMENT_NAME,
                SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS
        };
        String orderBy = SinTrabaDBHelper.DEPARTMENT_ID_ORDER + " ASC";

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_DEPARTMENT, columns, null, null, null, null, orderBy);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                listItem.add(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
            }
            while (cursor.moveToNext());
        }
        return listItem;
    }

    public Department getDepartment(int id) {
        Department itemDB = new Department();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.DEPARTMENT_ID,
                SinTrabaDBHelper.DEPARTMENT_ID_ORDER,
                SinTrabaDBHelper.DEPARTMENT_NAME,
                SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS
        };
        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_DEPARTMENT, columns, SinTrabaDBHelper.DEPARTMENT_ID + " = '" + id + "'", null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Department item = new Department();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID)));
                item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID_ORDER)));
                item.setNameSpa(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
                item.setImageAddress(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS)));
                itemDB = item;
            }
            while (cursor.moveToNext());
        }
        return itemDB;
    }

    public Department getDepartmentByOrder(int id_order) {
        Log.d("TOWATCH", id_order + "");
        Department itemDB = new Department();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.DEPARTMENT_ID,
                SinTrabaDBHelper.DEPARTMENT_ID_ORDER,
                SinTrabaDBHelper.DEPARTMENT_NAME,
                SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS
        };
        Cursor cursor = null;
        try {
            cursor = db.query(SinTrabaDBHelper.TABLE_DEPARTMENT, columns, SinTrabaDBHelper.DEPARTMENT_ID_ORDER + " = '" + id_order + "'", null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Department item = new Department();
                    item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID)));
                    item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_ID_ORDER)));
                    item.setNameSpa(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
                    item.setImageAddress(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_IMAGE_ADDRESS)));
                    itemDB = item;
                }
                while (cursor.moveToNext());
            }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }
        return itemDB;
    }

    public int deleteDepartment() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(SinTrabaDBHelper.TABLE_DEPARTMENT, null, null);
        return count;
    }
    
}


