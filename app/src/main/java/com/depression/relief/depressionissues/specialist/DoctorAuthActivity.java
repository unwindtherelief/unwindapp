package com.depression.relief.depressionissues.specialist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorAuthActivity extends AppCompatActivity {
    EditText edtAuthDoctorId, edtAuthDoctorPassword;
    Button btnDoctorAuth;
    private FirebaseFirestore db;
    private CollectionReference doctorsCollection;
    ImageView eye_toggle, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_auth);

        edtAuthDoctorId = findViewById(R.id.edtAuthDoctorId);
        edtAuthDoctorPassword = findViewById(R.id.edtAuthDoctorPassword);
        btnDoctorAuth = findViewById(R.id.btnDoctorAuth);
        eye_toggle = findViewById(R.id.eye_toggle);
        backbtn = findViewById(R.id.backbtn);

        db = FirebaseFirestore.getInstance();
        doctorsCollection = db.collection("doctors");

        btnDoctorAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateDoctor();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        eye_toggle.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtAuthDoctorPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        eye_toggle.setImageResource(R.drawable.ic_close_eye_toggle);
                        break;
                    case MotionEvent.ACTION_UP:
                        edtAuthDoctorPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        eye_toggle.setImageResource(R.drawable.ic_open_eye_toggle);
                        break;
                }
                return true;
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

/*
        doctorsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Loop through all documents in the collection
                        String storedDoctorId = document.getString("doctorId");
                        String storedPassword = document.getString("doctorPassword");

                        if (storedDoctorId != null && storedPassword != null &&
                                storedDoctorId.equals(doctorId) && storedPassword.equals(doctorPassword)) {
                            // Details match, proceed to next activity
                            Log.d("DoctorAuth", "Authentication successful for doctorId: " + doctorId);

                            Intent intent = new Intent(DoctorAuthActivity.this, DoctorAddProfileDataActivity.class);
                            intent.putExtra("doctorId", doctorId);
                            intent.putExtra("doctorPassword", doctorPassword);
                            startActivity(intent);
                            finish();

                            return; // Exit the loop if a match is found
                        }
                    }

                    // If the loop completes without finding a match
                    Toast.makeText(DoctorAuthActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoctorAuthActivity.this, "Error fetching doctors: " + task.getException(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error fetching doctors", task.getException());
                }
            }
        });
*/

        doctorsCollection.whereEqualTo("doctorId", doctorId)
                .whereEqualTo("doctorPassword", doctorPassword)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // Matching document found
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0); // Assuming there is only one matching document

                                // Check for the required fields
                                if (areRequiredFieldsPresent(document)) {
                                    // All required fields are present, go to DoctorHomeActivity
                                    Log.d("DoctorAuth", "Authentication successful for doctorId: " + doctorId);

                                    Intent intent = new Intent(DoctorAuthActivity.this, DoctorHomeActivity.class);
                                    intent.putExtra("doctorId", doctorId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Required fields are missing, go to DoctorAddProfileDataActivity
                                    Log.d("DoctorAuth", "Some required fields are missing for doctorId: " + doctorId);

                                    Intent intent = new Intent(DoctorAuthActivity.this, DoctorAddProfileDataActivity.class);
                                    intent.putExtra("doctorId", doctorId);
                                    intent.putExtra("doctorPassword", doctorPassword);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // No matching document found
                                Log.d("DoctorAuth", "Invalid credentials for doctorId: " + doctorId);

                                Toast.makeText(DoctorAuthActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DoctorAuthActivity.this, "Error fetching doctor: " + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreError", "Error fetching doctor", task.getException());
                        }
                    }
                });

    }

    private boolean areRequiredFieldsPresent(QueryDocumentSnapshot document) {
        // Check for the required fields
        return isFieldValid(document, "doctorId") &&
                isFieldValid(document, "doctorEmail") &&
                isFieldValid(document, "doctorPassword") &&
                isFieldValid(document, "documentId") &&
                isFieldValid(document, "hospitalName") &&
                isFieldValid(document, "pincode") &&
                isFieldValid(document, "address") &&
                isFieldValid(document, "specialization") &&
                isFieldValid(document, "languages") &&
                isFieldValid(document, "experience") &&
                isFieldValid(document, "selfDescription");
    }

    private boolean isFieldValid(QueryDocumentSnapshot document, String field) {
        return document.contains(field) && document.get(field) != null;
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.INSTANCE.animateSlideDown(DoctorAuthActivity.this);
    }
}