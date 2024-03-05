package com.depression.relief.depressionissues.admin.meditation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.meditation.Meditation;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import javax.annotation.Nullable;

public class MeditationDataAddActivity extends AppCompatActivity {
    private static final int PICK_AUDIO_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private ImageView imageView;
    private EditText editTextTitle, editTextCreator;
    private Spinner spinnerCategory, spinnerLanguage;
    private RelativeLayout relativeLayoutUpload;
    private TextView textViewFileName;
    private ImageView btnAddMeditation, backbtn;

    private Uri audioUri;
    private String audioFileName;
    private Uri imageUri; // Added for image selection

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private String imageFileName;
    private Dialog progress_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_data_add);

        imageView = findViewById(R.id.imageView);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextCreator = findViewById(R.id.editTextCreator);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        relativeLayoutUpload = findViewById(R.id.relativeLayoutUpload);
        textViewFileName = findViewById(R.id.textViewFileName);
        btnAddMeditation = findViewById(R.id.btnAddMeditation);
        backbtn = findViewById(R.id.backbtn);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        // Setup Spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories_array, R.layout.spinner_item_view);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.languages_array, R.layout.spinner_item_view);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(languageAdapter);

        // Setup Click Listeners
        relativeLayoutUpload.setOnClickListener(view -> chooseAudioFile());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        btnAddMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_dialog = new Dialog(MeditationDataAddActivity.this);
                progress_dialog.setContentView(LayoutInflater.from(MeditationDataAddActivity.this).inflate(R.layout.progress_dialog, null));
                addMeditationToFirestore();
                progress_dialog.show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Animatoo.INSTANCE.animateShrink(MeditationDataAddActivity.this);
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    private void chooseAudioFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, PICK_AUDIO_REQUEST);
    }

    private boolean validateInputs() {
        return !editTextTitle.getText().toString().isEmpty() &&
                !editTextCreator.getText().toString().isEmpty() &&
                audioUri != null;
    }

    // Add this interface to handle callbacks from file upload tasks
    interface UploadCallback {
        void onUploadSuccess(String url);

        void onUploadFailure(String errorMessage);
    }


    private void addMeditationToFirestore() {
        if (validateInputs()) {
            uploadAudioFile(new UploadCallback() {
                @Override
                public void onUploadSuccess(String audioFileUrl) {
                    uploadImage(new UploadCallback() {
                        @Override
                        public void onUploadSuccess(String imageUrl) {
                            saveMeditationDataToFirestore(audioFileUrl, imageUrl);
                        }

                        @Override
                        public void onUploadFailure(String errorMessage) {
                            // Handle image file upload failure
                            Toast.makeText(MeditationDataAddActivity.this, "Image upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onUploadFailure(String errorMessage) {
                    // Handle audio file upload failure
                    Toast.makeText(MeditationDataAddActivity.this, "Audio file upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showSnackBar("Please fill all required fields!");

//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(UploadCallback callback) {
        if (imageUri != null) {
            StorageReference storageRef = storage.getReference().child("images/" + imageFileName);

            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            callback.onUploadSuccess(uri.toString());
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        callback.onUploadFailure(e.getMessage());
                    });
        } else {
            callback.onUploadFailure("Image URI is null");
        }
    }


    private void uploadAudioFile(UploadCallback callback) {
        if (audioUri != null) {
            StorageReference storageRef = storage.getReference().child("audio_files/" + audioFileName);

            storageRef.putFile(audioUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL of the uploaded file
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            callback.onUploadSuccess(uri.toString());
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        callback.onUploadFailure(e.getMessage());
                    });
        } else {
            callback.onUploadFailure("Audio URI is null");
        }
    }

    private void saveMeditationDataToFirestore(String audioFileUrl, String imageUrl) {
        // Generate a new document ID
        String documentId = firestore.collection("meditations").document().getId();

        // Create a Meditation object
        Meditation meditation = new Meditation();
        meditation.setDocumentId(documentId);
        meditation.setTitle(editTextTitle.getText().toString());
        meditation.setCreator(editTextCreator.getText().toString());
        meditation.setCategory(spinnerCategory.getSelectedItem().toString());
        meditation.setLanguage(spinnerLanguage.getSelectedItem().toString());
        meditation.setFileName(audioFileName);
        meditation.setFileUrl(audioFileUrl);
        meditation.setImageUrl(imageUrl);

        firestore.collection("meditations").document(documentId)
                .set(meditation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MeditationDataAddActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();

                    progress_dialog.dismiss();

                    finish();
                })
                .addOnFailureListener(e -> {
                    progress_dialog.dismiss();
                    Toast.makeText(MeditationDataAddActivity.this, "Firestore insertion failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Implement logic to get the file name from URI
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            audioUri = data.getData();
            audioFileName = getFileName(audioUri);
            textViewFileName.setText(audioFileName);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageFileName = getFileName(imageUri);
            imageView.setImageURI(imageUri);
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateShrink(MeditationDataAddActivity.this);
    }
}