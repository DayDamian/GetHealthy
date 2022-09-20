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
import android.widget.TextView;

import com.example.gethealthy.DishRVAdapter;
import com.example.gethealthy.R;
import com.example.gethealthy.models.Dish;
import com.example.gethealthy.Helper;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

public class DishesFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    RecyclerView rv;
    //private TextView date;

    private TextView totalKcal;

    ArrayList<Dish> dishArrayList;
    DishRVAdapter dishRVAdapter;
    public static final String COLLECTION = "Dishes";

    public Double absoluteKcal;

    public Double getAbsoluteKcal() {
        return absoluteKcal;
    }

    public void setAbsoluteKcal(Double absoluteKcal) {
        this.absoluteKcal = absoluteKcal;
    }

    public DishesFragment() {
        db = FirebaseFirestore.getInstance();
        absoluteKcal = 0.0;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        View rootView = inflater.inflate(R.layout.fragment_dishes, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rvDishes);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //date = rootView.findViewById(R.id.textDate);

        //SimpleDateFormat dateFormatX = new SimpleDateFormat("dd.MM.yyyy");
        //String now = dateFormatX.format((Timestamp.now()).toDate());

        //date.setText(now);

        totalKcal = rootView.findViewById(R.id.totalKcal);
        dishArrayList = new ArrayList<>();
        dishRVAdapter = new DishRVAdapter(getActivity(), dishArrayList);

        rv.setAdapter(dishRVAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Helper(dishRVAdapter));
        itemTouchHelper.attachToRecyclerView(rv);
        totalKcal.setText(absoluteKcal.toString() + " kcal");
        EventChangeListener();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void EventChangeListener() {

        final FirebaseUser user = mAuth.getCurrentUser();

        db.collection(COLLECTION)
                .addSnapshotListener((value, error) -> {

                    if (error !=null) {
                        Log.e("Firestore fail", error.getMessage());
                        return;
                    }
                    assert value != null;
                    for (DocumentChange dc: value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            DocumentReference documentReference = (DocumentReference) dc.getDocument().get("userID");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date ts = ((Timestamp) dc.getDocument().get("date")).toDate();
                            Date now = (Timestamp.now()).toDate();
                            String nowstr = dateFormat.format(now);
                            String tsstr = dateFormat.format(ts);
                            String id = dc.getDocument().getId();
                            if(documentReference.getPath().equals("Users/"+user.getUid())) {
                                if (nowstr.equals(tsstr)) {
                                dishArrayList.add(dc.getDocument().toObject(Dish.class).withId(id));
                                absoluteKcal += (Double) dc.getDocument().get("kcal");
                                totalKcal.setText(absoluteKcal.toString() + " kcal");
                                }
                            }
                        }
                    }
                    dishRVAdapter.notifyDataSetChanged();
                }
                );
    }
}