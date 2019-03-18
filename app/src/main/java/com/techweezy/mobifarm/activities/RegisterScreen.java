package com.techweezy.mobifarm.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.db.DbContract;
import com.techweezy.mobifarm.db.DbHelper;

public class RegisterScreen extends AppCompatActivity {
    Button btn;
    EditText title, fname,sname,email,street,phone,password, con_pass;
    AppCompatSpinner spinnerRole;

    DbHelper dbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
//        FrameLayout contentFrame= (FrameLayout)findViewById(R.id.content_frame);
//        getLayoutInflater().inflate(R.layout.activity_register_screen,contentFrame);
//        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
//
//        navigationView.getMenu().getItem(1).setChecked(true);

        title=(EditText)findViewById(R.id.etTitle);
        fname=(EditText)findViewById(R.id.etFname);
        sname=(EditText)findViewById(R.id.etSName);
        email=(EditText)findViewById(R.id.etEmail);
        street=(EditText)findViewById(R.id.etStreet);
        phone=(EditText)findViewById(R.id.etPhone);
        password = (EditText)findViewById(R.id.etPass);
        con_pass= (EditText)findViewById(R.id.etconfirmPW);
        spinnerRole=(AppCompatSpinner)findViewById(R.id.role_spinner);

        btn=(Button)findViewById(R.id.registerBTN);

    }
    public  void onClickedOPtion(View v){
        switch (v.getId()){
            case R.id.registerBTN:
                saveData();
                break;

            case R.id.loginLink:
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                break;
        }
    }

    public  void saveData(){

        String title_str= title.getText().toString().trim();
        String fname_str=fname.getText().toString().trim();
        String sname_str= sname.getText().toString().trim();
        String email_str=email.getText().toString().trim();
        String street_str=street.getText().toString().trim();
        String phone_str=phone.getText().toString().trim();
        String password_str=password.getText().toString().trim();
        String confirmpass_str=con_pass.getText().toString().trim();
        String role_str=spinnerRole.getSelectedItem().toString();

        //checking for empty text fields
        if (TextUtils.isEmpty(fname_str) || TextUtils.isEmpty(sname_str) || TextUtils.isEmpty(title_str)
                || TextUtils.isEmpty(email_str) || TextUtils.isEmpty(street_str) || TextUtils.isEmpty(phone_str)
                || TextUtils.isEmpty(confirmpass_str) || TextUtils.isEmpty(password_str)){

            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        //validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
            email.setError("Please enter valid email!");
            Toast.makeText(this, "Invalid Email ", Toast.LENGTH_LONG).show();
            return;
        }

        //Checking if provided passwords do match
        if (! password_str.equals(confirmpass_str)){
            Toast.makeText(this, "Password don't match", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        try {
//            if (! dbHelper.isEmailExists(email_str)){

            contentValues.put(DbContract.UserTable.COLUMN_STREET,street_str);
            contentValues.put(DbContract.UserTable.COLUMN_MYPASSWORD,password_str);
            contentValues.put(DbContract.UserTable.COLUMN_USER_ROLE,role_str);
            contentValues.put(DbContract.UserTable.COLUMN_TITLE,title_str);
            contentValues.put(DbContract.UserTable.COLUMN_FNAME,fname_str);
            contentValues.put(DbContract.UserTable.COLUMN_SNAME,sname_str);
            contentValues.put(DbContract.UserTable.COLUMN_EMAIL,email_str);
            contentValues.put(DbContract.UserTable.COLUMN_PHONE,phone_str);

//                contentValues.put(DbContract.UserTable.COLUMN_PASSWORD,password_str);
//                contentValues.put(DbContract.UserTable.COLUMN_USER_ROLE,role_str);

                long newRow=database.insert(DbContract.UserTable.USERS_TABLE,null,contentValues);

                if (newRow !=-1){
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Log.i("ROLE","userRole is"+spinnerRole.getSelectedItem().toString());
                    startActivity(new Intent(getApplicationContext(),LoginScreen.class));
                }
                else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
//            }
//            else {
//                Toast.makeText(this, "User Record Exists", Toast.LENGTH_LONG).show();
//            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void clearFields(){
        title.setText("");
        fname.setText("");
        sname.setText("");
        email.setText("");
        street.setText("");
        phone.setText("");;
        password.setText("");
        con_pass.setText("");
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
