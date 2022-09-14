package com.example.gethealthy.fragment;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gethealthy.R;


public class ProductsFragment extends Fragment {


    public ProductsFragment() {
    }

    View.OnClickListener handler = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageButtonMeat:
                    Log.d("LOG","Meat clicked");
                    changeView("meat");
                    break;
                case R.id.imageButtonFruits:
                    Log.d("LOG","Fruits clicked");
                    changeView("vegfruits");
                    break;
                case R.id.imageButtonDrinks:
                    Log.d("LOG","Drinks clicked");
                    changeView("drinks");
                    break;
                case R.id.imageButtonCarbs:
                    Log.d("LOG","Carbs clicked");
                    changeView("carbohydrates");
                    break;
                case R.id.imageButtonFats:
                    Log.d("LOG","Fats clicked");
                    changeView("fats");
                    break;
                case R.id.imageButtonHighSugar:
                    Log.d("LOG","High sugars clicked");
                    changeView("high_sugar");
                    break;
                case R.id.imageButtonDairy:
                    Log.d("LOG","Dairy clicked");
                    changeView("dairy");
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        ImageButton meatButton = rootView.findViewById(R.id.imageButtonMeat);
        ImageButton fruitsButton = rootView.findViewById(R.id.imageButtonFruits);
        ImageButton drinksButton = rootView.findViewById(R.id.imageButtonDrinks);
        ImageButton carbsButton = rootView.findViewById(R.id.imageButtonCarbs);
        ImageButton fatsButton = rootView.findViewById(R.id.imageButtonFats);
        ImageButton highsugarButton = rootView.findViewById(R.id.imageButtonHighSugar);
        ImageButton dairyButton = rootView.findViewById(R.id.imageButtonDairy);

        meatButton.setOnClickListener(handler);
        fruitsButton.setOnClickListener(handler);
        drinksButton.setOnClickListener(handler);
        carbsButton.setOnClickListener(handler);
        fatsButton.setOnClickListener(handler);
        highsugarButton.setOnClickListener(handler);
        dairyButton.setOnClickListener(handler);

        return rootView;
    }

    public void changeView(String cat)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ProductFragment productFragment = new ProductFragment();
        transaction.replace(R.id.container, productFragment);
        transaction.replace(R.id.container, new ProductFragment(cat)).commit();

        transaction.addToBackStack(null);
    }
}