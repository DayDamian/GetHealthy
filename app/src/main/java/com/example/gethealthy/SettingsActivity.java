package com.example.gethealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner3;
    private FirebaseAuth auth;
    private FirebaseUser user;
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
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String password = snapshot.child(userID).child("password").getValue().toString();
                //Toast.makeText(SettingsActivity.this, password, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}