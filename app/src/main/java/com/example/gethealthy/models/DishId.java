package com.example.gethealthy.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class DishId {
    @Exclude
    public String DishId;

    public <T extends  DishId>T withId(@NonNull final String id){
        this.DishId = id;
        return (T) this;
    }
}
