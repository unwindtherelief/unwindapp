package com.depression.relief.depressionissues.fragments;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class RelationshipCommentFragment extends Fragment {
    private RecyclerView recyclerView;
    private CommentDataShowAdapter commentDataShowAdapter;
    private List<Comment_mdl> commentList;
    private FirebaseFirestore db;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_relationship_comment, container, false);

        recyclerView = view.findViewById(R.id.relationrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.relationswiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this::refreshComments);

        db = FirebaseFirestore.getInstance();
        commentList = new ArrayList<>();
        commentDataShowAdapter = new CommentDataShowAdapter(getActivity(), commentList);
        recyclerView.setAdapter(commentDataShowAdapter);

        // Fetch Trending comments
        fetchdRelationshipComments();

        return view;
    }

    private void refreshComments() {
        // Refresh Trending comments
        fetchdRelationshipComments();
        swipeRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("LongLogTag")
    private void fetchdRelationshipComments() {
        CollectionReference commentsCollection = db.collection("comments");

        // Query to fetch only comments with the category "Trending"
        Query query = commentsCollection
                .whereEqualTo("selectedCategory", "Relationship")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                // Handle the error
                Log.e("RelationshipCommentFragment", "Error fetching Relationship comments: " + e.getMessage());
                return;
            }

            if (queryDocumentSnapshots != null) {
                commentList.clear();
                commentList.addAll(queryDocumentSnapshots.toObjects(Comment_mdl.class));
                commentDataShowAdapter.notifyDataSetChanged();
            }
        });
    }
}