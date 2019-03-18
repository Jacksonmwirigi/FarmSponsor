package com.techweezy.mobifarm.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.techweezy.mobifarm.model.User;

public final class DbContract {
    private DbContract(){
    }

    /* Inner class that defines the table contents-user table */
    public static abstract class UserTable implements BaseColumns {
//     USER TABLE
        public static final String USERS_TABLE = "testu";
        public static final String COLUMN_TITLE= "m_title";
        public static final String COLUMN_FNAME = "f_name";
        public static final String COLUMN_SNAME= "s_name";
        public static final String COLUMN_EMAIL = " email_address ";
        public static final String COLUMN_STREET= "street_address";
        public static final String COLUMN_PHONE= "phone_number";
        public static final String COLUMN_MYPASSWORD = " password ";
        public static final String COLUMN_USER_ROLE= "user_role";

   //  PRODUCT/PROFILE TABLE
        public static final String TABLE_REQUSTS = "newrequests";
        public static final  String COLUMN_FARMER_NAME ="farmerName";
        public static final String COLUMN_PRODUCT_NAME= "pName";
        public static final String COLUMN_BUDGET = "budget";
        public static final String COLUMN_PROPOSAL= "proposal";
        public static final String COLUMN_PRODUCT_IMAGE= "pImage";
        public static final String COLUMN_STATUS= "status";
        public static final String COLUMN_COMMENT= "comments";

        public static final String  CREATE_ENTRIES =
                "CREATE TABLE " + UserTable.USERS_TABLE + " (" +
                        UserTable._ID + " INTEGER PRIMARY KEY, " +
                        UserTable.COLUMN_TITLE + " TEXT, " +
                        UserTable.COLUMN_FNAME + " TEXT, " +
                        UserTable.COLUMN_SNAME + " TEXT, " +
                        UserTable.COLUMN_EMAIL + " TEXT, " +
                        UserTable.COLUMN_STREET+ " TEXT ," +
                        UserTable.COLUMN_PHONE +" INTEGER, " +
                        UserTable.COLUMN_MYPASSWORD + " TEXT, " +
                        UserTable.COLUMN_USER_ROLE + " TEXT )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + USERS_TABLE;
        public static final String DELETE_TABLE_PROFILE = "DROP TABLE IF EXISTS " + TABLE_REQUSTS;

        public static final String CREATE_PROFILE=
                "CREATE TABLE " + UserTable.TABLE_REQUSTS + " (" +
                        UserTable._ID + " INTEGER PRIMARY KEY, " +
                        UserTable.COLUMN_FARMER_NAME + " TEXT, " +
                        UserTable.COLUMN_PRODUCT_NAME + " TEXT, " +
                        UserTable.COLUMN_BUDGET + " TEXT," +
                        UserTable.COLUMN_PROPOSAL + " TEXT," +
                        UserTable.COLUMN_PRODUCT_IMAGE + " BLOB, " +
                        UserTable.COLUMN_STATUS + " TEXT, " +
                        UserTable.COLUMN_COMMENT+ " TEXT )";

    }

}
