package com.example.gethealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner3;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DatabaseReference reference;
    private Button logoutButton, updateButton;
    private EditText updateFullName, updateUsername, updateEmail, updateBirth, updateWeight, updateHeight, updatePassword;
    private String userID;
    private Spinner spinnerSex2;

    final ArrayList<String> spinnerList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        banner3 = (TextView) findViewById(R.id.banner3);
        banner3.setOnClickListener(this);

        updateFullName = (EditText) findViewById(R.id.updateFullName);
        updateUsername = (EditText) findViewById(R.id.updateUsername);
        updateEmail = (EditText) findViewById(R.id.updateEmail);
        updateBirth = (EditText) findViewById(R.id.updateBirth);
        updateWeight = (EditText) findViewById(R.id.updateWeight);
        updateHeight = (EditText) findViewById(R.id.updateHeight);
        updatePassword = (EditText) findViewById(R.id.updatePassword);

        spinnerSex2 = (Spinner) findViewById(R.id.spinnerSex2);

        spinnerList2.add("Male");
        spinnerList2.add("Female");
        spinnerList2.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList2);
        spinnerSex2.setAdapter(adapter);
        
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);

        showAllData();

    }

    private void showAllData() {
        updateEmail.setText(user.getEmail());
        String email = updateEmail.getText().toString().trim();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        updateFullName.setText(documentSnapshot.get("fullname").toString());
                        updateUsername.setText(documentSnapshot.get("username").toString());
                        updateBirth.setText(documentSnapshot.get("birth").toString());
                        updateWeight.setText(documentSnapshot.get("weight").toString());
                        updateHeight.setText(documentSnapshot.get("height").toString());
                        updatePassword.setText(documentSnapshot.get("password").toString());
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoutButton:
                logoutUser();
                break;
            case R.id.banner3:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.updateButton:
                updateUser();
                break;
        }
    }

    private void updateUser() {
        String fullName = updateFullName.getText().toString().trim();
        String userName = updateUsername.getText().toString().trim();
        String email = updateEmail.getText().toString().trim();
        String birth = updateBirth.getText().toString().trim();
        String weight = updateWeight.getText().toString().trim();
        String height = updateHeight.getText().toString().trim();
        String password = updatePassword.getText().toString().trim();
        String sex = spinnerSex2.getSelectedItem().toString();

        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("birth", birth);
        userDetail.put("email", email);
        userDetail.put("fullname", fullName);
        userDetail.put("height", height);
        userDetail.put("password", password);
        userDetail.put("sex", sex);
        userDetail.put("username", userName);
        userDetail.put("weight", weight);

        if(fullName.isEmpty()){
            updateFullName.setError("Full name is required!");
            updateFullName.requestFocus();
            return;
        }
        if(userName.isEmpty()){
            updateUsername.setError("Username is required!");
            updateUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            updateEmail.setError("Email is required!");
            updateEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            updateEmail.setError("Please provide valid email!");
            updateEmail.requestFocus();
            return;
        }
        if(birth.isEmpty()){
            updateBirth.setError("Birth is required!");
            updateBirth.requestFocus();
            return;
        }
        if (Integer.valueOf(birth) >2022 || Integer.valueOf(birth) <1900){
            updateBirth.setError("Birth must be real!");
            updateBirth.requestFocus();
            return;
        }
        if(weight.isEmpty()){
            updateWeight.setError("Weight is required!");
            updateWeight.requestFocus();
            return;
        }
        if(Integer.valueOf(weight) > 300 || Integer.valueOf(weight) < 20){
            updateWeight.setError("Weight must be real!");
            updateWeight.requestFocus();
            return;
        }
        if(height.isEmpty()){
            updateHeight.setError("Height is required!");
            updateHeight.requestFocus();
            return;
        }
        if(Integer.valueOf(height) > 300 || Integer.valueOf(height) < 20){
            updateHeight.setError("Height must be real!");
            updateHeight.requestFocus();
            return;
        }
        if(password.isEmpty()){
            updatePassword.setError("Password is required!");
            updatePassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            updatePassword.setError("Min password length should be 6 characters!");
            updatePassword.requestFocus();
            return;
        }

        db.collection("Users")
                .whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Users")
                                    .document(documentID)
                                    .update(userDetail)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SettingsActivity.this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SettingsActivity.this, "Some Error Occurred!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(SettingsActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}