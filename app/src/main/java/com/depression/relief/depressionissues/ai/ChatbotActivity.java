package com.depression.relief.depressionissues.ai;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.depression.relief.depressionissues.databinding.ActivityChatbotBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {
    ActivityChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatbotBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        messageList = new ArrayList<>();

        chatAdapter = new ChatAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        // Set click listener for send button
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    /*    private void sendMessage() {
            String userMessage = binding.userInput.getText().toString().trim();
            binding.userInput.getText().clear();

            addMessage("User", userMessage, true);

            if (containsMedicalKeywords(userMessage)) {
                sendGPT3Request(userMessage);
            } else {
                addMessage("ChatGPT", "Sorry, I can only help with medical issues.", false);
            }
        }*/
    private void sendMessage() {
        String userMessage = binding.userInput.getText().toString().trim();
        binding.userInput.getText().clear();

        addMessage("User", userMessage, true);

        if (containsWellBeingKeywords(userMessage)) {
            addMessage("ChatGPT", "Thank you for asking! I'm here to help you. How can I assist you today?", false);
        } else if (containsWishingKeywords(userMessage)) {
            addMessage("ChatGPT", "Thank you! Wishing you a great day too!", false);
        } else if (containsGreetingKeywords(userMessage)) {
            addMessage("ChatGPT", "Hello! How can I assist you today?", false);
        } else if (containsMedicalKeywords(userMessage)) {
            sendGPT3Request(userMessage);
        } else {
            addMessage("ChatGPT", "Sorry, I can only help with mental health and well-being topics.", false);
        }
    }

    private boolean containsWishingKeywords(String message) {
        String[] wishingKeywords = {"good morning", "good afternoon", "good evening", "thank you", "thanks"};

        for (String keyword : wishingKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsWellBeingKeywords(String message) {
        String[] wellBeingKeywords = {"how are you", "how's it going", "are you okay"};

        for (String keyword : wellBeingKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsGreetingKeywords(String message) {
        String[] greetingKeywords = {"hello", "hi", "hey", "greetings"};

        for (String keyword : greetingKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private void sendGPT3Request(String userMessage) {
        ChatGPTApi.sendRequest(userMessage, new ChatGPTApi.ChatGPTCallback() {
            @Override
            public void onSuccess(String response) {
                // Add GPT-3 response to the chat
                addMessage("ChatGPT", response, false);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle GPT-3 API failure
                addMessage("ChatGPT", errorMessage, false);
            }
        });
    }


    private boolean containsMedicalKeywords(String message) {
//        String[] medicalKeywords = {"medical", "health", "doctor", "hospital", "symptom", "treatment", "prescription", "Mindfulness Meditation", "Cognitive Behavioral Therapy (CBT)", "Yoga for Mental Health",
//                "Breathing Exercises", "Positive Affirmations", "Stress Management Techniques", "Gratitude Journaling", "Self-Care Practices", "Healthy Sleep Habits", "Nature Therapy", "Social Connection",
//                "Relaxation Techniques", "Coping Strategies", "Emotional Support", "Mind-Body Connection", "Art Therapy", "Laughter Therapy", "Goal Setting for Well-being", "Nutrition for Mental Health",
//                "Time Management for Stress Reduction", "Music Therapy", "Physical Exercise for Mood Boost", "Mindful Walking", "Acceptance and Commitment Therapy (ACT)", "Journaling for Emotional Release",
//                "Guided Imagery", "Holistic Wellness", "Emotional Intelligence", "Creative Outlets", "Self-Compassion Practices", "Relaxation Techniques", "Deep Breathing Exercises", "Progressive Muscle Relaxation (PMR)", "Guided Imagery", "Stress Balls and Fidget Toys", "Aromatherapy", "Laughter Therapy", "Nature Therapy (Ecotherapy)", "Massage Therapy", "Acupressure", "Yoga for Stress Relief", "Tai Chi", "Biofeedback", "Time Management Strategies", "Expressive Arts Therapy", "Music Therapy", "Dance Therapy", "Humor Therapy", "ournaling for Stress Management",
//                "Gratitude Practices", "Positive Affirmations", "Social Support", "Healthy Sleep Habits", "Physical Exercise for Stress Reduction", "Workplace Stress Management", "Self-Compassion Practices",
//                "Progressive Relaxation Techniques", "Stress Reduction Workshops"};

        String[] medicalKeywords = {"depression", "stress", "anxiety", "well-being", "mindfulness", "therapy", "coping", "greetings"};


        for (String keyword : medicalKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private void addMessage(String sender, String message, boolean isUser) {
        Message newMessage = new Message(sender, message, isUser);
        messageList.add(newMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        binding.chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }
}