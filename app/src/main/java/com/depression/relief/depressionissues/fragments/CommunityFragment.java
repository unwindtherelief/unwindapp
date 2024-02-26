package com.depression.relief.depressionissues.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.adapters.CommunityPagerAdapter;
import com.depression.relief.depressionissues.models.Comment_mdl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class CommunityFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    ImageView userimg;
    String userId;
    TabLayout categorytabs;
    ViewPager viewpager;
    private FloatingActionButton fabAddComment;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImagePath = ""; // To store the selected image path
    String currimageimageUrl;   //we add current userimage
    String currentusername;    //we add in that current user name


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        userimg = view.findViewById(R.id.userimg);
        categorytabs = view.findViewById(R.id.categorytabs);
        viewpager = view.findViewById(R.id.viewpager);
        fabAddComment = view.findViewById(R.id.fabAddComment);


        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();


        CommunityPagerAdapter pagerAdapter = new CommunityPagerAdapter(getActivity().getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        categorytabs.setupWithViewPager(viewpager);

        // user image fetch
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("FirestoreError", "Error getting user document: " + error.getMessage());
                    return;
                }
                if (value != null && value.exists()) {
                    if (value.contains("selectedImageUrl")) {
                        currimageimageUrl = value.getString("selectedImageUrl");
                        currentusername = value.getString("firstname");

                        Glide.with(getActivity().getApplicationContext()).load(currimageimageUrl).into(userimg);
                    } else {
                        Log.e("FirestoreError", "Document does not contain 'selectedImageUrl' field");
                    }
                } else {
                    Log.e("FirestoreError", "User document does not exist");
                }
            }
        });


        fabAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentPopup();
            }
        });

        return view;
    }

    private void showCommentPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_comment, null);

        final EditText etComment = view.findViewById(R.id.etComment);
        final ImageView ivAddImage = view.findViewById(R.id.ivAddImage);
        final Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        final Button btnSubmit = view.findViewById(R.id.btnSubmit);

        builder.setView(view);
        final AlertDialog dialog = builder.create();

        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = etComment.getText().toString();
                String selectedCategory = spinnerCategory.getSelectedItem().toString();

                if (!selectedCategory.isEmpty()) {
                    // Save the comment and category to Firestore
                    saveCommentToFirestore(commentText, selectedCategory, selectedImagePath);

                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }


   /* private void saveCommentToFirestore(String commentText, String selectedCategory, String commentimagePath) {
        // Use the Firebase Firestore instance and collection reference
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference commentsCollection = db.collection("comments");

        Comment_mdl newComment = new Comment_mdl(commentText, selectedCategory, new Date(), commentimagePath, currimageimageUrl, currentusername);

        // Add the comment to Firestore
        commentsCollection.add(newComment).addOnSuccessListener(documentReference -> {
            Toast.makeText(getActivity(), "Comment added successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Log.e("CommentTAG", "saveCommentToFirestore: " + e.getMessage());
            Toast.makeText(getActivity(), "Failed to add the comment", Toast.LENGTH_SHORT).show();
        });

    }*/

    private void saveCommentToFirestore(String commentText, String selectedCategory, String commentimagePath) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference commentsCollection = db.collection("comments");

        Comment_mdl newComment = new Comment_mdl(commentText, selectedCategory, new Date(), commentimagePath, currimageimageUrl, currentusername);

        // Set the category for the comment
        newComment.setSelectedCategory(selectedCategory);

        commentsCollection.add(newComment)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(), "Comment added successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("CommentTAG", "saveCommentToFirestore: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed to add the comment", Toast.LENGTH_SHORT).show();
                });
    }


    private void openGallery() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            selectedImagePath = getRealPathFromURI(selectedImageUri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    @Override
    public void onResume() {
        super.onResume();

        int trendingTabIndex = 1;

        viewpager.setCurrentItem(trendingTabIndex);
    }

}