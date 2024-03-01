package com.depression.relief.depressionissues.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.event.EventData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventFullInfoActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewTitle, textViewDate, textViewTime, textViewLocation, textViewAttendeeLimit, textViewDescription;
    private Button btnDelete;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_full_info);

        imageView = findViewById(R.id.imageView);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewAttendeeLimit = findViewById(R.id.textViewAttendeeLimit);
        textViewDescription = findViewById(R.id.textViewDescription);
        btnDelete = findViewById(R.id.btnDelete);

        // Assuming you have passed the EventData object through Intent
        EventData eventData = (EventData) getIntent().getSerializableExtra("eventData");

        if (eventData != null) {
            // Load image using Glide or your preferred library
            Glide.with(this).load(eventData.getImageUrl()).into(imageView);

            textViewTitle.setText(eventData.getTitle());
            textViewDate.setText("Date: " + eventData.getDate());
            textViewTime.setText("Time: " + eventData.getTime());
            textViewLocation.setText(eventData.getLocation());
            textViewAttendeeLimit.setText("Attendee Limit: " + eventData.getAttendeeLimit());
            textViewDescription.setText(eventData.getDescription());

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String documentId = eventData.getDocumentId();

                    eventsRef.document(documentId).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    showSnackBar("Event deleted successfully");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showSnackBar("Error deleting event: " + e.getMessage());
                                }
                            });
                }
            });
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();

    }
}