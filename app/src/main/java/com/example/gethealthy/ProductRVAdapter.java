package com.example.gethealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gethealthy.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewProductHolder> {


    Context context;
    ArrayList<Product> productArrayList;
    FirebaseFirestore firestore;

    public ProductRVAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductRVAdapter.ViewProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_rv,parent,false);
        firestore = FirebaseFirestore.getInstance();

        return new ViewProductHolder(view);
    }

    public Context getContext() {
        return context;
    }

    public void onBindViewHolder(@NonNull ProductRVAdapter.ViewProductHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ViewProductHolder extends RecyclerView.ViewHolder{

        TextView category;
        TextView name;
        TextView carbs;
        TextView kcal;
        TextView fats;
        TextView sat;
        TextView protein;
        TextView sugars;

        public ViewProductHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.productName);
            carbs = itemView.findViewById(R.id.carbs);
            kcal = itemView.findViewById(R.id.energy);
            fats = itemView.findViewById(R.id.fats);
            sat = itemView.findViewById(R.id.saturated);
            protein = itemView.findViewById(R.id.protein);
            sugars = itemView.findViewById(R.id.sugars);

        }
    }
}

