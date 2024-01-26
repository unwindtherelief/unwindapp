package com.depression.relief.depressionissues.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.databinding.ActivityOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    String mobileNumber = "";
    ActivityOtpBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        String email = getIntent().getStringExtra("email");
        String mobile = getIntent().getStringExtra("mobile");
        binding.mobilenumber.setText(mobile);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(mobile)                          // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)               // Timeout duration
                        .setActivity(this)                                // Activity (or context) reference
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                // This method is called when verification is completed automatically
                                signInWithPhoneAuthCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // This method is called when verification fails
                                String errorMessage = e.getMessage();
                                Toast.makeText(OtpActivity.this, "Verification failed! " + errorMessage, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   PhoneAuthProvider.@NonNull ForceResendingToken forceResendingToken) {
                                // This method is called when the verification code is successfully sent
                                String storedVerificationId = verificationId;

                                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("verificationId", storedVerificationId);
                                editor.apply();

                                binding.resetbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String otp = binding.editTextOtp.getText().toString().trim();
                                        if (!otp.isEmpty()) {
                                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                                            signInWithPhoneAuthCredential(credential);
                                        }
                                    }
                                });
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String email = getIntent().getStringExtra("email");
                            Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish(); // Optional, if you want to close the current activity
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OtpActivity.this, "Invalid otp entered!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OtpActivity.this, "Error verifying otp!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

}