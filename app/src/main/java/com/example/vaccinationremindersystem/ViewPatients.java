package com.example.vaccinationremindersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaccinationremindersystem.databinding.ActivityViewPatientsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPatients extends AppCompatActivity {
    ArrayList<User> users;
    PatientsAdapter.OnUserClicked onUserClicked;
    ActivityViewPatientsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPatientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();
        binding.recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewPatients.this);
        binding.recyclerView.setLayoutManager(layoutManager);

        onUserClicked = position -> startActivity(new Intent(ViewPatients.this, SetNotifier.class)
                .putExtra("username", users.get(position).getUsername())
                .putExtra("uid", users.get(position).getUid()));

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String role = dataSnapshot.child("role").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String uid = dataSnapshot.child("uid").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    Long time = dataSnapshot.child("time").getValue(Long.class);
                    String message = dataSnapshot.child("message").getValue(String.class);
                    if (role != null && role.equals("User")) {
                        users.add(new User(name, email, password, role, uid, time, title, message));
                    }
                    binding.recyclerView.setAdapter(new PatientsAdapter(users, ViewPatients.this, onUserClicked));
                    binding.progressBar.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}