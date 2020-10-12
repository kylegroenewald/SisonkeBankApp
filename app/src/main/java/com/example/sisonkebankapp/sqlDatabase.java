package com.example.sisonkebankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlDatabase extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "SisonkeBanke";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";


    // User Table Columns
    private static final String KEY_USER_ID = "userId";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_EMAIL = "email";
    private static final String USER_GENDER = "gender";
    private static final String USER_MOBILE = "mobile";
    private static final String USER_PASSWORD = "password";
    private static final String CURRENT_ACCOUNT = "currentAccount";
    private static final String SAVINGS_ACCOUNT = "savingsAccount";

    private static sqlDatabase sInstance;

    public static synchronized sqlDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new sqlDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    public sqlDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating the database and tables.
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_FIRST_NAME + " VARCHAR," +
                USER_LAST_NAME + " VARCHAR, " +
                USER_EMAIL + " VARCHAR, " +
                USER_GENDER + " VARCHAR, " +
                USER_MOBILE + " VARCHAR, " +
                USER_PASSWORD + " VARCHAR, " +
                CURRENT_ACCOUNT + " INTEGER NOT NULL, " +
                SAVINGS_ACCOUNT + " INTEGER NOT NULL " +
                ")";

        //Executing the sql query
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //Simplest implementation is to drop all old tables and recreate them if an upgrade is needed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    public boolean addUser(UserDetails ud){
        /*
        This method is used to enter a new users details into the database
        It makes use of a UserDetails class in order to get the data from the registration class.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_FIRST_NAME, ud.getUser_FirstName());
        cv.put(USER_LAST_NAME, ud.getUser_LastName());
        cv.put(USER_EMAIL, ud.getUser_Email());
        cv.put(USER_MOBILE, ud.getUser_Mobile());
        cv.put(USER_GENDER, ud.getUser_Gender());
        cv.put(USER_PASSWORD, ud.getUser_Password());
        cv.put(CURRENT_ACCOUNT, ud.getCurrentAccount());
        cv.put(SAVINGS_ACCOUNT, ud.getSavingsAccount());
        long insert = db.insert(TABLE_USERS, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public String getUserEmail(SQLiteDatabase db, String email){
        //returns the email if the email entered is in the database. Used to check if a user already exists
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(USER_EMAIL));
        } else return "";
    }

    public String getUserPassword(SQLiteDatabase db, String email){
        //returns users password based on the email entered. Used to check if the password entered is correct.
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
        } else return "";
    }

    public String getUserFirstName(SQLiteDatabase db, int ID){
        //returns the users first name based on the user ID
        String userID = ID + "";
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {userID});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME));
        } else return "";
    }

    public String getUserLastName(SQLiteDatabase db, int ID){
        //returns the users last name based on the user ID
        String userID = ID + "";
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {userID});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(USER_LAST_NAME));
        } else return "";
    }

    public int getUserID(SQLiteDatabase db, String email){
        //returns the users ID based on the email
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
        } else return -1;
    }

    public int getCurrentAccount(SQLiteDatabase db, int ID){
        //returns the users current account balance based on the user ID
        String userID = ID + "";
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {userID});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(CURRENT_ACCOUNT)));
        } else return -1;
    }

    public int getSavingsAccount(SQLiteDatabase db, int ID){
        //returns the users savings account balance based on the user ID
        String userID = ID + "";
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {userID});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(SAVINGS_ACCOUNT)));
        } else return -1;
    }

    public void updateBalance(SQLiteDatabase db, int ID, int newCurrent, int newSavings){
        //updates the users accounts when a transfer is performed
        String query = "UPDATE "+ TABLE_USERS + " SET " + CURRENT_ACCOUNT + " = " + newCurrent +", "+SAVINGS_ACCOUNT + " = " + newSavings + " WHERE " + KEY_USER_ID + " = " + ID;
        db.execSQL(query);
    }
}

