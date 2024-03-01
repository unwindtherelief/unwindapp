package com.depression.relief.depressionissues.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.event.EventData;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventDataAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    EditText event_title, event_date, event_time, event_location, event_price, event_limit, event_description;
    ImageView btn_AddEvent, event_img;
    private Uri selectedImageUri;

    private FirebaseFirestore db;
    private CollectionReference eventsRef;

    private StorageReference storageReference;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data_add);

        event_img = findViewById(R.id.event_img);
        event_title = findViewById(R.id.event_title);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
        event_location = findViewById(R.id.event_location);
        event_price = findViewById(R.id.event_price);
        event_limit = findViewById(R.id.event_limit);
        event_description = findViewById(R.id.event_description);
        btn_AddEvent = findViewById(R.id.btn_AddEvent);

        db = FirebaseFirestore.getInstance();
        eventsRef = db.collection("events");

        storageReference = FirebaseStorage.getInstance().getReference();

        calendar = Calendar.getInstance();

        setDateTimeListeners();


        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        event_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btn_AddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFileds()) {
                    uploadImageAndAddEventDataToFirestore();
                } else {
                    showSnackBar("All fields are required");
                }
            }
        });
    }

    private void setDateTimeListeners() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEditText();
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeEditText();
            }
        };
    }


    private void showDatePickerDialog() {
        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePickerDialog() {
        new TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        event_date.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeEditText() {
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        event_time.setText(sdf.format(calendar.getTime()));
    }

    private boolean validateFileds() {
        return selectedImageUri != null &&
                !event_title.getText().toString().isEmpty() &&
                !event_date.getText().toString().isEmpty() &&
                !event_time.getText().toString().isEmpty() &&
                !event_location.getText().toString().isEmpty() &&
                !event_price.getText().toString().isEmpty() &&
                !event_limit.getText().toString().isEmpty() &&
                !event_description.getText().toString().isEmpty();
    }


    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();

    }

    private void uploadImageAndAddEventDataToFirestore() {
        if (selectedImageUri != null) {
            StorageReference imageRef = storageReference.child("event_images/" + System.currentTimeMillis() + ".jpg");

            imageRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageurl = uri.toString();
                    addEventDataToFirestore(imageurl);
                });
            }).addOnFailureListener(e -> {
                showSnackBar("Error Uploading image!" + e.getMessage());
            });
        }
    }

    private void addEventDataToFirestore(String imageurl) {
        Map<String, Object> eventData = new HashMap<>();

        eventData.put("title", event_title.getText().toString());
        eventData.put("date", event_date.getText().toString());
        eventData.put("time", event_time.getText().toString());
        eventData.put("location", event_location.getText().toString());
        eventData.put("eventPrice", Double.parseDouble(event_price.getText().toString()));
        eventData.put("attendeeLimit", Integer.parseInt(event_limit.getText().toString()));
        eventData.put("description", event_description.getText().toString());
        eventData.put("imageUrl", imageurl);

        eventsRef.add(eventData).addOnSuccessListener(documentReference -> {
            String eventId = documentReference.getId();
            // Add documentId to eventData
            eventData.put("documentId", eventId);

            // Update the document with the documentId field
            eventsRef.document(eventId).set(eventData)
                    .addOnSuccessListener(aVoid -> {
                        showSnackBar("Event Added Successfully!");
                        finish();
                    })
                    .addOnFailureListener(e ->
                            showSnackBar("Error Adding Event! " + e.getMessage())
                    );
        }).addOnFailureListener(e -> {
            showSnackBar("Error Adding Event!" + e.getMessage());
        });
    }


    private void updateEventWithDocumentId(String documentId) {
        EventData eventdata = new EventData();
        eventdata.setDocumentId(documentId);

        eventdata.setTitle(event_title.getText().toString());
        eventdata.setDate(event_date.getText().toString());
        eventdata.setTime(event_time.getText().toString());
        eventdata.setLocation(event_location.getText().toString());
        eventdata.setEventPrice(Double.parseDouble(event_price.getText().toString()));
        eventdata.setAttendeeLimit(Integer.parseInt(event_limit.getText().toString()));
        eventdata.setDescription(event_description.getText().toString());

    }

    private void pickImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            event_img.setImageURI(selectedImageUri);
        }
    }
}