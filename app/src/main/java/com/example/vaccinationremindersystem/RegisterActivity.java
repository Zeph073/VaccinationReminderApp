package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccinationremindersystem.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edPassword, edConfirm;
    Button btn;

    Spinner spinner;
    TextView tv;
    ProgressBar progressBar;

    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    String[] role = {"Select" , "User" , "Healthcare Provider"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();


        edUsername = binding.fullName;
        edPassword = binding.password;
        edEmail = binding.email;
        edConfirm = binding.confirmPassword;
        btn = binding.buttonBookAppointment;
        tv = binding.textViewExistingUser;
        progressBar = binding.progressBar;
        spinner = binding.spinner;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, role);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tv.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        btn.setOnClickListener(view -> {

            String username = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String confirm = edConfirm.getText().toString();
            String userRole = spinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                return;
             }
            if (userRole.equals("Select")) {
                Toast.makeText(RegisterActivity.this, "Select user type", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    User user = new User(username , email , password , userRole , userId ,0L , "", "");
                    reference.child(userId).setValue(user);

                }
            }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());


        });
    }
}








