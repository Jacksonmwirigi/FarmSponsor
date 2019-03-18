package com.techweezy.mobifarm.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
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

public class FundFarmer extends MainActivity {
    TextView name,budgetTV,propTV,proddt;
    EditText commetsET;
    Spinner spin_status;
    ImageView productImage;
    Button fundBT;
    String prdname;
    String fmname;
    DbHelper dbHelper=new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fund_farmer);
        FrameLayout contentFrame= (FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_fund_farmer,contentFrame);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);

        propTV=(TextView)findViewById(R.id.TV_proposal) ;
        budgetTV=(TextView)findViewById(R.id.TV_budget1);
        proddt=(TextView)findViewById(R.id.TVProduct_name);
        name=(TextView)findViewById(R.id.TVfarmer_name);
        spin_status=(Spinner)findViewById(R.id.status_spinner);
        fundBT=(Button)findViewById(R.id.fund_btn);
        commetsET=(EditText)findViewById(R.id.comment);
        productImage=(ImageView)findViewById(R.id.prdt_image);

        fundBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveFundingReq();
            }
        });

        String [] selectedRequest=  getIntent().getStringArrayExtra(ViewRequests.EXTRA_DATA);
        byte [] imageBytes=getIntent().getByteArrayExtra(ViewRequests.IMAGE_BITMAP);

        Bitmap bitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        prdname=selectedRequest[0];
        fmname=selectedRequest[1];
        String budgetnm=selectedRequest[2];
        String proposal=selectedRequest[3];

        name.setText(fmname);
        proddt.setText(prdname);
        budgetTV.setText(budgetnm);
        propTV.setText(proposal);
        productImage.setImageBitmap(bitmap);

    }

    public  void approveFundingReq(){
        String comment_str =commetsET.getText().toString().trim();
        String status_str=spin_status.getSelectedItem().toString();

        if (TextUtils.isEmpty(comment_str)||TextUtils.isEmpty(status_str)){
            Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db =dbHelper.getReadableDatabase();

        ContentValues contentValues=new ContentValues();
        try {
            contentValues.put(DbContract.UserTable.COLUMN_STATUS,status_str);
            contentValues.put(DbContract.UserTable.COLUMN_COMMENT,comment_str);

            String selection = DbContract.UserTable.COLUMN_PRODUCT_NAME + " LIKE ?";
            String [] selectionArgs = { prdname };
            Log.i("PNAME IS ","JJJ"+prdname);

            int count =db.update(DbContract.UserTable.TABLE_REQUSTS,
                    contentValues,
                    selection,
                    selectionArgs);
            if (count >0){
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "OOPS!! Update Failed", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

