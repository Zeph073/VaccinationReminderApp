package com.example.vaccinationremindersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaccinationremindersystem.model.Date;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReminderActivity extends AppCompatActivity {

    public TextView tvParent, tvEmail,tvPhonenumber, tvLocation, tvChild, tvDate;
    public EditText etNVD;
    public Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        tvParent = findViewById(R.id.TV_ParentsName);
        tvEmail = findViewById(R.id.TV_email);
        tvLocation = findViewById(R.id.TV_location);
        tvPhonenumber = findViewById(R.id.TV_phonenumber);
        tvDate = findViewById(R.id.TV_DOB);
        tvChild = findViewById(R.id.TV_childname);
        etNVD = findViewById(R.id.ET_NVD);
        update = findViewById(R.id.buttonUpdate);


        ref.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {

                    tvParent.setText(snap.child("parentName").getValue().toString());
                    tvEmail.setText(snap.child("email").getValue().toString());
                    tvPhonenumber.setText(snap.child("phoneNumber").getValue().toString());
                    tvLocation.setText(snap.child("location").getValue().toString());
                    tvChild.setText(snap.child("childName").getValue().toString());
                    tvDate.setText(snap.child("dateOfBirth").getValue().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NextVisit(etNVD);
                Toast.makeText(ReminderActivity.this, "Reminder Sent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ReminderActivity.this, HealthcareProviderDashboard.class));

            }
        });
    }

        private void NextVisit(TextView nextDateOfVisit) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            Date grade = new Date(nextDateOfVisit.getText().toString());

            String key = ref.push().getKey();
            ref.child("NextVisit").child(key).setValue(grade).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

    }
}