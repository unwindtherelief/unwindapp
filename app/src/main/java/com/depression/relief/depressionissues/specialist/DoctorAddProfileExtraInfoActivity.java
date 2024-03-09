package com.depression.relief.depressionissues.specialist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.doctor.DoctorModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class DoctorAddProfileExtraInfoActivity extends AppCompatActivity {
    private Spinner spinnerSpecialization, spinnerExperience;
    private CheckBox checkboxEnglish, checkboxHindi, checkboxMarathi, checkboxGujarati, checkboxSpanish, checkboxGerman;
    private EditText edtSelfDescription;
    private Button btnContinue;

    private FirebaseFirestore db;
    private List<String> selectedLanguagesList;
    private String doctorId;
    private String doctorPassword;
    private String hospitalName;
    private String pincode;
    private String address;
    private String doctorName;

    private CollectionReference doctorsCollection;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_profile_extra_info);


        spinnerSpecialization = findViewById(R.id.spinnerSpecialization);
        checkboxEnglish = findViewById(R.id.checkboxEnglish);
        checkboxHindi = findViewById(R.id.checkboxHindi);
        checkboxMarathi = findViewById(R.id.checkboxMarathi);
        checkboxGujarati = findViewById(R.id.checkboxGujarati);
        checkboxSpanish = findViewById(R.id.checkboxSpanish);
        checkboxGerman = findViewById(R.id.checkboxGerman);
        spinnerExperience = findViewById(R.id.spinnerExperience);
        edtSelfDescription = findViewById(R.id.edtSelfDescription);
        btnContinue = findViewById(R.id.btnContinue);

        db = FirebaseFirestore.getInstance();
        doctorsCollection = db.collection("doctors");
        storageReference = FirebaseStorage.getInstance().getReference("doctorImages");

        Intent intent = getIntent();
        if (intent != null) {
            doctorId = intent.getStringExtra("doctorId");
            doctorPassword = intent.getStringExtra("doctorPassword");
            hospitalName = intent.getStringExtra("hospitalName");
            pincode = intent.getStringExtra("pincode");
            address = intent.getStringExtra("address");
            doctorName = intent.getStringExtra("doctorName");
//            Toast.makeText(this, "doctorID" + doctorId + "doctor password " + doctorPassword, Toast.LENGTH_SHORT).show();
        }

        // Set up the specialization spinner
        List<String> specializationList = new ArrayList<>();
        specializationList.add("Psychiatrists");
        specializationList.add("Therapists");
        specializationList.add("Psychologists");
        specializationList.add("Nephrology");
        specializationList.add("Ophthalmology");


        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_view, specializationList);
        specializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialization.setAdapter(specializationAdapter);

        List<String> experienceList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            experienceList.add(String.valueOf(i) + " years");
        }
        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_view, experienceList);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExperience.setAdapter(experienceAdapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirestore();
            }
        });
    }

    private void saveDataToFirestore() {
        // Get selected values from spinners
        String specialization = spinnerSpecialization.getSelectedItem().toString();
        String experience = spinnerExperience.getSelectedItem().toString();

        // Get selected languages from checkboxes
        List<String> selectedLanguages = new ArrayList<>();
        if (checkboxEnglish.isChecked()) {
            selectedLanguages.add("English");
        }
        if (checkboxHindi.isChecked()) {
            selectedLanguages.add("Hindi");
        }
        if (checkboxMarathi.isChecked()) {
            selectedLanguages.add("Marathi");
        }
        if (checkboxGujarati.isChecked()) {
            selectedLanguages.add("Gujarati");
        }
        if (checkboxSpanish.isChecked()) {
            selectedLanguages.add("Spanish");
        }
        if (checkboxGerman.isChecked()) {
            selectedLanguages.add("German");
        }

        String selfDescription = edtSelfDescription.getText().toString().trim();
        // Upload image to Firebase Storage
        Uri imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        uploadImage(imageUri, specialization, experience, selectedLanguages, selfDescription);

/*
        doctorsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Loop through all documents in the collection
                        String storedDoctorId = document.getString("doctorId");
                        String storedPassword = document.getString("doctorPassword");

                        if (storedDoctorId != null && storedPassword != null && storedDoctorId.equals(doctorId) && storedPassword.equals(doctorPassword)) {
                            // Details match, update the existing document with new data
                            document.getReference().update("specialization", specialization);
                            document.getReference().update("languages", selectedLanguages.toString());
                            document.getReference().update("experience", experience);
                            document.getReference().update("selfDescription", selfDescription);
                            document.getReference().update("pincode", pincode);
                            document.getReference().update("address", address);
                            document.getReference().update("hospitalName", hospitalName);
                            document.getReference().update("doctorName", doctorName);
                            String imageUriString = getIntent().getStringExtra("imageUri");
                            if (imageUriString != null) {
                                // Image URI is available, save it to Firestore
                                document.getReference().update("imageUri", imageUriString);
                            }

                            Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DoctorAddProfileExtraInfoActivity.this, DoctorHomeActivity.class);
                            intent.putExtra("doctorId", doctorId);
                            intent.putExtra("doctorPassword", doctorPassword);
                            startActivity(intent);
                            Animatoo.INSTANCE.animateCard(DoctorAddProfileExtraInfoActivity.this);
                            return;
                        }
                    }

                    Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Doctor credintials not valid!", Toast.LENGTH_SHORT).show();
                    // TODO: Handle the case where the document does not exist
                } else {
                    Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Error fetching doctors: " + task.getException(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error fetching doctors", task.getException());
                }
            }
        });
*/
    }

    private void uploadImage(Uri imageUri, String specialization, String experience, List<String> selectedLanguages, String selfDescription) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = fileReference.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        updateFirestore(specialization, experience, selectedLanguages, selfDescription, downloadUri);
                    } else {
                        Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Image URI is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFirestore(String specialization, String experience, List<String> selectedLanguages, String selfDescription, Uri downloadUri) {
        doctorsCollection.whereEqualTo("doctorId", doctorId)
                .whereEqualTo("doctorPassword", doctorPassword)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // Matching document found
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                                // Update the existing document with new data
                                document.getReference().update("specialization", specialization);
                                document.getReference().update("languages", selectedLanguages.toString());
                                document.getReference().update("experience", experience);
                                document.getReference().update("selfDescription", selfDescription);
                                document.getReference().update("pincode", pincode);
                                document.getReference().update("address", address);
                                document.getReference().update("hospitalName", hospitalName);
                                document.getReference().update("doctorName", doctorName);

                                // Save the image URL to Firestore
                                if (downloadUri != null) {
                                    document.getReference().update("doctorImageUri", downloadUri.toString());
                                }

                                Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DoctorAddProfileExtraInfoActivity.this, DoctorHomeActivity.class);
                                intent.putExtra("doctorId", doctorId);
                                intent.putExtra("doctorPassword", doctorPassword);
                                startActivity(intent);
                                Animatoo.INSTANCE.animateCard(DoctorAddProfileExtraInfoActivity.this);
                            } else {
                                Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DoctorAddProfileExtraInfoActivity.this, "Error fetching doctor: " + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreError", "Error fetching doctor", task.getException());
                        }
                    }
                });
    }
}