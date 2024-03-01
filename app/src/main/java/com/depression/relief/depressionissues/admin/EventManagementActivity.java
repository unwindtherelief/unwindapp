package com.depression.relief.depressionissues.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.event.EventAdapter;
import com.depression.relief.depressionissues.admin.event.EventData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EventManagementActivity extends AppCompatActivity implements EventAdapter.OnItemClickListener {
    RecyclerView recycler_event;
    FloatingActionButton fabAddEvent;
    ImageView backbtn;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        recycler_event = findViewById(R.id.recycler_event);
        fabAddEvent = findViewById(R.id.fabAddEvent);
        backbtn = findViewById(R.id.backbtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Set up RecyclerView
        List<EventData> eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList, this, this);
        recycler_event.setLayoutManager(new LinearLayoutManager(this));
        recycler_event.setAdapter(adapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventManagementActivity.this, EventDataAddActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this::fetchData);

        fetchData();

    }

    private void fetchData() {
        eventsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<EventData> eventList = queryDocumentSnapshots.toObjects(EventData.class);
            adapter.setData(eventList);
            swipeRefreshLayout.setRefreshing(false);
        }).addOnFailureListener(e -> {
            // Handle failure
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateCard(EventManagementActivity.this);
    }

    @Override
    public void onItemClick(EventData eventData) {
        Intent intent = new Intent(this, EventFullInfoActivity.class);
        intent.putExtra("eventData", eventData);
        startActivity(intent);
    }

    @Override
    public void onDeleteButtonClick(EventData eventData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Event");
        builder.setMessage("Are you sure you want to delete this event?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteEvent(eventData);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteEvent(EventData eventData) {
        String documentId = eventData.getDocumentId();

        eventsRef.document(documentId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showSnackBar("Event deleted successfully");
                        adapter.removeItem(eventData);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showSnackBar("Error deleting event: " + e.getMessage());
                    }
                });
    }


    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

}