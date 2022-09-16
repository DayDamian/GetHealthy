package com.example.gethealthy.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gethealthy.R;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class HealthyFragment extends Fragment implements View.OnClickListener {

    private Button bmiButton, calorieButton;
    private Spinner spinnerSex3, spinnerGym, spinnerDiet;
    private EditText checkBirth, checkWeight, checkHeight;

    final ArrayList<String> spinnerListSex = new ArrayList<>();
    final ArrayList<String> spinnerListGym = new ArrayList<>();
    final ArrayList<String> spinnerListDiet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_healthy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkBirth = view.findViewById(R.id.checkBirth);
        checkWeight = view.findViewById(R.id.checkWeight);
        checkHeight = view.findViewById(R.id.checkHeight);

        spinnerSex3 = view.findViewById(R.id.spinnerSex3);
        spinnerGym = view.findViewById(R.id.spinnerGym);
        spinnerDiet = view.findViewById(R.id.spinnerDiet);

        spinnerListSex.add("Male");
        spinnerListSex.add("Female");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerListSex);
        spinnerSex3.setAdapter(adapter);

        spinnerListGym.add("Sedentary: Little or no exercise");
        spinnerListGym.add("Light: Exercise 1-3 times/week");
        spinnerListGym.add("Medium: Exercise 3-4 times/week");
        spinnerListGym.add("Very Active: Exercise 6-7 times/week");
        spinnerListGym.add("Extra Active: Exercise daily, or physical job");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerListGym);
        spinnerGym.setAdapter(adapter2);

        spinnerListDiet.add("I want to lose weight");
        spinnerListDiet.add("I want to keep my weight");
        spinnerListDiet.add("I want to gain weight");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerListDiet);
        spinnerDiet.setAdapter(adapter3);

        bmiButton = view.findViewById(R.id.bmiButton);
        bmiButton.setOnClickListener(this);
        calorieButton = view.findViewById(R.id.calorieButton);
        calorieButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bmiButton:
                bmiCheck();
                break;
            case R.id.calorieButton:
                calorieCheck();
                break;
        }
    }

    private void calorieCheck() {
        String birth = checkBirth.getText().toString().trim();
        String weight = checkWeight.getText().toString().trim();
        String height = checkHeight.getText().toString().trim();
        String sex = spinnerSex3.getSelectedItem().toString();
        String gym = spinnerGym.getSelectedItem().toString();
        String diet = spinnerDiet.getSelectedItem().toString();
        Bundle bundle = new Bundle();

        if(birth.isEmpty()){
            checkBirth.setError("Birth is required!");
            checkBirth.requestFocus();
            return;
        }
        if (Integer.valueOf(birth) >2022 || Integer.valueOf(birth) <1900){
            checkBirth.setError("Birth must be real!");
            checkBirth.requestFocus();
            return;
        }
        if(weight.isEmpty()){
            checkWeight.setError("Weight is required!");
            checkWeight.requestFocus();
            return;
        }
        if(Integer.valueOf(weight) > 300 || Integer.valueOf(weight) < 20){
            checkWeight.setError("Weight must be real!");
            checkWeight.requestFocus();
            return;
        }
        if(height.isEmpty()){
            checkHeight.setError("Height is required!");
            checkHeight.requestFocus();
            return;
        }
        if(Integer.valueOf(height) > 300 || Integer.valueOf(height) < 20){
            checkHeight.setError("Height must be real!");
            checkHeight.requestFocus();
            return;
        }

        bundle.putString("birth", birth);
        bundle.putString("weight", weight);
        bundle.putString("height", height);
        bundle.putString("sex", sex);
        bundle.putString("gym", gym);
        bundle.putString("diet", diet);

        CaloriesFragment caloriesFragment = new CaloriesFragment();
        caloriesFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, caloriesFragment).commit();

    }

    private void bmiCheck() {
        String birth = checkBirth.getText().toString().trim();
        String weight = checkWeight.getText().toString().trim();
        String height = checkHeight.getText().toString().trim();
        String sex = spinnerSex3.getSelectedItem().toString();
        Bundle bundle = new Bundle();

        if(birth.isEmpty()){
            checkBirth.setError("Birth is required!");
            checkBirth.requestFocus();
            return;
        }
        if (Integer.valueOf(birth) >2022 || Integer.valueOf(birth) <1900){
            checkBirth.setError("Birth must be real!");
            checkBirth.requestFocus();
            return;
        }
        if(weight.isEmpty()){
            checkWeight.setError("Weight is required!");
            checkWeight.requestFocus();
            return;
        }
        if(Integer.valueOf(weight) > 300 || Integer.valueOf(weight) < 20){
            checkWeight.setError("Weight must be real!");
            checkWeight.requestFocus();
            return;
        }
        if(height.isEmpty()){
            checkHeight.setError("Height is required!");
            checkHeight.requestFocus();
            return;
        }
        if(Integer.valueOf(height) > 300 || Integer.valueOf(height) < 20){
            checkHeight.setError("Height must be real!");
            checkHeight.requestFocus();
            return;
        }
        bundle.putString("birth", birth);
        bundle.putString("weight", weight);
        bundle.putString("height", height);
        bundle.putString("sex", sex);

        BMIFragment bmiFragment = new BMIFragment();
        bmiFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, bmiFragment).commit();
    }
}