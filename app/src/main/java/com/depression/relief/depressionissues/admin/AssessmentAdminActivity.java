package com.depression.relief.depressionissues.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.quetsions.QuestionAdapter;
import com.depression.relief.depressionissues.admin.quetsions.QuestionData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdminActivity extends AppCompatActivity implements QuestionAdapter.QuestionAdapterListener {
    private EditText editTextQuestion, editTextOption1, editTextOption2, editTextOption3, editTextOption4, editTextOption5;
    private ImageView buttonSubmit;

    private FirebaseFirestore db;
    private CollectionReference questionsRef;
    private List<QuestionData> questionList;
    private QuestionAdapter questionAdapter;
    ImageView backbtn, button_showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_admin);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        questionsRef = db.collection("questions");



        // Initialize UI components
        editTextQuestion = findViewById(R.id.editText_question);
        editTextOption1 = findViewById(R.id.editText_option1);
        editTextOption2 = findViewById(R.id.editText_option2);
        editTextOption3 = findViewById(R.id.editText_option3);
        editTextOption4 = findViewById(R.id.editText_option4);
        editTextOption5 = findViewById(R.id.editText_option5);
        buttonSubmit = findViewById(R.id.button_submit);
        button_showData = findViewById(R.id.button_showData);

        backbtn = findViewById(R.id.backbtn);

        button_showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentAdminActivity.this, ShowDataActivity.class);
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(questionList, questionsRef, this, this);

        // Set onClickListener for submit button
        buttonSubmit.setOnClickListener(v -> {
            // Add the new question to Firebase
            addQuestionToFirebase();
        });

    }

    private void addQuestionToFirebase() {
        // Get data from UI
        String question = editTextQuestion.getText().toString();
        String option1 = editTextOption1.getText().toString();
        String option2 = editTextOption2.getText().toString();
        String option3 = editTextOption3.getText().toString();
        String option4 = editTextOption4.getText().toString();
        String option5 = editTextOption5.getText().toString();

        // Check if the question is not empty
        if (question.isEmpty()) {
            Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if all options are not empty
        if (option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || option5.isEmpty()) {
            Toast.makeText(this, "Please enter values for all options", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference docRef;
        docRef=questionsRef.document();

        // Create a QuestionData object
        QuestionData questionData = new QuestionData();
        questionData.setQuestion(question);
        questionData.setOption1(option1);
        questionData.setOption2(option2);
        questionData.setOption3(option3);
        questionData.setOption4(option4);
        questionData.setOption5(option5);
        questionData.setDocId(docRef.getId());

        // Store data in Firestore
        docRef.set(questionData)
                .addOnSuccessListener(documentReference -> {
//                    Toast.makeText(this, docRef.getId(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ShowDataActivity.class));
                })
                .addOnFailureListener(e -> {
                    Log.e("optionerror", "addQuestionToFirebase: " + e.getMessage());
                    // Handle the error
                    Toast.makeText(this, "Error adding question", Toast.LENGTH_SHORT).show();
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
        Animatoo.INSTANCE.animateShrink(this);
    }
}