package com.depression.relief.depressionissues.activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.JournalAdapter;
import com.depression.relief.depressionissues.models.JournalEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JournalActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private JournalAdapter adapter;
    private List<JournalEntry> journalList;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout dateLinearLayout;
    ImageView backbtn, filter_btn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        backbtn = findViewById(R.id.backbtn);
        filter_btn = findViewById(R.id.filter_btn);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dateLinearLayout = findViewById(R.id.lnrDatePicker);
        Calendar calendar = Calendar.getInstance();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Iterate through the last 7 days' dates
        for (int i = 6; i >= 0; i--) {
            TextView dateTextView = new TextView(this);
            dateTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            dateTextView.setGravity(Gravity.CENTER);

            // Get the date for the current iteration
            Date currentDate = calendar.getTime();
            String dateString = new SimpleDateFormat("d").format(currentDate);

            // Set the date text
            dateTextView.setText(dateString);

            // Check if it's today's date
            if (i == 6) {
                dateTextView.setBackgroundResource(R.drawable.ripplerectbg); // Highlight with green background
                dateTextView.setTextColor(R.color.textcolor);
            } else {
                dateTextView.setBackgroundResource(R.drawable.roundbg); // Highlight with green background
                dateTextView.setTextColor(R.color.textcolor);
            }

            // Add the TextView to the LinearLayout
            dateLinearLayout.addView(dateTextView);

            // Move to the previous day
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }


        // Add TextView for the current date
        TextView currentDateTextView = new TextView(this);
        currentDateTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        currentDateTextView.setGravity(Gravity.CENTER);

        // Get the current system date
        Date currentDate = Calendar.getInstance().getTime();
        String currentDateString = new SimpleDateFormat("d").format(currentDate);

        currentDateTextView.setText(currentDateString);
        currentDateTextView.setBackgroundResource(R.drawable.ripplerectbg); // Set drawable for the current date
        dateLinearLayout.addView(currentDateTextView);

        // Inside your JournalActivity's onCreate method, after populating dates
        for (int i = 0; i < dateLinearLayout.getChildCount(); i++) {
            View dateView = dateLinearLayout.getChildAt(i);
            dateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView selectedDateTextView = (TextView) v;
                    String selectedDate = selectedDateTextView.getText().toString();

                    // Call a method to filter and display entries for the selected date
                    filterAndDisplayEntries(selectedDate);
                }
            });
        }

        journalList = new ArrayList<>();
        adapter = new JournalAdapter(journalList);
        recyclerView.setAdapter(adapter);

        // Fetch and display user's journal entries
        fetchJournalEntries();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJournalEntries();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Handle FAB click to show the bottom sheet
        findViewById(R.id.fabAddEntry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,R.style.DatePickerDialogStyle,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date (year, month, dayOfMonth)
                        String selectedDate = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, month + 1, year);

                        // Call a method to filter and display entries for the selected date
                        filterAndDisplayEntries(selectedDate);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }


    private void filterAndDisplayEntries(String selectedDate) {
        List<JournalEntry> filteredEntries = new ArrayList<>();

        // Iterate through all entries and filter based on selected date
        for (JournalEntry entry : journalList) {
            if (entry.getDate().equals(selectedDate)) {
                filteredEntries.add(entry);
            }
        }

        // Clear the RecyclerView when there are no entries for the selected date
        if (filteredEntries.isEmpty()) {
            adapter.clearJournalList();
            showSnackBar("No entries for the selected date");
        } else {
            // Update RecyclerView with filtered entries
            adapter.setJournalList(filteredEntries);
            adapter.notifyDataSetChanged();
        }
    }


    private void fetchJournalEntries() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        CollectionReference journalRef = db.collection("journal").document(currentUserId).collection("entries");

        journalRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                journalList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    JournalEntry entry = document.toObject(JournalEntry.class);
                    journalList.add(entry);
                }
                adapter.notifyDataSetChanged();

                updatePlaceholderVisibility();
            }
        });
    }

    private void updatePlaceholderVisibility() {
        ImageView placeholderImage = findViewById(R.id.placeholderImage);
        RelativeLayout relativeLayout = findViewById(R.id.placeholderLayout);

        if (journalList.isEmpty()) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    private void showBottomSheetDialog() {
        BottomSheetDialogFragment bottomSheetDialogFragment = new NewJournalEntryBottomSheet();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    // Add the logic to save a new journal entry to Firestore
    // You can call this method after clicking the submit button in the bottom sheet
    public void saveJournalEntry(String title, String description) {
        if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
            showSnackBar("Nothing to save... please firstly add something");
            return;
        }

        String currentUserId = mAuth.getCurrentUser().getUid();
        CollectionReference journalRef = db.collection("journal").document(currentUserId).collection("entries");

        Map<String, Object> entry = new HashMap<>();
        entry.put("title", title);
        entry.put("description", description);
        entry.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        entry.put("time", new SimpleDateFormat("hh:mm a", Locale.US).format(new Date()));

        journalRef.add(entry).addOnSuccessListener(documentReference -> {
            Toast.makeText(this, "Data saves in firestore", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to save Data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateCard(this);
    }
}
