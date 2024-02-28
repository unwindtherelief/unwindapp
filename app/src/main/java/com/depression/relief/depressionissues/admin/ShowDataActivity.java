package com.depression.relief.depressionissues.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.quetsions.QuestionAdapter;
import com.depression.relief.depressionissues.admin.quetsions.QuestionData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity implements QuestionAdapter.QuestionAdapterListener {

    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<QuestionData> questionList;

    private FirebaseFirestore db;
    ImageView buttonGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView = findViewById(R.id.recyclerViewShowData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonGoBack = findViewById(R.id.buttonGoBack);

        db = FirebaseFirestore.getInstance();

        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(questionList, db.collection("questions"), this, this);
        recyclerView.setAdapter(questionAdapter);


        loadQuestionsFromFirebase();

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadQuestionsFromFirebase() {
        db.collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    questionList.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        QuestionData questionData = document.toObject(QuestionData.class);
                        questionList.add(questionData);
                    }

                    // Notify the adapter about the data change
                    questionAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ShowDataActivity.this, "Error loading questions", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onQuestionDeleted(int position) {
        if (position != RecyclerView.NO_POSITION && position < questionList.size()) {
            questionList.remove(position);
            questionAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Question deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateCard(this);
    }
}
