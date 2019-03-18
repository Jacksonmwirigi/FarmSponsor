package com.techweezy.mobifarm.model;

import android.graphics.Bitmap;

public class MFarmrRequests {

    public  String farmerName, pName,budget,proposal;
    public int id;
    public byte[] productImage;

    public MFarmrRequests() {
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getBudget() {
        return budget;
    }


    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getProposal() {
        return proposal;
    }


    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }
}

