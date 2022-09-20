package com.example.gethealthy;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gethealthy.models.Dish;
import com.example.gethealthy.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DishRVAdapter extends RecyclerView.Adapter<DishRVAdapter.ViewDishHolder> {

    Context context;
    ArrayList<Dish> dishArrayList;
    FirebaseFirestore firestore;
    public Double absoluteKcal;

    public Double getAbsoluteKcal() {
        return absoluteKcal;
    }

    public void setAbsoluteKcal(Double absoluteKcal) {
        this.absoluteKcal = absoluteKcal;
    }

    public DishRVAdapter(Context context, ArrayList<Dish> dishArrayList)
    {
        this.context = context;
        this.dishArrayList = dishArrayList;
        absoluteKcal = 0.0;
    }

    @NonNull
    @Override
    public ViewDishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.dish_rv,parent,false);
        firestore = FirebaseFirestore.getInstance();

        return new ViewDishHolder(view);
    }

    public Context getContext() { return context; }

    public void deleteDish(int position)
    {
        Dish dish = dishArrayList.get(position);
        firestore.collection("Dishes").document(dish.DishId).delete();
        dishArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void onBindViewHolder(@NonNull ViewDishHolder holder, int position) {
        Dish dish = dishArrayList.get(holder.getAdapterPosition());
        DocumentReference dr = dish.getProductID();
        Product product = new Product();

        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Double amount = dish.getAmount();
                        product.setName((String) document.getData().get("name"));
                        product.setKcal(Double.parseDouble(document.getData().get("kcal").toString()));
                        product.setCarbs(Double.parseDouble(document.getData().get("carbs").toString()));
                        product.setFats(Double.parseDouble(document.getData().get("fats").toString()));
                        product.setFats_sat(Double.parseDouble(document.getData().get("fats_sat").toString()));
                        product.setProtein(Double.parseDouble(document.getData().get("protein").toString()));
                        product.setSugars(Double.parseDouble(document.getData().get("sugars").toString()));
                        holder.name.setText(product.getName());
                        holder.energy.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getKcal() * amount/100))) + " kcal");
                        holder.carbs.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getCarbs() * amount/100))) + " g");
                        holder.fats.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getFats() * amount/100))) + " g");
                        holder.protein.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getProtein() * amount/100))) + " g");
                        holder.satur.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getFats_sat() * amount/100))) + " g");
                        holder.sugars.setText(String.valueOf(String.format(Locale.US,"%,.2f",(product.getSugars() * amount/100))) + " g");

                        absoluteKcal += product.getKcal() * amount/100;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        holder.amount.setText(dish.getAmount().toString() + " g");
        //holder.date.setText(dateFormat.format(dish.getDate().toDate()));


    }

    @Override
    public int getItemCount() { return dishArrayList.size(); }

    public static class ViewDishHolder extends RecyclerView.ViewHolder{

        DishRVAdapter adapter;

        TextView name;
        TextView amount;
        //TextView date;
        TextView energy;
        TextView carbs;
        TextView fats;
        TextView protein;
        TextView satur;
        TextView sugars;
        //Button deleteButton;


        public ViewDishHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prodDishName);
            energy = itemView.findViewById(R.id.energyDish);
            carbs = itemView.findViewById(R.id.carbsDish);
            fats = itemView.findViewById(R.id.fatsDish);
            protein = itemView.findViewById(R.id.proteinDish);
            amount = itemView.findViewById(R.id.amount);
            //date = itemView.findViewById(R.id.date);
            satur = itemView.findViewById(R.id.satDish);
            sugars = itemView.findViewById(R.id.sugarsDish);
            /*deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do zrobienia!!
                }
            });*/
        }
    }
}
