package com.depression.relief.depressionissues.specialist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorAuthActivity extends AppCompatActivity {
    EditText edtAuthDoctorId, edtAuthDoctorPassword;
    Button btnDoctorAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_auth);

        edtAuthDoctorId = findViewById(R.id.edtAuthDoctorId);
        edtAuthDoctorPassword = findViewById(R.id.edtAuthDoctorPassword);
        btnDoctorAuth = findViewById(R.id.btnDoctorAuth);

        db = FirebaseFirestore.getInstance();

        btnDoctorAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateDoctor();
            }
        });
    }

    private void authenticateDoctor() {
        String doctorId = edtAuthDoctorId.getText().toString().trim();
        String doctorPassword = edtAuthDoctorPassword.getText().toString().trim();

        if (doctorId.isEmpty() || doctorPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("doctors")
                .document(doctorId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Doctor exists, check if details match
                                String storedPassword = document.getString("doctorPassword");

                                if (storedPassword.equals(doctorPassword)) {
                                    // Details match, proceed to next activity
                                    Intent intent = new Intent(DoctorAuthActivity.this, DoctorAddProfileDetailsActivity.class);
                                    intent.putExtra("doctorId", doctorId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DoctorAuthActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DoctorAuthActivity.this, "Doctor not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DoctorAuthActivity.this, "Error fetching doctor details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}