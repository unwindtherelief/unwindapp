package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.MeditationEachUserShowAdapter;
import com.depression.relief.depressionissues.admin.meditation.Meditation;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class MeditationOfEachActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMeditations;
    private MeditationEachUserShowAdapter meditationAdapter;
    private List<Meditation> meditationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_of_each);

        recyclerViewMeditations = findViewById(R.id.recyclerViewMeditations);

        meditationList = new ArrayList<>();
        meditationAdapter = new MeditationEachUserShowAdapter(meditationList, this);

        recyclerViewMeditations.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns, adjust as needed
        recyclerViewMeditations.setAdapter(meditationAdapter);

        // Fetch data from Firestore
        fetchDataFromFirestore();
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore.getInstance()
                .collection("meditations")
                .whereEqualTo("category", getIntent().getStringExtra("category"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Meditation meditation = document.toObject(Meditation.class);
                            meditationList.add(meditation);
                        }
                        // Notify the adapter about the changes
                        meditationAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the failure to fetch data
                    }
                });
    }
}