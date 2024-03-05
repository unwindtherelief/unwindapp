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

public class QuickMeditationDataAddActivity extends AppCompatActivity {
    private static final int PICK_AUDIO_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private ImageView quick_medi_imageView;
    private EditText quick_medi_editTextTitle, quick_medi_editTextCreator;
    private Spinner quick_spinnerCategory, quick_medi_spinnerLanguage;
    private RelativeLayout quick_LayoutUpload;
    private TextView quick_txtFileName;
    private ImageView quick_btnAddMeditation, backbtn;

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
        setContentView(R.layout.activity_quick_meditation_data_add);

        quick_medi_imageView = findViewById(R.id.quick_medi_imageView);
        quick_medi_editTextTitle = findViewById(R.id.quick_medi_editTextTitle);
        quick_medi_editTextCreator = findViewById(R.id.quick_medi_editTextCreator);
        quick_spinnerCategory = findViewById(R.id.quick_spinnerCategory);
        quick_medi_spinnerLanguage = findViewById(R.id.quick_medi_spinnerLanguage);
        quick_LayoutUpload = findViewById(R.id.quick_LayoutUpload);
        quick_txtFileName = findViewById(R.id.quick_txtFileName);
        quick_btnAddMeditation = findViewById(R.id.quick_btnAddMeditation);
        backbtn = findViewById(R.id.backbtn);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        // Setup Spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.quick_categories_array, R.layout.spinner_item_view);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quick_spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.languages_array, R.layout.spinner_item_view);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quick_medi_spinnerLanguage.setAdapter(languageAdapter);

        // Setup Click Listeners
        quick_LayoutUpload.setOnClickListener(view -> chooseAudioFile());
        quick_medi_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        quick_btnAddMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_dialog = new Dialog(QuickMeditationDataAddActivity.this);
                progress_dialog.setContentView(LayoutInflater.from(QuickMeditationDataAddActivity.this).inflate(R.layout.progress_dialog, null));
                addMeditationToFirestore();
                progress_dialog.show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Animatoo.INSTANCE.animateShrink(QuickMeditationDataAddActivity.this);
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
        return !quick_medi_editTextTitle.getText().toString().isEmpty() &&
                !quick_medi_editTextCreator.getText().toString().isEmpty() &&
                audioUri != null;
    }

    // Add this interface to handle callbacks from file upload tasks
    interface UploadCallback {
        void onUploadSuccess(String url);

        void onUploadFailure(String errorMessage);
    }


    private void addMeditationToFirestore() {
        if (validateInputs()) {
            uploadAudioFile(new QuickMeditationDataAddActivity.UploadCallback() {
                @Override
                public void onUploadSuccess(String audioFileUrl) {
                    uploadImage(new QuickMeditationDataAddActivity.UploadCallback() {
                        @Override
                        public void onUploadSuccess(String imageUrl) {
                            saveMeditationDataToFirestore(audioFileUrl, imageUrl);
                        }

                        @Override
                        public void onUploadFailure(String errorMessage) {
                            // Handle image file upload failure
                            Toast.makeText(QuickMeditationDataAddActivity.this, "Image upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onUploadFailure(String errorMessage) {
                    // Handle audio file upload failure
                    Toast.makeText(QuickMeditationDataAddActivity.this, "Audio file upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showSnackBar("Please fill all required fields!");

//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(QuickMeditationDataAddActivity.UploadCallback callback) {
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


    private void uploadAudioFile(QuickMeditationDataAddActivity.UploadCallback callback) {
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
        String documentId = firestore.collection("quickmeditation").document().getId();

        // Create a Meditation object
        Meditation meditation = new Meditation();
        meditation.setDocumentId(documentId);
        meditation.setTitle(quick_medi_editTextTitle.getText().toString());
        meditation.setCreator(quick_medi_editTextCreator.getText().toString());
        meditation.setCategory(quick_spinnerCategory.getSelectedItem().toString());
        meditation.setLanguage(quick_medi_spinnerLanguage.getSelectedItem().toString());
        meditation.setFileName(audioFileName);
        meditation.setFileUrl(audioFileUrl);
        meditation.setImageUrl(imageUrl);

        firestore.collection("quickmeditation").document(documentId)
                .set(meditation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(QuickMeditationDataAddActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();

                    progress_dialog.dismiss();

                    finish();
                })
                .addOnFailureListener(e -> {
                    progress_dialog.dismiss();
                    Toast.makeText(QuickMeditationDataAddActivity.this, "Firestore insertion failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            quick_txtFileName.setText(audioFileName);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageFileName = getFileName(imageUri);
            quick_medi_imageView.setImageURI(imageUri);
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateShrink(QuickMeditationDataAddActivity.this);
    }
}