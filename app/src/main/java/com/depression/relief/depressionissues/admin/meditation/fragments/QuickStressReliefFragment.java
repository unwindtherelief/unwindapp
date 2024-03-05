package com.depression.relief.depressionissues.admin.meditation.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.meditation.Meditation;
import com.depression.relief.depressionissues.admin.meditation.MeditationAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class QuickStressReliefFragment extends Fragment implements  MeditationAdapter.OnDeleteClickListener{
    private RecyclerView recyclerViewQuickStreeRelief;
    private MeditationAdapter meditationAdapter;
    private List<Meditation> meditationList;
    private FirebaseFirestore firestore;
    private CollectionReference meditationCollection;
    ImageView img_no_item;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_stress_relief, container, false);


        firestore = FirebaseFirestore.getInstance();
        meditationList = new ArrayList<>();

        recyclerViewQuickStreeRelief = view.findViewById(R.id.recyclerViewQuickStreeRelief);
        img_no_item = view.findViewById(R.id.img_no_item);
        swipeRefreshLayout = view.findViewById(R.id.swiperfLayoutQuickstressRelief);

        swipeRefreshLayout.setOnRefreshListener(this::refreshData);


        recyclerViewQuickStreeRelief.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Adjust the span count as needed
        meditationAdapter = new MeditationAdapter(getContext(), meditationList, position -> onDeleteClick(position));
        recyclerViewQuickStreeRelief.setAdapter(meditationAdapter);

        // Load data from Firestore
        loadDataFromFirestore();


        return view;
    }

    private void refreshData() {
        // This method is called when a user pulls down to refresh
        loadDataFromFirestore();
        // Stop the refreshing animation
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadDataFromFirestore() {
        // Clear existing data
        meditationList.clear();

        // Fetch data from Firestore where category is "Stress Relief"
        firestore.collection("quickmeditation")
                .whereEqualTo("category", "Stress Relief")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                        Meditation meditation = queryDocumentSnapshots.getDocuments().get(i).toObject(Meditation.class);
                        if (meditation != null) {
                            meditationList.add(meditation);
                        }
                    }
                    // Notify the adapter about the changes
                    meditationAdapter.notifyDataSetChanged();

                    if (meditationList.isEmpty()) {
                        img_no_item.setVisibility(View.VISIBLE);
                    } else {
                        img_no_item.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if any
                    Toast.makeText(getContext(), "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this meditation?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String documentId = meditationList.get(position).getDocumentId();
                    firestore.collection("quickmeditation").document(documentId).delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(requireContext(), "Meditation deleted", Toast.LENGTH_SHORT).show();
                                meditationList.remove(position);
                                meditationAdapter.notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(requireContext(), "Failed to delete meditation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}