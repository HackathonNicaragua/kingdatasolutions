package com.kingdatasolutions.sintraba.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Job;
import com.kingdatasolutions.sintraba.datamodel.JobCategory;
import com.kingdatasolutions.sintraba.datamodel.Setting;

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
            statement.bindString(3, item.getName());
            statement.bindString(4, item.getImageAddress());
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
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
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
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
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
                    item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.DEPARTMENT_NAME)));
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

    //------------------------------->JOB

    public void fillJob(ArrayList<Job> data) {
        deleteJobCategory();
        String sql = "INSERT INTO " + SinTrabaDBHelper.TABLE_JOB + " VALUES (?,?,?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < data.size(); i++) {
            Job item = data.get(i);
            statement.clearBindings();
            statement.bindLong(1, item.getId());
            statement.bindLong(2, item.getIdCategory());
            statement.bindLong(3, item.getIdDepartment());
            statement.bindLong(4, item.getIdCompany());
            statement.bindString(5, item.getName());
            statement.bindString(6, item.getDescription());

            statement.execute();
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Job> getJobList() {

        ArrayList<Job> listItem = new ArrayList<>();
        Job item = new Job();
        for (int i = 0; i < 3; i++) {
            item = new Job();
            item.setId(i);

            if (i == 0) {
                item.setIdCategory(1);
                item.setIdDepartment(1);
                item.setIdCompany(1);
                item.setName("Técnico mecánico automotriz");
                item.setDescription("Se requiere mecánico automotriz con certificación en ...");
            } else if (i == 1) {
                item.setIdCategory(2);
                item.setIdDepartment(2);
                item.setIdCompany(2);
                item.setName("Técnico electricista");
                item.setDescription("Se requiere mecánico automotriz con certificación en ...");
            } else if (i == 2) {
                item.setIdCategory(3);
                item.setIdDepartment(2);
                item.setIdCompany(3);
                item.setName("Técnico en refrigeración");
                item.setDescription("Se requiere mecánico automotriz con certificación en ...");
            }
            listItem.add(item);
        }

        return  listItem;
        /*
        ArrayList<Job> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.JOB_ID,
                SinTrabaDBHelper.JOB_ID_CATEGORY,
                SinTrabaDBHelper.JOB_ID_DEPARTMENT,
                SinTrabaDBHelper.JOB_ID_COMPANY,
                SinTrabaDBHelper.JOB_NAME,
                SinTrabaDBHelper.JOB_DESCRIPTION
        };

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_JOB, columns, null, null, null, null, SinTrabaDBHelper.JOB_CATEGORY_ID + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Job item = new Job();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_ID)));
                item.setIdCategory(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_ID_CATEGORY)));
                item.setIdDepartment(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_ID_DEPARTMENT)));
                item.setIdCompany(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_ID_COMPANY)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_NAME)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_DESCRIPTION)));
                listItem.add(item);
            }
            while (cursor.moveToNext());
        }
        return listItem;
        */
    }

    //------------------------------->JOB CATEGORY

    public void fillJobCategory(ArrayList<JobCategory> data) {
        deleteJobCategory();
        String sql = "INSERT INTO " + SinTrabaDBHelper.TABLE_JOB_CATEGORY + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < data.size(); i++) {
            JobCategory item = data.get(i);
            statement.clearBindings();
            statement.bindLong(1, item.getId());
            statement.bindString(2, item.getName());
            statement.bindString(3, item.getImageAddress());
            statement.execute();
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<JobCategory> getJobCategoryList() {
        ArrayList<JobCategory> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.JOB_CATEGORY_ID,
                SinTrabaDBHelper.JOB_CATEGORY_NAME,
                SinTrabaDBHelper.JOB_CATEGORY_IMAGE_ADDRESS
        };

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_JOB_CATEGORY, columns, null, null, null, null, SinTrabaDBHelper.JOB_CATEGORY_ID + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                JobCategory item = new JobCategory();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_NAME)));
                item.setImageAddress(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_IMAGE_ADDRESS)));
                listItem.add(item);
            }
            while (cursor.moveToNext());
        }
        return listItem;
    }

    public ArrayList<String> getJobCategoryStringList() {
        ArrayList<String> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.JOB_CATEGORY_ID,
                SinTrabaDBHelper.JOB_CATEGORY_NAME,
                SinTrabaDBHelper.JOB_CATEGORY_IMAGE_ADDRESS
        };
        String orderBy = SinTrabaDBHelper.JOB_CATEGORY_ID + " ASC";

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_JOB_CATEGORY, columns, null, null, null, null, orderBy);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listItem.add(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_NAME)));
                }
                while (cursor.moveToNext());
            }
        return listItem;
    }

    public JobCategory getJobCategory(int id) {
        JobCategory itemDB = new JobCategory();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.JOB_CATEGORY_ID,
                SinTrabaDBHelper.JOB_CATEGORY_NAME,
                SinTrabaDBHelper.JOB_CATEGORY_IMAGE_ADDRESS
        };
        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_JOB_CATEGORY, columns, SinTrabaDBHelper.JOB_CATEGORY_ID + " = '" + id + "'", null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                JobCategory item = new JobCategory();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_NAME)));
                item.setImageAddress(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.JOB_CATEGORY_IMAGE_ADDRESS)));
                itemDB = item;
            }
            while (cursor.moveToNext());
        }
        return itemDB;
    }

    public int deleteJobCategory() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(SinTrabaDBHelper.TABLE_JOB_CATEGORY, null, null);
        return count;
    }
    
    //------------------------------->SETTING

    public void createSetting(Setting item) {
        String sql = "INSERT INTO " + SinTrabaDBHelper.TABLE_SETTING + " VALUES (?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        statement.clearBindings();
        statement.bindLong(2, item.getIdOrder());
        statement.bindString(3, item.getName());
        statement.bindString(4, item.getValue());
        statement.execute();
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }


    public ArrayList<Setting> getSettingList() {
        ArrayList<Setting> listItem = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.SETTING_ID,
                SinTrabaDBHelper.SETTING_ID_ORDER,
                SinTrabaDBHelper.SETTING_NAME,
                SinTrabaDBHelper.SETTING_VALUE
        };

        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_SETTING, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Setting item = new Setting();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID)));
                item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID_ORDER)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_NAME)));
                item.setValue(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_VALUE)));
                listItem.add(item);
            }
            while (cursor.moveToNext());
        }
        return listItem;
    }

    public Setting getSetting(int id) {
        Setting itemDB = new Setting();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.SETTING_ID,
                SinTrabaDBHelper.SETTING_ID_ORDER,
                SinTrabaDBHelper.SETTING_NAME,
                SinTrabaDBHelper.SETTING_VALUE
        };
        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_SETTING, columns, SinTrabaDBHelper.SETTING_ID + " = '" + id + "'", null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Setting item = new Setting();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID)));
                item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID_ORDER)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_NAME)));
                item.setValue(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_VALUE)));
                itemDB = item;
            }
            while (cursor.moveToNext());
        }
        return itemDB;
    }

    public Setting getSetting(String name) {
        Setting itemDB = new Setting();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] columns = {
                SinTrabaDBHelper.SETTING_ID,
                SinTrabaDBHelper.SETTING_ID_ORDER,
                SinTrabaDBHelper.SETTING_NAME,
                SinTrabaDBHelper.SETTING_VALUE
        };
        Cursor cursor = db.query(SinTrabaDBHelper.TABLE_SETTING, columns, SinTrabaDBHelper.SETTING_NAME + " = '" + name + "'", null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Setting item = new Setting();
                item.setId(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID)));
                item.setIdOrder(cursor.getInt(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_ID_ORDER)));
                item.setName(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_NAME)));
                item.setValue(cursor.getString(cursor.getColumnIndex(SinTrabaDBHelper.SETTING_VALUE)));
                itemDB = item;
            }
            while (cursor.moveToNext());
        }
        return itemDB;
    }

    public int updateSetting(String name, String newValue) {
        Setting itemDB = new Setting();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SinTrabaDBHelper.SETTING_VALUE, newValue);
        String[] whereArgs = { name };
        int result = db.update(SinTrabaDBHelper.TABLE_SETTING, contentValues, SinTrabaDBHelper.SETTING_NAME + " =? ", whereArgs);
        return result;
    }

    public int deleteSetting() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(SinTrabaDBHelper.TABLE_SETTING, null, null);
        return count;
    }
}


