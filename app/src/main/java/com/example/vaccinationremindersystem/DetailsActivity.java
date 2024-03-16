package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccinationremindersystem.model.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    public EditText edParentsName, edEmail, edPhoneNumber, edLocation, edChildName, edDOB;
    public Button btn;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        edParentsName = findViewById(R.id.fullName);
        edEmail = findViewById(R.id.email);
        edPhoneNumber = findViewById(R.id.phonenumber);
        edLocation = findViewById(R.id.location);
        edChildName = findViewById(R.id.childname);
        edDOB = findViewById(R.id.DOB);
        btn = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);


        Profile profile = (Profile) getIntent().getSerializableExtra("profile");
        if (profile != null) {
            edParentsName.setText(profile.getParentName());
            edEmail.setText(profile.getEmail());
            edPhoneNumber.setText(profile.getPhone());
            edLocation.setText(profile.getLocation());
            edChildName.setText(profile.getChildName());
            edDOB.setText(profile.getChildDOB());


            btn.setOnClickListener(view -> updateUserProfile());
        } else {
            btn.setOnClickListener(view -> updateUserProfile());
        }

    }

    private void updateUserProfile() {

        String Fullnames = edParentsName.getText().toString();
        String Email = edEmail.getText().toString();
        String MobileNo = edPhoneNumber.getText().toString();
        String City = edLocation.getText().toString();
        String child = edChildName.getText().toString();
        String DOB = edDOB.getText().toString();


        if (Fullnames.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter Full names", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Email.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (MobileNo.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (City.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
            return;
        }
        if (child.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter Child's Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (DOB.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Enter Child's DOB", Toast.LENGTH_SHORT).show();
            return;
        }

            FirebaseDatabase.getInstance().getReference("Profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(
                            new Profile(
                                    Fullnames, Email, MobileNo, City, child, DOB
                            )
                    ).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            edParentsName.setText("");
                            edEmail.setText("");
                            edPhoneNumber.setText("");
                            edChildName.setText("");
                            edDOB.setText("");

                            startActivity(new Intent(this, HomeActivity.class));


                        } else {
                            Toast.makeText(this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


