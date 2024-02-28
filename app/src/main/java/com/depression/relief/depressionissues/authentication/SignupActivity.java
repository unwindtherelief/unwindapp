package com.depression.relief.depressionissues.authentication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.depression.relief.depressionissues.MainActivity;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private DatePickerDialog.OnDateSetListener dateOfBirthListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private Dialog dialog;

    private static final int[] MALE_IMAGES = {R.drawable.rm1, R.drawable.rm2, R.drawable.rm3, R.drawable.rm4};

    private static final int[] FEMALE_IMAGES = {R.drawable.rfm1, R.drawable.rfm2, R.drawable.rfm3};


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

        //dialog for hold user

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(SignupActivity.this);
                dialog.setContentView(LayoutInflater.from(SignupActivity.this).inflate(R.layout.progress_dialog, (ViewGroup) null));
                signup();

                dialog.show();
            }
        });

    }


    private void signup() {
        String firstname = binding.editTextFirstName.getText().toString().trim();
//        String lastname = binding.editTextLastName.getText().toString().trim();
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
        } /*else if (lastname.isEmpty()) {
            binding.editTextLastName.setError("Please enter last name!");
        }*/ else if (email.isEmpty()) {
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


            int selectedImageResourceId = 0;
            if (gender.equals("Male") || gender.equals("male")) {
                selectedImageResourceId = MALE_IMAGES[new Random().nextInt(MALE_IMAGES.length)];
            } else if (gender.equals("Female") || gender.equals("female")) {
                selectedImageResourceId = FEMALE_IMAGES[new Random().nextInt(FEMALE_IMAGES.length)];
            }

            String selectedImageUrl = "android.resource://" + getPackageName() + "/" + selectedImageResourceId;


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            UserModel usermodel = new UserModel(firstname, email, completeNumber, gender, dateOfBirth, selectedImageUrl, userId);


                            firestore.collection("users")
                                    .document(userId)
                                    .set(usermodel)
                                    .addOnCompleteListener(storeTask -> {
                                        if (storeTask.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                        } else {
                                            dialog.dismiss();
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