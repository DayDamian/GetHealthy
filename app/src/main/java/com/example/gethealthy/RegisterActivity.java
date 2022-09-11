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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gethealthy.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText editFullName, editUsername, editEmail, editBirth, editWeight, editHeight, editPassword;
    private Button registerButton;
    private ProgressBar progressBar;
    private Spinner spinnerSex;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    final ArrayList<String> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        editFullName = (EditText) findViewById(R.id.editFullName);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editBirth = (EditText) findViewById(R.id.editBirth);
        editWeight = (EditText) findViewById(R.id.editWeight);
        editHeight = (EditText) findViewById(R.id.editHeight);
        editPassword = (EditText) findViewById(R.id.editPassword);

        spinnerSex = (Spinner) findViewById(R.id.spinnerSex);

        spinnerList.add("Male");
        spinnerList.add("Female");
        spinnerList.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinnerSex.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = editFullName.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String birth = editBirth.getText().toString().trim();
        String weight = editWeight.getText().toString().trim();
        String height = editHeight.getText().toString().trim();
        String sex = spinnerSex.getSelectedItem().toString();

        if(fullName.isEmpty()){
            editFullName.setError("Full name is required!");
            editFullName.requestFocus();
            return;
        }
        if(username.isEmpty()){
            editUsername.setError("Username is required!");
            editUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            return;
        }
        if(birth.isEmpty()){
            editBirth.setError("Birth is required!");
            editBirth.requestFocus();
            return;
        }
        if (Integer.valueOf(birth) >2022 || Integer.valueOf(birth) <1900){
            editBirth.setError("Birth must be real!");
            editBirth.requestFocus();
            return;
        }
        if(weight.isEmpty()){
            editWeight.setError("Weight is required!");
            editWeight.requestFocus();
            return;
        }
        if(Integer.valueOf(weight) > 300 || Integer.valueOf(weight) < 20){
            editWeight.setError("Weight must be real!");
            editWeight.requestFocus();
            return;
        }
        if(height.isEmpty()){
            editHeight.setError("Height is required!");
            editHeight.requestFocus();
            return;
        }
        if(Integer.valueOf(height) > 300 || Integer.valueOf(height) < 20){
            editHeight.setError("Height must be real!");
            editHeight.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password is required!");
            editPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            editPassword.setError("Min password length should be 6 characters!");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullname", fullName);
                            user.put("username", username);
                            user.put("email", email);
                            user.put("password", password);
                            user.put("birth", birth);
                            user.put("weight", weight);
                            user.put("height", height);
                            user.put("sex", sex);

                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    }
                });

    }
}