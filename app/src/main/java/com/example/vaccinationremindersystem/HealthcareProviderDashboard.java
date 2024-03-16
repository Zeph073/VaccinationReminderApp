package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccinationremindersystem.databinding.ActivityHealthcareProviderDashboardBinding;

public class HealthcareProviderDashboard extends AppCompatActivity {
    ActivityHealthcareProviderDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthcareProviderDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPatients.setOnClickListener(view -> startActivity(
                new Intent(HealthcareProviderDashboard.this, ViewPatients.class)
        ));
    }
}