package com.kingdatasolutions.sintraba.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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



}


