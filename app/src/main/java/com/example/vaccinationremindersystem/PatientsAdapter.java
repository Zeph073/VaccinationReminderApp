package com.example.vaccinationremindersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientsViewHolder>{
    private final ArrayList<User> users;
    private final Context context;
    private final OnUserClicked onUserClicked;

    public PatientsAdapter(ArrayList<User> users, Context context, OnUserClicked onUserClicked) {
        this.users = users;
        this.context = context;
        this.onUserClicked = onUserClicked;
    }

    public interface OnUserClicked {
        void onUserClicked(int position);
    }
    @NonNull
    @Override
    public PatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientsViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_holder , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsViewHolder holder, int position) {
        holder.name.setText(users.get(position).getUsername());
        holder.itemView.setOnClickListener(view -> onUserClicked.onUserClicked(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class PatientsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public PatientsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

    }
}
