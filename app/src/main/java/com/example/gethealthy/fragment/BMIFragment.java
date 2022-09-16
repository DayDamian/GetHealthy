package com.example.gethealthy.fragment;

import static com.example.gethealthy.R.color.fats;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gethealthy.R;

import java.util.Calendar;

import javax.annotation.Nullable;

public class BMIFragment extends Fragment {

    private TextView birthView, weightView, heightView, sexView, resultView, textResoult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b_m_i, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        Bundle bundle = this.getArguments();
        String birth = bundle.getString("birth");
        String weight = bundle.getString("weight");
        String height = bundle.getString("height");
        String sex = bundle.getString("sex");

        Integer age = calendar.get(Calendar.YEAR) - Integer.parseInt(birth);
        String ageString = String.valueOf(age);
        double weightNumber = Integer.parseInt(weight);
        double heightNumber = Integer.parseInt(height);
        double bmi = (weightNumber / (heightNumber * heightNumber))*10000;
        bmi = Math.round(bmi *10);
        bmi = bmi/10;
        String bmiString = String.valueOf(bmi);


        birthView = view.findViewById(R.id.birthView);
        weightView = view.findViewById(R.id.weightView);
        heightView = view.findViewById(R.id.heightView);
        sexView = view.findViewById(R.id.sexView);
        resultView = view.findViewById(R.id.resultView);
        textResoult = view.findViewById(R.id.textResoult);

        if (bmi < 16){
            textResoult.setText("Severe Thinness");
            textResoult.setTextColor(R.color.red);
        }else if(bmi >= 16 && bmi <= 17){
            textResoult.setText("Moderate Thinness");
            textResoult.setTextColor(R.color.red);
        }else if(bmi > 17 && bmi <= 18){
            textResoult.setText("Mild Thinness");
            textResoult.setTextColor(R.color.red);
        }else if(bmi > 18 && bmi <= 25){
            textResoult.setText("Normal");
        }else if(bmi > 25 && bmi <= 30){
            textResoult.setText("Overweight");
            textResoult.setTextColor(R.color.red);
        }else if(bmi > 30 && bmi <= 35){
            textResoult.setText("Obese Class I");
            textResoult.setTextColor(R.color.red);
        }else if(bmi > 35 && bmi <= 40){
            textResoult.setText("Obese Class II");
            textResoult.setTextColor(R.color.red);
        }else if(bmi >= 40){
            textResoult.setText("Obese Class III");
            textResoult.setTextColor(R.color.red);
        }
        birthView.setText("Age: "+ ageString);
        weightView.setText("Weight: "+ weight + "kg");
        heightView.setText("Height: "+ height + "cm");
        sexView.setText("Sex: "+ sex);
        resultView.setText("BMI:" + bmiString);

    }
}