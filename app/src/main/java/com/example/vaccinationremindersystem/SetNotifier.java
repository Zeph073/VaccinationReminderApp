package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccinationremindersystem.databinding.ActivitySetNotifierBinding;
import com.example.vaccinationremindersystem.model.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SetNotifier extends AppCompatActivity {
    ActivitySetNotifierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetNotifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Long time = System.currentTimeMillis();

        String username = getIntent().getStringExtra("username");
        String uid = getIntent().getStringExtra("uid");
        binding.username.setText(username);

        FirebaseDatabase.getInstance().getReference("Profile/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.getValue(Profile.class).getPhone();
                binding.phoneValue.setText(phone);

                binding.phoneValue.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.notificationButton.setOnClickListener(view -> {
            binding.progress.setVisibility(View.VISIBLE);
            assert uid != null;
            FirebaseDatabase.getInstance().getReference().child(uid).child("time").setValue(time);
            FirebaseDatabase.getInstance().getReference().child(uid).child("title").setValue(
                    Objects.requireNonNull(binding.title.getText()).toString()
            );
            FirebaseDatabase.getInstance().getReference().child(uid).child("message").setValue(
                    Objects.requireNonNull(binding.message.getText()).toString()
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    binding.progress.setVisibility(View.GONE);
                    Toast.makeText(this, "Notification set", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SetNotifier.this, ViewPatients.class));
                    finish();
                }
            });
        });
    }
//
//    private Long getTime() {
//        int minute = binding.timePicker.getMinute();
//        int hour = binding.timePicker.getHour();
//        int day = binding.datePicker.getDayOfMonth();
//        int month = binding.datePicker.getMonth();
//        int year = binding.datePicker.getYear();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day, hour, minute);
//        return calendar.getTimeInMillis();
//    }
}