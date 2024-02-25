package com.depression.relief.depressionissues.moodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.MainActivity;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.quetsions.QuestionData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoodCheckActivity extends AppCompatActivity {

    private TextView textViewQuestionNumber;
    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private RadioButton radioButtonOption1, radioButtonOption2, radioButtonOption3, radioButtonOption4, radioButtonOption5;
    private ImageView buttonNext, backbtn;
    private TextView textViewScore;

    private List<QuestionData> questionList;
    private int currentQuestionIndex = 0;
    private double overallScore = 0;

    private FirebaseFirestore db;
    private Map<Integer, Integer> optionValues;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_check);

        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioButtonOption1 = findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = findViewById(R.id.radioButtonOption2);
        radioButtonOption3 = findViewById(R.id.radioButtonOption3);
        radioButtonOption4 = findViewById(R.id.radioButtonOption4);
        radioButtonOption5 = findViewById(R.id.radioButtonOption5);
        buttonNext = findViewById(R.id.buttonNext);
        backbtn = findViewById(R.id.backbtn);
        textViewScore = findViewById(R.id.textViewScore);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch questions from Firestore
        loadQuestionsFromFirestore();

        // Initialize option values
        optionValues = new HashMap<>();
        optionValues.put(0, 10);
        optionValues.put(1, 8);
        optionValues.put(2, 6);
        optionValues.put(3, 4);
        optionValues.put(4, 2);


        // Set click listener for the "Next" button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if an option is selected
                int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    Toast.makeText(MoodCheckActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    // Calculate the score based on the selected option
                    double score = calculateScore(selectedOptionId);

                    // Store the user's selection and score (you might want to save this to Firestore or elsewhere)
                    QuestionData currentQuestion = questionList.get(currentQuestionIndex);
                    currentQuestion.setUserSelectedOption(getSelectedOptionText(selectedOptionId));
                    currentQuestion.setScore(score);

                    // Calculate the overall score
                    overallScore += score;

                    // Move to the next question
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questionList.size()) {
                        setQuestion();
                    } else {
                        // Display the user's selections and scores
                        displayUserSelections();

                        // Display the overall score
                        displayOverallScore();

                        Intent intent = new Intent(MoodCheckActivity.this, MoodReportActivity.class);
                        intent.putExtra("overallScore", overallScore);
                        startActivity(intent);
                    }
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadQuestionsFromFirestore() {
        // Fetch questions and options from Firestore
        // Replace "questions" with your actual Firestore collection name
        db.collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    questionList = queryDocumentSnapshots.toObjects(QuestionData.class);

                    // Set the first question
                    setQuestion();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MoodCheckActivity.this, "Error loading questions", Toast.LENGTH_SHORT).show();
                });
    }

    private void setQuestion() {

        if (questionList == null || questionList.isEmpty()) {
            Toast.makeText(this, "Server is unable to help you!...", Toast.LENGTH_SHORT).show();
            navigateToMainActivity();
            return;
        }

        // Set question number and text
        textViewQuestionNumber.setText("Question " + (currentQuestionIndex + 1) + "/" + questionList.size());
        textViewQuestion.setText(questionList.get(currentQuestionIndex).getQuestion());

        // Set options
        List<String> options = questionList.get(currentQuestionIndex).getOptionsList();

        if (options != null && options.size() >= 5) {
            radioButtonOption1.setText(options.get(0));
            radioButtonOption2.setText(options.get(1));
            radioButtonOption3.setText(options.get(2));
            radioButtonOption4.setText(options.get(3));
            radioButtonOption5.setText(options.get(4));
        } else {
            navigateToMainActivity();
        }

        // Clear selection
        radioGroupOptions.clearCheck();
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        Animatoo.INSTANCE.animateShrink(this);
    }

    private void displayUserSelections() {
        StringBuilder result = new StringBuilder();
        for (QuestionData question : questionList) {
            result.append("Question: ").append(question.getQuestion()).append("\n")
                    .append("Selected Option: ").append(question.getUserSelectedOption()).append("\n")
                    .append("Score: ").append(question.getScore()).append("\n\n");
        }
        textViewScore.setText(result.toString());
    }

    private void displayOverallScore() {
        textViewScore.setText("Overall Score: " + overallScore);
    }


    private double calculateScore(int selectedOptionId) {
        // Map the selected option to its corresponding value
        return optionValues.get(getOptionPositionById(selectedOptionId));
    }

    private String getSelectedOptionText(int selectedOptionId) {
        switch (getOptionPositionById(selectedOptionId)) {
            case 0:
                return questionList.get(currentQuestionIndex).getOption1();
            case 1:
                return questionList.get(currentQuestionIndex).getOption2();
            case 2:
                return questionList.get(currentQuestionIndex).getOption3();
            case 3:
                return questionList.get(currentQuestionIndex).getOption4();
            case 4:
                return questionList.get(currentQuestionIndex).getOption5();
            default:
                return "";
        }
    }

    private int getOptionPositionById(int selectedOptionId) {
        switch (selectedOptionId) {
            case R.id.radioButtonOption1:
                return 0;
            case R.id.radioButtonOption2:
                return 1;
            case R.id.radioButtonOption3:
                return 2;
            case R.id.radioButtonOption4:
                return 3;
            case R.id.radioButtonOption5:
                return 4;
            default:
                return -1;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.INSTANCE.animateShrink(this);
    }
}
