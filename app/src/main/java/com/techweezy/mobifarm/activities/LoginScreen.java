package com.techweezy.mobifarm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.db.DbContract;
import com.techweezy.mobifarm.db.DbHelper;

public class LoginScreen extends AppCompatActivity {
    Button button;
    EditText myemail,mypass;

    DbHelper dbHelper=new DbHelper(this);
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        button=(Button)findViewById(R.id.loginBtn);
        myemail=(EditText)findViewById(R.id.etusername);
        mypass=(EditText)findViewById(R.id.etmyPassword);


    }
    public void onSelectedButton(View view){
        switch (view.getId()){
            case R.id.loginBtn:
                userLogin();
                break;

            case R.id.registerLink:
                startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
                break;
        }

    }
    /***USER LOGIN FUNCTION***/
    public void userLogin(){
        String email=myemail.getText().toString().trim();
        String password=mypass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        //validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            myemail.setError("Please enter valid email!");
            Toast.makeText(this, "Invalid Email ", Toast.LENGTH_LONG).show();
            return;
        }
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

        SQLiteDatabase db=dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.query(DbContract.UserTable.USERS_TABLE,// Selecting Table
                    projection,
                    //Selecting columns want to query
                    DbContract.UserTable.COLUMN_EMAIL + "=?",
                    new String[]{email},//Where clause
                    null, null, null);

            if (cursor != null && cursor.moveToFirst() &&cursor.getCount()>0){
                String myEMAIL=cursor.getString(4);
                String myFName= cursor.getString(2);
                String mySName= cursor.getString(3);
                String fullName= myFName +"  "+mySName;
                String phone=cursor.getString(6);
                String streetAd=cursor.getString(5);
                String role=cursor.getString(8);

                if (cursor.getString(4).equalsIgnoreCase(email) &&
                        cursor.getString(7).equalsIgnoreCase(password)){

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    sharedPreferences=getSharedPreferences("userData",MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();

                    editor.putString("my_email",myEMAIL);
                    editor.putString("name",fullName);
                    editor.putString("phone",phone);
                    editor.putString("street",streetAd);
                    editor.putString("user_role",role);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    clearFields();
                }
                else {
                    Toast.makeText(this, "Failed. Incorrect password or Email.",
                            Toast.LENGTH_LONG).show();
                }

            }
            else {
                Toast.makeText(this, "User not registered", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error Occurred!!"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void clearFields(){
        mypass.setText("");
        myemail.setText("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
       final  AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle("Exiting...");
       builder.setMessage("Do you want to Exit ?");
       builder.setPositiveButton("Take me Away", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               finish();
           }
       });
       builder.setNegativeButton("", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               dialog.dismiss();
           }
       });
       AlertDialog dialog=builder.create();
       dialog.show();


        super.onBackPressed();
    }
}
