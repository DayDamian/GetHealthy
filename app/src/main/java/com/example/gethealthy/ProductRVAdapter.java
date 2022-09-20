package com.example.gethealthy;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gethealthy.models.Dish;
import com.example.gethealthy.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        holder.kcal.setText(product.getKcal().toString());
        holder.carbs.setText(product.getCarbs().toString() + "g");
        holder.fats.setText(product.getFats().toString() + "g");
        holder.sat.setText(product.getFats_sat().toString() + "g");
        holder.sugars.setText(product.getSugars().toString() + "g");
        holder.protein.setText(product.getProtein().toString() + "g");
        holder.prodId.setText(product.ProductsId);
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
        Button addButton;
        String prodName;

        TextView prodId;
        String prodIdstr;
        Double kcals;

        public Double getKcals() { return kcals; }

        public void setKcals(Double kcals) { this.kcals = kcals; }


        public String getProdIdstr() { return prodIdstr; }

        public void setProdIdstr(String prodIdstr) { this.prodIdstr = prodIdstr; }


        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }


        public ViewProductHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.productName);
            carbs = itemView.findViewById(R.id.carbs);
            kcal = itemView.findViewById(R.id.amount);
            fats = itemView.findViewById(R.id.fats);
            sat = itemView.findViewById(R.id.saturated);
            protein = itemView.findViewById(R.id.protein);
            sugars = itemView.findViewById(R.id.sugars);
            prodId = itemView.findViewById(R.id.prodId);
            addButton = itemView.findViewById(R.id.addButton);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    setProdName((String) name.getText());
                    setProdIdstr((String) prodId.getText());
                    setKcals((Double.parseDouble((String) kcal.getText())));
                    showDialog();
                }
            });

        }

        private void showDialog() {
            Dialog dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.add_dialog);
            dialog.show();
            Dish dish = new Dish();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            window.setAttributes(wlp);

            TextView itemInfo = dialog.findViewById(R.id.itemInfo);
            itemInfo.setText(getProdName());
            Button addAmountButton = dialog.findViewById(R.id.addAmountButton);
            EditText amountNumb = dialog.findViewById(R.id.amountNumber);
            addAmountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountNumb.getText().toString().equals("")){
                        Toast.makeText(itemView.getContext(), "Amount is empty!", Toast.LENGTH_LONG).show();
                        //przedyskutowac czy ma byc 100g domyslnie czy nie
                    }
                    else {
                        Timestamp date = Timestamp.now();
                        final FirebaseUser user = mAuth.getCurrentUser();
                        Double amount = Double.parseDouble(amountNumb.getText().toString());
                        DocumentReference documentReference = db.document("Users/"+user.getUid());
                        DocumentReference prodref = db.document("Products/"+getProdIdstr());

                        Double mKcal = amount * getKcals()/100;
                        dish.setAmount(amount);
                        dish.setDate(date);
                        dish.setUserID(documentReference);
                        dish.setProductID(prodref);
                        dish.setKcal(mKcal);

                        db.collection("Dishes")
                                .add(dish)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                });

                        dialog.dismiss();
                        Toast.makeText(itemView.getContext(), "Product added to dish!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}

