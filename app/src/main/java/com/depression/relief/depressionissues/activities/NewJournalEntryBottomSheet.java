package com.depression.relief.depressionissues.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.depression.relief.depressionissues.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewJournalEntryBottomSheet extends BottomSheetDialogFragment {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private ImageView buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_journal_entry_bottom_sheet, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                // Call the saveJournalEntry method from JournalActivity to save the entry to Firestore
                ((JournalActivity) requireActivity()).saveJournalEntry(title, description);

                dismiss(); // Close the bottom sheet after submitting
            }
        });

        return view;    }
}
