package com.example.gethealthy.models;

public class Product extends com.example.gethealthy.models.ProductsId {

    String category;
    Double carbs;
    Double fats;
    Double fats_sat;
    Double kcal;
    Double protein;
    Double sugars;
    String name;
    String _id; //niepotrzebne, jedynie po to by zniknely bledy


    public Product() {
    }

    public Product(String category, Double carbs, Double fats, Double fats_sat, Double kcal, Double protein, Double sugars, String name, String _id) {
        this.category = category;
        this.carbs = carbs;
        this.fats = fats;
        this.fats_sat = fats_sat;
        this.kcal = kcal;
        this.protein = protein;
        this.sugars = sugars;
        this.name= name;
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getFats_sat() {
        return fats_sat;
    }

    public void setFats_sat(Double fats_sat) {
        this.fats_sat = fats_sat;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getSugars() {
        return sugars;
    }

    public void setSugars(Double sugars) {
        this.sugars = sugars;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}

