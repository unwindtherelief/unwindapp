package com.depression.relief.depressionissues.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.databinding.ActivityForgotpasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ForgotpasswordActivity extends AppCompatActivity {
    ActivityForgotpasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotpasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextEmail.getText().toString().trim();
                Intent intent = new Intent(ForgotpasswordActivity.this, OtpActivity.class);
//                intent.putExtra("email", email);
//                startActivity(intent);

                // Query Firestore to fetch mobile number based on email
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference usersCollection = db.collection("users");

                usersCollection.whereEqualTo("email", email)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    String mobile = documentSnapshot.getString("mobile");

                                    // Start OtpActivity with email and mobile number
                                    Intent intent = new Intent(ForgotpasswordActivity.this, OtpActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("mobile", mobile);
                                    startActivity(intent);
                                } else {
                                    // Handle case when email is not found
                                    Toast.makeText(ForgotpasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle Firestore query failure
                            }
                        });
            }
        });

    }
}