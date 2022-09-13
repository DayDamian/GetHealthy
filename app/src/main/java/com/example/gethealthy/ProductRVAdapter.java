package com.example.gethealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gethealthy.models.Product;
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
    public ViewProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_rv,parent,false);
        firestore = FirebaseFirestore.getInstance();

        return new ViewProductHolder(view);
    }

    public Context getContext() {
        return context;
    }

    public void onBindViewHolder(@NonNull ViewProductHolder holder, int position) {
        Product product = productArrayList.get(holder.getAdapterPosition());

        holder.category.setText(product.getCategory());
        holder.name.setText(product.getName());
        holder.kcal.setText(product.getKcal().toString() + " kcal");
        holder.carbs.setText(product.getCarbs().toString() + "g");
        holder.fats.setText(product.getFats().toString() + "g");
        holder.sat.setText(product.getFats_sat().toString() + "g");
        holder.sugars.setText(product.getSugars().toString() + "g");
        holder.protein.setText(product.getProtein().toString() + "g");
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

