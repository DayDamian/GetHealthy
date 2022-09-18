package com.example.gethealthy.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gethealthy.R;

import java.util.Calendar;

import javax.annotation.Nullable;


public class CaloriesFragment extends Fragment {

    private TextView sexView2, ageView2, weightView2, heightView2, activityView2, targetView2, bmrView, bmr2View, dailyView, addView, wkcalView, wgView, tkcalView, tgView, bkcalView, bgView, textView58;
    private ProgressBar circleCarbon, circleCarbon2, circleCarbon3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        Bundle bundle = this.getArguments();
        String sex = bundle.getString("sex");
        String birth = bundle.getString("birth");
        String weight = bundle.getString("weight");
        String height = bundle.getString("height");
        String activity = bundle.getString("gym");
        String target = bundle.getString("diet");

        sexView2 = view.findViewById(R.id.sexView2);
        ageView2 = view.findViewById(R.id.ageView2);
        weightView2 = view.findViewById(R.id.weightView2);
        heightView2 = view.findViewById(R.id.heightView2);
        activityView2 = view.findViewById(R.id.activityView2);
        targetView2 = view.findViewById(R.id.targetView2);
        textView58 = view.findViewById(R.id.textView58);

        bmrView = view.findViewById(R.id.bmrView);
        bmr2View = view.findViewById(R.id.bmr2View);
        dailyView = view.findViewById(R.id.dailyView);
        addView = view.findViewById(R.id.addView);
        wkcalView = view.findViewById(R.id.wkcalView);
        wgView = view.findViewById(R.id.wgView);
        tkcalView = view.findViewById(R.id.tkcalView);
        tgView = view.findViewById(R.id.tgView);
        bkcalView = view.findViewById(R.id.bkcalView);
        bgView = view.findViewById(R.id.bgView);


        sexView2.setText(sex);
        weightView2.setText(weight + "kg");
        heightView2.setText(height + "cm");
        activityView2.setText(activity);
        targetView2.setText(target);

        circleCarbon = view.findViewById(R.id.circleCarbon);
        circleCarbon.setProgress(50);

        circleCarbon2 = view.findViewById(R.id.circleCarbon2);
        circleCarbon2.setProgress(25);

        circleCarbon3 = view.findViewById(R.id.circleCarbon3);
        circleCarbon3.setProgress(25);

        Integer age = calendar.get(Calendar.YEAR) - Integer.parseInt(birth);
        String ageString = String.valueOf(age);
        ageView2.setText(ageString);
        double A = age;
        double S;
        double R = 0;
        double W = Integer.parseInt(weight);
        double H = Integer.parseInt(height);

        if (sex == "Male"){
            S = Math.round((9.99*W)+(6.25*H)-(4.92*A)+5);
        }else {
            S =  Math.round((9.99*W)+(6.25*H)-(4.92*A)-161);
        }

        if (activity == "Sedentary: Little or no exercise"){
            R = S*1.2;
        }else if (activity == "Light: Exercise 1-3 times/week"){
            R = S*1.35;
        }else if (activity == "Medium: Exercise 3-4 times/week"){
            R = S*1.55;
        }else if (activity == "Very Active: Exercise 6-7 times/week"){
            R = S*1.75;
        }else if (activity == "Extra Active: Exercise daily, or physical job"){
            R = S*1.95;
        }

        if (target == "I want to keep my weight"){
            bmrView.setText(String.valueOf(Math.round(S))+ " calories per day");
            bmr2View.setText(String.valueOf(Math.round(R))+ " calories per day");
            dailyView.setText(String.valueOf(Math.round(R))+ " calories per day");
            wkcalView.setText(String.valueOf(Math.round((R*0.80)*0.50))+ "kcal");
            wgView.setText(String.valueOf(Math.round(((R*0.80)*0.50)/4))+ "g");
            tkcalView.setText(String.valueOf(Math.round((R*0.80)*0.25))+ "kcal");
            tgView.setText(String.valueOf(Math.round(((R*0.80)*0.25)/9))+ "g");
            bkcalView.setText(String.valueOf(Math.round((R*0.80)*0.25))+ "kcal");
            bgView.setText(String.valueOf(Math.round(((R*0.80)*0.25)/4))+ "g");
            addView.setText("");
            textView58.setText("If you want to keep weight, you should eat around:");

        }else if(target == "I want to lose weight"){
            bmrView.setText(String.valueOf(Math.round(S))+ " calories per day");
            bmr2View.setText(String.valueOf(Math.round(R))+ " calories per day");
            dailyView.setText(String.valueOf(Math.round(R*0.80))+ " calories per day");
            addView.setText("- "+String.valueOf(Math.round(R-(R*0.80)))+ " calories per day");
            wkcalView.setText(String.valueOf(Math.round((R*0.80)*0.50))+ "kcal");
            wgView.setText(String.valueOf(Math.round(((R*0.80)*0.50)/4))+ "g");
            tkcalView.setText(String.valueOf(Math.round((R*0.80)*0.25))+ "kcal");
            tgView.setText(String.valueOf(Math.round(((R*0.80)*0.25)/9))+ "g");
            bkcalView.setText(String.valueOf(Math.round((R*0.80)*0.25))+ "kcal");
            bgView.setText(String.valueOf(Math.round(((R*0.80)*0.25)/4))+ "g");
            textView58.setText("If you want to lose weight, you should eat around:");

        }else if(target == "I want to gain weight"){
            bmrView.setText(String.valueOf(Math.round(S))+ " calories per day");
            bmr2View.setText(String.valueOf(Math.round(R))+ " calories per day");
            dailyView.setText(String.valueOf(Math.round(R*1.20))+ " calories per day");
            addView.setText("+ "+String.valueOf(Math.round((R*1.20)-R))+ " calories per day");
            wkcalView.setText(String.valueOf(Math.round((R*1.20)*0.50))+ "kcal");
            wgView.setText(String.valueOf(Math.round(((R*1.20)*0.50)/4))+ "g");
            tkcalView.setText(String.valueOf(Math.round((R*1.20)*0.25))+ "kcal");
            tgView.setText(String.valueOf(Math.round(((R*1.20)*0.25)/9))+ "g");
            bkcalView.setText(String.valueOf(Math.round((R*1.20)*0.25))+ "kcal");
            bgView.setText(String.valueOf(Math.round(((R*1.20)*0.25)/4))+ "g");
            textView58.setText("If you want to gain weight, you should eat around:");


        }


    }
}