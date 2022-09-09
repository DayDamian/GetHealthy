package com.example.gethealthy.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gethealthy.ProductRVAdapter;
import com.example.gethealthy.R;
import com.example.gethealthy.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ProductFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    RecyclerView rv;
    ArrayList<Product> productArrayList;
    ProductRVAdapter productRVAdapter;
    public static final String COLLECTION = "Test";

    public ProductFragment() {
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rvProducts);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        productArrayList = new ArrayList<>();
        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        rv.setAdapter(productRVAdapter);
        EventChangeListener();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void EventChangeListener() {

        db.collection(COLLECTION)
                .addSnapshotListener((value, error) -> {

                    if (error !=null) {
                        Log.e("Firestore fail", error.getMessage());
                        return;
                    }
                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            //DocumentReference documentReference = (DocumentReference) dc.getDocument().get("idUser");
                            String id = dc.getDocument().getId();
                            //if (documentReference.getPath().equals("Users/" + user.getUid())) {
                            productArrayList.add(dc.getDocument().toObject(Product.class).withId(id));
                        }

                        productRVAdapter.notifyDataSetChanged();
                    }
                });
    }
}

