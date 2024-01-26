package com.depression.relief.depressionissues.authentication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.MainActivity;
import com.depression.relief.depressionissues.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private DatePickerDialog.OnDateSetListener dateOfBirthListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        binding.buttonLoginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.textViewDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }


    private void signup() {
        String firstname = binding.editTextFirstName.getText().toString().trim();
        String lastname = binding.editTextLastName.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();
        String dateOfBirth = binding.textViewDateOfBirth.getText().toString().trim();
        String gender = binding.radioGroupGender.getText().toString().trim();

        binding.textViewDateOfBirth.setText(dateOfBirth);

        String countryCode = binding.countryCodePicker.getSelectedCountryCode();
        String mobile = binding.editTextMnumber.getText().toString().trim();
        String completeNumber = "+" + countryCode + mobile;

        if (firstname.isEmpty()) {
            binding.editTextFirstName.setError("Please enter first name!");
        } else if (lastname.isEmpty()) {
            binding.editTextLastName.setError("Please enter last name!");
        } else if (email.isEmpty()) {
            binding.editTextEmail.setError("Please enter email address!");
        } else if (password.isEmpty()) {
            binding.editTextPassword.setError("Please enter password!");
        } else if (dateOfBirth.isEmpty()) {
            binding.textViewDateOfBirth.setError("Please select Birth date!");
        } else if (gender.isEmpty()) {
            binding.radioGroupGender.setError("Please enter your gender!");
        } else if (completeNumber.isEmpty()) {
            binding.editTextMnumber.setError("Please fill complate Mobile number!");
        } else {

/*
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("email", email);
                        user.put("mobile", mobile);
                        user.put("date_of_birth", dateOfBirth);
                        user.put("gender", gender);

                        firestore.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SignupActivity.this, "Signup Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "There is Some Issue!", Toast.LENGTH_SHORT).show();
                    }
                });
*/

            UserModel usermodel = new UserModel(firstname, lastname, email, completeNumber, gender, dateOfBirth);

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            firestore.collection("users")
                                    .document(userId)
                                    .set(usermodel)
                                    .addOnCompleteListener(storeTask -> {
                                        if (storeTask.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error occurred while storing user data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    public void showDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());

                        binding.textViewDateOfBirth.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}