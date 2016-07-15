package com.task.vasilyevanton.task;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.task.vasilyevanton.task.pojos.PeopleData;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String LOG_TAG = DataBaseHandler.class.getName();

    private static final String DB_NAME = "PeopleDB.sqlite";
    private static final String DB_FOLDER = "/data/data/" + App.getInstance().getPackageName() + "/databases/";
    private static final String DB_PATH = DB_FOLDER + DB_NAME;
    private static final String DB_ASSETS_PATH = "db/" + DB_NAME;
    private static final int DB_VERSION = 3;
    private static final int DB_FILES_COPY_BUFFER_SIZE = 8192;

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "_id";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_POSITION = "position";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_AGE = "age";
    private static final String KEY_SALARY = "salary";

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static boolean isInitialized() {

        SQLiteDatabase checkDB = null;
        Boolean correctVersion = false;

        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            correctVersion = checkDB.getVersion() == DB_VERSION;
        } catch (SQLiteException e) {
            Log.w(LOG_TAG, e.getMessage());
        } finally {
            if (checkDB != null)
                checkDB.close();
        }

        return checkDB != null && correctVersion;
    }

    private static void copyInitialDBfromAssets() {

        Context appContext = App.getInstance().getApplicationContext();
        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            inStream = new BufferedInputStream(appContext.getAssets().open(
                    DB_ASSETS_PATH), DB_FILES_COPY_BUFFER_SIZE);
            File dbDir = new File(DB_FOLDER);
            if (!dbDir.exists())
                dbDir.mkdir();
            outStream = new BufferedOutputStream(new FileOutputStream(DB_PATH),
                    DB_FILES_COPY_BUFFER_SIZE);

            byte[] buffer = new byte[DB_FILES_COPY_BUFFER_SIZE];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            outStream.flush();
            outStream.close();
            inStream.close();

        } catch (IOException ex) {
            // Что-то пошло не так
        } finally {
            IOUtils.closeQuietly(outStream);
            IOUtils.closeQuietly(inStream);
        }
    }

    public static void Initialize() {
        if (!isInitialized()) {
            copyInitialDBfromAssets();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addPeople(PeopleData users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, users.getFullName());
        values.put(KEY_POSITION, users.getPosition());
        values.put(KEY_DEPARTMENT, users.getDepartment());
        values.put(KEY_AGE, users.getAge());
        values.put(KEY_SALARY, users.getSalary());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<PeopleData> getPeoplesDepartment(String department) {
        ArrayList<PeopleData> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(3).equals(department)) {
                    PeopleData contact = new PeopleData();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setFullName(cursor.getString(1));
                    contact.setPosition(cursor.getString(2));
                    contact.setDepartment(cursor.getString(3));
                    contact.setAge(cursor.getString(4));
                    contact.setSalary(cursor.getString(5));
                    contactList.add(contact);
                }

            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public ArrayList<PeopleData> getPeoples() {
        ArrayList<PeopleData> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PeopleData contact = new PeopleData();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFullName(cursor.getString(1));
                contact.setPosition(cursor.getString(2));
                contact.setDepartment(cursor.getString(3));
                contact.setAge(cursor.getString(4));
                contact.setSalary(cursor.getString(5));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public int updateContact(PeopleData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, user.getFullName());
        values.put(KEY_POSITION, user.getPosition());
        values.put(KEY_DEPARTMENT, user.getDepartment());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_SALARY, user.getSalary());
        return db.update(TABLE_USERS, values, KEY_ID + " = ?", new String[] { String.valueOf(user.getId()) });
    }
}

