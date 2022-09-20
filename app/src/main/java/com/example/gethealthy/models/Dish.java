package com.example.gethealthy.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Dish extends com.example.gethealthy.models.DishId{

    DocumentReference userID;

    public DocumentReference getUserID() {
        return userID;
    }

    public void setUserID(DocumentReference userID) {
        this.userID = userID;
    }

    public DocumentReference getProductID() {
        return productID;
    }

    public void setProductID(DocumentReference productID) {
        this.productID = productID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    DocumentReference productID;
    Timestamp date;
    Double amount;

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    Double kcal;

    public Dish() {
    }

    public Dish(DocumentReference userID, DocumentReference productID, Timestamp date, Double amount, Double kcal) {
        this.userID = userID;
        this.productID = productID;
        this.date = date;
        this.amount = amount;
        this.kcal = kcal;
    }


}
