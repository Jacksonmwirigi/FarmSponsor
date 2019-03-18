package com.techweezy.mobifarm.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.db.DbContract;
import com.techweezy.mobifarm.db.DbHelper;

import java.io.ByteArrayOutputStream;

public class CheckDetails extends MainActivity {
    private static final String TAG = "Details";
    Button btn;
    EditText productET, proposalET,budgetET;
    ImageView imagePrd;
    TextView name,phone,streetT;
    Spinner spin_status;

//    String farmerName;
    private static final int IMAGE_REQUEST = 1;
    public static final int REQUEST_CODE_FOR_PERMISSIONS=2;
//    String picturePath;
    String farmerName;

    Bitmap bitmap;
    byte [] pic;
    DbHelper dbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_check_details);


        FrameLayout contentFrame1=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_check_details,contentFrame1);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);

        navigationView.getMenu().getItem(0).setChecked(true);

        productET=(EditText)findViewById(R.id.ETproduct);
        proposalET=(EditText)findViewById(R.id.etProposal);
        budgetET=(EditText)findViewById(R.id.etBudget);
        imagePrd=(ImageView)findViewById(R.id.productImage);
        name=(TextView)findViewById(R.id.tvName);
        phone=(TextView)findViewById(R.id.tvPhone);
        streetT=(TextView)findViewById(R.id.streetTV) ;

        SharedPreferences sharedPreferences=
                getSharedPreferences("userData",MODE_PRIVATE);
        farmerName=sharedPreferences.getString("name","farmer");
        name.setText(farmerName);

        String myPhone=sharedPreferences.getString("phone","");
        phone.setText(myPhone);

        String street=sharedPreferences.getString("street","");
        streetT.setText(street);

        imagePrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage();
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                            .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOR_PERMISSIONS);
            }
        }

        btn=(Button)findViewById(R.id.btnUpdate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });

    }
    public void loadImage(){
        Intent imageIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntent, IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode==RESULT_OK){
                if (requestCode==IMAGE_REQUEST && data !=null){
                    Uri selectedImage = data.getData();

                    bitmap=decodeUri(selectedImage, 200);
                    imagePrd.setImageBitmap(bitmap);

                }
                else {
                    Toast.makeText(this, "No image data", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
    }

    //Convert and resize the selected  image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE){
        try {

            // Decode image size
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(selectedImage),null,options);
            int width_tmp = options.outWidth, height_tmp = options.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
           /*** Decoding image with inSampleSize***/
            BitmapFactory.Options options2=new BitmapFactory.Options();
            options2.inSampleSize=scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage),null,options2);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    /**Converting bitmap image to bytes []**/
    private byte[] getBitmapAsBytes(Bitmap bitmap){

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_FOR_PERMISSIONS){
            //You need to handle permission results, if user didn't allow them.
        }
    }

    public void updateDetails(){

        String product_name=productET.getText().toString().trim();
        String proposal_str=proposalET.getText().toString().trim();
        String budget_str=budgetET.getText().toString().trim();
//        String status_str=spin_status.getSelectedItem().toString();


        //checking for empty text fields
        if (TextUtils.isEmpty(proposal_str) || TextUtils.isEmpty(product_name) || TextUtils.isEmpty(budget_str)){
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        /**INSERTING SELECTED IMAGE ALONGSIDE OTHER DATA**/
        pic =getBitmapAsBytes(bitmap);
        if (pic ==null){
            Toast.makeText(this, "Please upload product image", Toast.LENGTH_LONG).show();
            return;
        }


        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DbContract.UserTable.COLUMN_FARMER_NAME,farmerName);
        contentValues.put(DbContract.UserTable.COLUMN_PRODUCT_NAME,product_name);
        contentValues.put(DbContract.UserTable.COLUMN_BUDGET,budget_str);
        contentValues.put(DbContract.UserTable.COLUMN_PROPOSAL,proposal_str);
        contentValues.put(DbContract.UserTable.COLUMN_PRODUCT_IMAGE,pic);
        contentValues.put(DbContract.UserTable.COLUMN_STATUS,"");

        long newRow=database.insert(DbContract.UserTable.TABLE_REQUSTS,null,contentValues);

        if (newRow !=-1){
            Toast.makeText(this, "Proposal Successful ", Toast.LENGTH_SHORT).show();
            clearFields();
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
        }
        else {
            Toast.makeText(this, "Failed to post", Toast.LENGTH_LONG).show();
        }
    }
    public void clearFields(){
        proposalET.setText("");
        productET.setText("");
        budgetET.setText("");
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
