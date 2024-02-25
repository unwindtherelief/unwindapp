package com.depression.relief.depressionissues.admin.quetsions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.depression.relief.depressionissues.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private List<QuestionData> questionList;
    private CollectionReference questionsRef;
    Context context;
    private QuestionAdapterListener listener;
    private FirebaseFirestore db;


    public interface QuestionAdapterListener {
        void onQuestionDeleted(int position);
    }

    public QuestionAdapter(List<QuestionData> questionList, CollectionReference questionsRef, Context context, QuestionAdapterListener listener) {
        this.questionList = questionList;
        this.questionsRef = questionsRef;
        this.context = context;
        this.listener = listener;
        db = FirebaseFirestore.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        QuestionData questionData = questionList.get(position);

        // Bind data to ViewHolder
        holder.textViewQuestion.setText(questionData.getQuestion());

        // Bind options to TextView
        List<String> optionsList = questionData.getOptionsList();
        String optionsText = TextUtils.join(", ", optionsList);
        holder.textViewOptions.setText("Options: " + optionsText);

        // Set up RecyclerView for options
        OptionsAdapter optionsAdapter = new OptionsAdapter(optionsList);
        holder.recyclerViewOptions.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewOptions.setAdapter(optionsAdapter);

        String questionId = questionList.get(position).getQuestionId();

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteQuestionDialog(questionData,position);
                /*if (questionId != null) {
                    deleteQuestionFromFirebase(questionId);
                } else {
                    Toast.makeText(context, "Sorry We can not found Anything!", Toast.LENGTH_SHORT).show();
                }*/
//                deleteQuestionFromFirebase(questionId);

            }
        });
    }

    private void showDeleteQuestionDialog(QuestionData questionData,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Question")
                .setMessage("Are you sure you want to delete this Question?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove the review from Firestore
                    removeQuestion(questionData,position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void removeQuestion(QuestionData questionData,int position) {
        db.collection("questions")
                .document(questionData.getDocId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    questionList.remove(questionData);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Question deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("QuestionLog", "removeQuestion: " + e.getMessage());
                    Toast.makeText(context, "Failed to delete the Question", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion;
        TextView textViewOptions;
        RecyclerView recyclerViewOptions;
        Button buttonDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);
            recyclerViewOptions = itemView.findViewById(R.id.recyclerViewOptions);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

//    private void deleteQuestionFromFirebase(String questionId) {
//        // Assuming your Firestore collection reference is questionsRef
//
//        if(questionsRef == null)
//        {
//            Toast.makeText(context, "Reference is null.", Toast.LENGTH_SHORT).show();
//        }else
//        {
//            questionsRef.document(questionId)
//                    .delete()
//                    .addOnSuccessListener(aVoid -> {
//                        int position = findPositionByQuestionId(questionId);
//                        if (position != RecyclerView.NO_POSITION) {
//                            questionList.remove(position);
//                            notifyItemRemoved(position);
//                            Toast.makeText(context, "Question deleted successfully", Toast.LENGTH_SHORT).show();
//                            Log.d("DeleteSuccess", "Question deleted successfully");
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Log.e("DeleteError", "Error deleting question: " + e.getMessage());
//                        Toast.makeText(context, "Error deleting question", Toast.LENGTH_SHORT).show();
//                    });
//        }
//
//
//    }

    private int findPositionByQuestionId(String questionId) {
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getQuestionId().equals(questionId)) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

}
