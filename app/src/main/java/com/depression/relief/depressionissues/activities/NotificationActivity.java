package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.EventFullInfoActivity;
import com.depression.relief.depressionissues.admin.event.EventUserShowAdapter;
import com.depression.relief.depressionissues.admin.event.EventData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements EventUserShowAdapter.OnItemClickListener {

    RecyclerView recycler_show_event;
    ImageView backbtn;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventUserShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        recycler_show_event = findViewById(R.id.recycler_show_event);
        backbtn = findViewById(R.id.backbtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Set up RecyclerView
        List<EventData> eventList = new ArrayList<>();
        adapter = new EventUserShowAdapter(eventList, this, this);
        recycler_show_event.setLayoutManager(new LinearLayoutManager(this));
        recycler_show_event.setAdapter(adapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
    }

    @Override
    public void onItemClick(EventData eventData) {
        Intent intent = new Intent(this, EventsDetailsUserShowActivity.class);
        intent.putExtra("eventData", eventData);
        startActivity(intent);
    }

}