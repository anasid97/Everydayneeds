package com.example.everydayneed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EverydayNeedDB";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_PHONE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(String name, String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SURNAME, surname);
        values.put(KEY_PHONE, phone);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateContact(int id, String name, String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SURNAME, surname);
        values.put(KEY_PHONE, phone);

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean deleteContact(int contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contactId)}) > 0;
    }


    public Contact getContactById(int contactId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{KEY_ID, KEY_NAME, KEY_SURNAME, KEY_PHONE},
                KEY_ID + "=?",
                new String[]{String.valueOf(contactId)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

        cursor.close();
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contactList;
    }
}
