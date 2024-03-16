package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button btn;
    TextView tv, tv1;
    ProgressBar progressBar;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        edEmail = findViewById(R.id.editTextAppAddress);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);
        tv1 = findViewById(R.id.forgotpass);


        tv1.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));



        btn.setOnClickListener(view -> {

            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                // Get user id
                String userId = Objects.requireNonNull(authResult.getUser()).getUid();

                // Fetch user role
                reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Fetch user role
                            String userRole = snapshot.child("role").getValue(String.class);

                            //Redirect as per the role
                            if ("Healthcare Provider".equals(userRole)) {
                                startActivity(new Intent(LoginActivity.this, HealthcareProviderDashboard.class));
                                progressBar.setVisibility(View.GONE);
                            }
                            if ("User".equals(userRole)) {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Snapshot does not exist", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }).addOnFailureListener(e -> {
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

            );


        });

        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

    }

}





