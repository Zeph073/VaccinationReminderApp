package com.example.vaccinationremindersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaccinationremindersystem.model.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navHome:
                            // Start HomeActivity or perform relevant action
                            FirebaseDatabase.getInstance().getReference("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Profile profile = snapshot.getValue(Profile.class);
                                            if (profile != null) {
                                                startActivity(new Intent(HomeActivity.this, DetailsActivity.class)
                                                        .putExtra("profile" , profile));
                                            } else {
                                                startActivity(new Intent(HomeActivity.this, DetailsActivity.class));
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                            return true;

                        case R.id.navVaccination:
                            // Start VaccinationActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, VaccinationActivity.class));
                            Toast.makeText(HomeActivity.this, "Vaccination", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navChangePass:
                            // Start ChangePasswordActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                            Toast.makeText(HomeActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navLogout:
                            // Start LoginActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            Toast.makeText(HomeActivity.this, "You have logged out", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                    finish();
                    return false;


                }
            };
}

