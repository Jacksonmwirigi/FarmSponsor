package com.techweezy.mobifarm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.techweezy.mobifarm.model.MFarmrRequests;
import com.techweezy.mobifarm.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public  class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "testnew.db";

    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.UserTable.CREATE_ENTRIES);
        db.execSQL(DbContract.UserTable.CREATE_PROFILE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DbContract.UserTable.DELETE_TABLE);
        db.execSQL(DbContract.UserTable.DELETE_TABLE_PROFILE);
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**AUTHENTICATION VIA THE MODEL CLASS**/
    public User authenticate(User user){

        SQLiteDatabase database = this.getReadableDatabase();
        // Defining projection that specifies which columns from the database
        String [] projection={
                DbContract.UserTable._ID,
                DbContract.UserTable.COLUMN_TITLE,
                DbContract.UserTable.COLUMN_FNAME,
                DbContract.UserTable.COLUMN_SNAME,
                DbContract.UserTable.COLUMN_EMAIL,
                DbContract.UserTable.COLUMN_STREET,
                DbContract.UserTable.COLUMN_PHONE,
                DbContract.UserTable.COLUMN_MYPASSWORD,
                DbContract.UserTable.COLUMN_USER_ROLE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DbContract.UserTable.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {user.emailAddress};
        Cursor cursor=database.query(DbContract.UserTable.USERS_TABLE,projection,
                selection,selectionArgs,null,null,null);

        if (cursor !=null &&cursor.moveToFirst() &&cursor.getCount()>0){
            //if cursor has value then in user database
            // there is user associated with this given email
            User user1=new User(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8));

            if (user1.emailAddress.equalsIgnoreCase(user.emailAddress)){
                return user1;
            }
        }
        //if user password does not matches or there is no record with the provided email
        return  null;

    }
/**CHECKING IF USER EMAIL EXISTS IS REGISTERED **/
    public boolean isEmailExists(String email) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DbContract.UserTable.USERS_TABLE,// Selecting Table
                new String[]{DbContract.UserTable.COLUMN_TITLE,
                        DbContract.UserTable.COLUMN_FNAME,
                        DbContract.UserTable.COLUMN_SNAME,
                        DbContract.UserTable.COLUMN_EMAIL,
                        DbContract.UserTable.COLUMN_STREET,
                        DbContract.UserTable.COLUMN_PHONE,
                        DbContract.UserTable.COLUMN_MYPASSWORD,
                        DbContract.UserTable.COLUMN_USER_ROLE},//Selecting columns want to query
                DbContract.UserTable.COLUMN_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user
            // associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    /***GETTING ALL THE REGISTERED REQUESTS **/
    public List<MFarmrRequests> getALLREquests(){
        List<MFarmrRequests> requestsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DbContract.UserTable.TABLE_REQUSTS + " ORDER BY " +
                DbContract.UserTable.COLUMN_FARMER_NAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MFarmrRequests farmrRequests = new MFarmrRequests();
                farmrRequests.setId(cursor.getInt(cursor.getColumnIndex(DbContract.UserTable._ID)));
                farmrRequests.setFarmerName(cursor.getString(cursor.
                        getColumnIndex(DbContract.UserTable.COLUMN_FARMER_NAME)));
                farmrRequests.setpName(cursor.getString(cursor.
                        getColumnIndex(DbContract.UserTable.COLUMN_PRODUCT_NAME)));
                farmrRequests.setBudget(cursor.getString(cursor.
                        getColumnIndex(DbContract.UserTable.COLUMN_BUDGET)));
                farmrRequests.setProposal(cursor.getString(cursor.
                        getColumnIndex(DbContract.UserTable.COLUMN_PROPOSAL)));
                farmrRequests.setProductImage(cursor.getBlob(cursor.
                        getColumnIndex(DbContract.UserTable.COLUMN_PRODUCT_IMAGE)));

                requestsList.add(farmrRequests);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        return requestsList;
    }

}
