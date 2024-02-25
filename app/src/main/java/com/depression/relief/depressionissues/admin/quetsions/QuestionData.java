package com.depression.relief.depressionissues.admin.quetsions;

import com.google.firebase.firestore.PropertyName;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class QuestionData {

    private String questionId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;

    private String docId;
    private String userSelectedOption;  // New field to store the selected option
    private double score;  // Add this line

    // Default constructor required for Firestore
    public QuestionData() {
        // Generate a unique question ID
        this.questionId = UUID.randomUUID().toString();
    }

    public QuestionData(String question, String option1, String option2, String option3, String option4, String option5,String docId) {
        // Generate a unique question ID
        this.questionId = UUID.randomUUID().toString();

        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.docId=docId;
    }

    @PropertyName("questionId")
    public String getQuestionId() {
        return questionId;
    }

    @PropertyName("question")
    public String getQuestion() {
        return question;
    }

    @PropertyName("option1")
    public String getOption1() {
        return option1;
    }

    @PropertyName("option2")
    public String getOption2() {
        return option2;
    }

    @PropertyName("option3")
    public String getOption3() {
        return option3;
    }

    @PropertyName("option4")
    public String getOption4() {
        return option4;
    }

    @PropertyName("option5")
    public String getOption5() {
        return option5;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getUserSelectedOption() {
        return userSelectedOption;
    }

    public void setUserSelectedOption(String userSelectedOption) {
        this.userSelectedOption = userSelectedOption;
    }

    public List<String> getOptionsList() {
        return Arrays.asList(option1, option2, option3, option4, option5);
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}