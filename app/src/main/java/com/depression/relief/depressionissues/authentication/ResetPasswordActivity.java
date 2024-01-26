package com.depression.relief.depressionissues.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.databinding.ActivityResetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.editTextEmail.setInputType(InputType.TYPE_NULL);
        String email = getIntent().getStringExtra("email");

        binding.editTextEmail.setText(email);

        binding.resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = binding.editresetpass.getText().toString().trim();

                if (newPassword.isEmpty()) {
                    binding.editresetpass.setError("Please enter a new password");
                    binding.editresetpass.requestFocus();
                    return;
                }

                // Update password in Firebase Authentication
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    currentUser.updatePassword(newPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    updatePasswordInFirestore(email, newPassword);
                                } else {
                                    // Handle password update failure
                                }
                            });
                }
            }

        });
    }

    private void updatePasswordInFirestore(String email, String newPassword) {
        // Access the Firestore collection containing user information
        CollectionReference usersCollection = firestore.collection("users");

        // Query for the user with the provided email address
        Query query = usersCollection.whereEqualTo("email", email).limit(1);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // User found, update the password

                    // Get the document ID of the user
                    String userId = querySnapshot.getDocuments().get(0).getId();

                    // Create a map to update the password field
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("password", newPassword);

                    // Update the document with the new password
                    usersCollection.document(userId)
                            .update(updates)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    // Password updated successfully in Firestore
                                    // You can show a success message or perform other actions
                                    Toast.makeText(this, "Passsword reset successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Handle password update failure in Firestore
                                    // You can show an error message or perform other actions
                                    Toast.makeText(this, "Not Reset!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // User not found with the provided email address
                    // Handle this case accordingly
                }
            } else {
                // Handle Firestore query failure
                // You can show an error message or perform other actions
            }
        });
    }
}