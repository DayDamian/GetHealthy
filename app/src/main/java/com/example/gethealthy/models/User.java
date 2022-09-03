package com.example.gethealthy.models;

public class User {

    String fullName, age, email;

    public User(){

    }

    public User(String fullName, String age, String email){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }

    public String getFullName(){return fullName;}

    public String getAge(){return age;}

    public String getEmail(){return email;}

    public void setFullName(){this.fullName = fullName;}

    public void setAge(){this.age = age;}

    public void setEmail(){this.email = email;}

}
