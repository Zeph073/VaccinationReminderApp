package com.example.vaccinationremindersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.vaccinationremindersystem.databinding.ActivityViewNotificationBinding;

public class ViewNotification extends AppCompatActivity {

    ActivityViewNotificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String message = getIntent().getStringExtra("message");
        binding.fullMessage.setText(message);

        binding.okay.setOnClickListener(view -> {
            startActivity(new Intent(ViewNotification.this , HomeActivity.class));
            finish();
        });
    }
}