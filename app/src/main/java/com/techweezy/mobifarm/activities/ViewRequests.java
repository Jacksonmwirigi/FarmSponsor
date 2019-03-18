package com.techweezy.mobifarm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.adapetr.MyAdapter;
import com.techweezy.mobifarm.db.DbHelper;
import com.techweezy.mobifarm.model.MFarmrRequests;

import java.util.ArrayList;
import java.util.List;

public class ViewRequests extends MainActivity {
    public static final String EXTRA_DATA= "SELECTED_DATA";
    public static final String IMAGE_BITMAP="ImageByte";
    private static final int REQUEST_RESPONSE = 1;

    RecyclerView recyclerView;
    private List<MFarmrRequests> listRequests;
//    private OnItemClickListener listener;
    private MyAdapter myAdapter;
    DbHelper db = new DbHelper(this);
    Button approveBtn,rejectBtn;

    /**Setting on click listener on the recyclerView items*/
    private  View.OnClickListener onItemClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder)view.getTag();
            int position =viewHolder.getAdapterPosition();
            MFarmrRequests item=listRequests.get(position);
            String pname=item.getpName();
            String farmername=item.getFarmerName();
            String budget= item.getBudget();
            String proposal =item.getProposal();
            byte[] imageBytes= item.getProductImage();

            /**Passing Data to Next Activity-clickked view**/
            ArrayList<String>arrayList=new ArrayList<>();
            arrayList.add(pname);
            arrayList.add(farmername);
            arrayList.add(budget);
            arrayList.add(proposal);

                Intent intent = new Intent(getApplicationContext(), FundFarmer.class);
                intent.putExtra(EXTRA_DATA,new String[]{pname,farmername,budget,proposal});
                intent.putExtra(IMAGE_BITMAP,imageBytes);
                startActivityForResult(intent,REQUEST_RESPONSE);
            Toast.makeText(ViewRequests.this, "You Selected"+pname,
                    Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_requests);
        FrameLayout frameLayout1=(FrameLayout)findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_view_requests,frameLayout1);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);

        /** Initializing request list adapter **/
        listRequests = new ArrayList<>();
        myAdapter = new MyAdapter(listRequests);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
        RecyclerView.LayoutManager recyce = new
                LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(recyce);
        MyAdapter myAdapter = new MyAdapter(listRequests);
        recyclerView.setAdapter(myAdapter);

        /**Create and set OnItemClickListener to the adapter.**/
        myAdapter.setOnItemClickListener(onItemClickListener);
        getDataFromSQLite();

    }

    /**
     * This method is to fetch all requests records from SQLite database
     */
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listRequests.clear();
                listRequests.addAll(db.getALLREquests());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                myAdapter.notifyDataSetChanged();
            }
        }.execute();

    }
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
