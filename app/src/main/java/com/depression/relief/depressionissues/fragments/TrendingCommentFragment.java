package com.depression.relief.depressionissues.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.CommentDataShowAdapter;
import com.depression.relief.depressionissues.models.Comment_mdl;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrendingCommentFragment extends Fragment {
    private RecyclerView recyclerView;
    private CommentDataShowAdapter commentDataShowAdapter;
    private List<Comment_mdl> commentList;
    private FirebaseFirestore db;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_comment, container, false);

        recyclerView = view.findViewById(R.id.trendingrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.Trendingswiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this::refreshComments);

        db = FirebaseFirestore.getInstance();
        commentList = new ArrayList<>();
        commentDataShowAdapter = new CommentDataShowAdapter(getActivity(), commentList);
        recyclerView.setAdapter(commentDataShowAdapter);

        // Fetch all comments, removing the category filter
        fetchAllComments();

        return view;
    }

    private void refreshComments() {
        fetchAllComments();
        swipeRefreshLayout.setRefreshing(false);
    }

    //old code of fetchAllComments();
/*
    private void fetchAllComments() {
        CollectionReference commentsCollection = db.collection("comments");

        // Query to fetch all comments without filtering by category
        Query query = commentsCollection.orderBy("timestamp", Query.Direction.DESCENDING);

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                // Handle the error
                Log.e("TrendingFragment", "Error fetching comments: " + e.getMessage());
                return;
            }

            if (queryDocumentSnapshots != null) {
                commentList.clear();
                commentList.addAll(queryDocumentSnapshots.toObjects(Comment_mdl.class));
                commentDataShowAdapter.notifyDataSetChanged();
            }
        });
    }
*/

    private void fetchAllComments() {
        CollectionReference commentsCollection = db.collection("comments");

        // Query to fetch all comments without filtering by category
        Query query = commentsCollection.orderBy("timestamp", Query.Direction.DESCENDING);

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                // Handle the error
                Log.e("TrendingFragment", "Error fetching comments: " + e.getMessage());
                return;
            }

            if (queryDocumentSnapshots != null) {
                commentList.clear();
                commentList.addAll(queryDocumentSnapshots.toObjects(Comment_mdl.class));
                commentDataShowAdapter.notifyDataSetChanged();
            }
        });
    }

    // Add this method to fetch comments when the fragment is resumed
    @Override
    public void onResume() {
        super.onResume();
        fetchAllComments();
    }
}

