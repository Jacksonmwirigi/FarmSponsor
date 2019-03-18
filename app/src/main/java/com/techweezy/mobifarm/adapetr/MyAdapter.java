package com.techweezy.mobifarm.adapetr;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techweezy.mobifarm.R;
import com.techweezy.mobifarm.model.MFarmrRequests;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private View.OnClickListener onItemClickListener;

    private List<MFarmrRequests> listRequests;
    Context context;

    public MyAdapter(List<MFarmrRequests> mFarmrRequests) {
        this.listRequests = mFarmrRequests;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requests, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.bind(listRequests.get(position),listener);

        holder.farmerName.setText(listRequests.get(position).getFarmerName());
        holder.product_name.setText(listRequests.get(position).getpName());
        holder.budget.setText(listRequests.get(position).getBudget());
        holder.proposal_1.setText(listRequests.get(position).getProposal());
        holder.imageView.setImageBitmap(convvertToBitmap(listRequests.get(position).productImage));

//        Picasso.with(context)
//                .load(convvertToBitmap(listRequests.get(position).productImage))
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
       Log.i(MyAdapter.class.getSimpleName(),""+listRequests.size());
        return listRequests.size();
    }

    public  void setOnItemClickListener (View.OnClickListener clickListener){
        onItemClickListener=clickListener;
    }

    /**
     * ViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView farmerName, budget,product_name,proposal_1;
        public AppCompatImageView imageView;

        public MyViewHolder(final View itemView) {
            super(itemView);

            farmerName = (AppCompatTextView) itemView.findViewById(R.id.TVfarmer_name);
            budget = (AppCompatTextView) itemView.findViewById(R.id.TV_budget1);
            proposal_1 = (AppCompatTextView) itemView.findViewById(R.id.TV_proposal);
            product_name=(AppCompatTextView)itemView.findViewById(R.id.TVProduct_name);
            imageView=(AppCompatImageView)itemView.findViewById(R.id.prdt_image);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }

    }
    private Bitmap convvertToBitmap(byte [] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
