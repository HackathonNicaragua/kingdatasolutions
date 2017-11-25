package com.kingdatasolutions.sintraba.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class SinTrabaDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sintraba";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_JOB = "job";
    public static final String JOB_ID = "_id";
    public static final String JOB_NAME = "name";
    public static final String JOB_MUNICIPALLITY = "id_municipality";
    public static final String JOB_DESCRIPTION = "job_description";
    private static final String CREATE_JOB = "CREATE TABLE " + TABLE_JOB + " ( " +
            JOB_ID + " INTEGER PRIMARY KEY, " +
            JOB_MUNICIPALLITY + " INTEGER, " +
            JOB_NAME + " VARCHAR(255), " +
            JOB_DESCRIPTION + " VARCHAR(255))";
    private static final String DROP_JOB = "DROP TABLE  IF EXISTS " + TABLE_JOB;

    public static final String TABLE_JOB_SKILL = "job_skill";
    public static final String JOB_SKILL_ID = "_id";
    public static final String JOB_SKILL_NAME = "name";
    public static final String JOB_SKILL_PRIORITY = "priority";
    private static final String CREATE_JOB_SKILL = "CREATE TABLE " + TABLE_JOB_SKILL + " ( " +
            JOB_SKILL_ID + " INTEGER PRIMARY KEY, " +
            JOB_SKILL_NAME + " VARCHAR(255), " +
            JOB_SKILL_PRIORITY + " INTEGER) ";
    private static final String DROP_JOB_SKILL = "DROP TABLE  IF EXISTS " + TABLE_JOB_SKILL;

    public static final String TABLE_DEPARTMENT = "department";
    public static final String DEPARTMENT_ID = "_id";
    public static final String DEPARTMENT_ID_ORDER = "id_order";
    public static final String DEPARTMENT_NAME = "name";
    public static final String DEPARTMENT_IMAGE_ADDRESS = "image_address";
    private static final String CREATE_DEPARTMENT = "CREATE TABLE " + TABLE_DEPARTMENT + " ( " +
            DEPARTMENT_ID + " INTEGER PRIMARY KEY, " +
            DEPARTMENT_ID_ORDER + " INTEGER, " +
            DEPARTMENT_NAME + " VARCHAR(50), " +
            DEPARTMENT_IMAGE_ADDRESS + " VARCHAR(300))";
    private static final String DROP_DEPARTMENT = "DROP TABLE  IF EXISTS " + TABLE_DEPARTMENT;

    public static final String TABLE_JOB_CATEGORY = "category";
    public static final String JOB_CATEGORY_ID = "_id";
    public static final String JOB_CATEGORY_NAME = "name";
    public static final String JOB_CATEGORY_IMAGE_ADDRESS = "image_address";
    private static final String CREATE_JOB_CATEGORY = "CREATE TABLE " + TABLE_JOB_CATEGORY + " ( " +
            JOB_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
            JOB_CATEGORY_NAME + " VARCHAR(255), " +
            JOB_CATEGORY_IMAGE_ADDRESS + " VARCHAR(255)) ";
    private static final String DROP_JOB_CATEGORY = "DROP TABLE  IF EXISTS " + TABLE_JOB_CATEGORY;

    public static final String TABLE_SETTING = "setting";
    public static final String SETTING_ID = "_id";
    public static final String SETTING_ID_ORDER = "id_order";
    public static final String SETTING_NAME = "name";
    public static final String SETTING_VALUE = "value";
    private static final String CREATE_SETTING = "CREATE TABLE " + TABLE_SETTING + " ( " +
            SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SETTING_ID_ORDER + " INTEGER, " +
            SETTING_NAME + " VARCHAR(255), " +
            SETTING_VALUE + " VARCHAR(255))";
    private static final String DROP_SETTING = "DROP TABLE  IF EXISTS " + TABLE_SETTING;

    private Context context;

    public SinTrabaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_JOB);
            db.execSQL(CREATE_JOB_SKILL);
            db.execSQL(CREATE_DEPARTMENT);
            db.execSQL(CREATE_JOB_CATEGORY);
            db.execSQL(CREATE_SETTING);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL(DROP_JOB);
            db.execSQL(DROP_JOB_SKILL);
            db.execSQL(DROP_DEPARTMENT);
            db.execSQL(DROP_JOB_CATEGORY);
            db.execSQL(DROP_SETTING);
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
}
