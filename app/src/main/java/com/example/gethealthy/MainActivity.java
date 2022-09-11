package com.example.gethealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, reset;
    private Button loginButton;
    private ProgressBar progressBar;
    private EditText editEmailLogin, editPasswordLogin;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.textView_register);
        register.setOnClickListener(this);

        reset = (TextView) findViewById(R.id.textView_forogot_password);
        reset.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        editPasswordLogin = (EditText) findViewById(R.id.editPasswordLogin);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.loginButton:
                loginUser();
                break;
            case R.id.textView_forogot_password:
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
        }
    }

    private void loginUser() {
        String email = editEmailLogin.getText().toString().trim();
        String password = editPasswordLogin.getText().toString().trim();

        if(email.isEmpty()){
            editEmailLogin.setError("Email is required!");
            editEmailLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPasswordLogin.setError("Password is required!");
            editPasswordLogin.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}