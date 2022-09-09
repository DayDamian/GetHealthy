package com.example.gethealthy.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class ProductsId {
    @Exclude
    public String ProductsId;

    public <T extends  ProductsId>T withId(@NonNull final String id){
        this.ProductsId = id;
        return (T) this;
    }
}
