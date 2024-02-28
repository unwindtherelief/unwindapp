package com.depression.relief.depressionissues.ai;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.depression.relief.depressionissues.databinding.ActivityChatbotBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatbotActivity extends AppCompatActivity {
    ActivityChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


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


        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMessage = binding.userInput.getText().toString().trim();

                if (userMessage.isEmpty()) {
                    Toast.makeText(ChatbotActivity.this, "Please Enter Something !", Toast.LENGTH_SHORT).show();
                } else {
                    addtoChat(userMessage, Message.SENT_BY_ME);
                    binding.userInput.setText("");
                    callAPI(userMessage);
                    binding.suggestion1.setVisibility(View.GONE);
                    binding.suggestion2.setVisibility(View.GONE);
                    binding.suggestion3.setVisibility(View.GONE);
                    binding.suggestion4.setVisibility(View.GONE);
                }
            }
        });

        binding.suggestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSuggestion1Message();

                binding.suggestion1.setVisibility(View.GONE);
                binding.suggestion2.setVisibility(View.GONE);
                binding.suggestion3.setVisibility(View.GONE);
                binding.suggestion4.setVisibility(View.GONE);
            }
        });

        binding.suggestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSuggestion2Message();

                binding.suggestion1.setVisibility(View.GONE);
                binding.suggestion2.setVisibility(View.GONE);
                binding.suggestion3.setVisibility(View.GONE);
                binding.suggestion4.setVisibility(View.GONE);
            }
        });

        binding.suggestion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSuggestion3Message();

                binding.suggestion1.setVisibility(View.GONE);
                binding.suggestion2.setVisibility(View.GONE);
                binding.suggestion3.setVisibility(View.GONE);
                binding.suggestion4.setVisibility(View.GONE);
            }
        });

        binding.suggestion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSuggestion4Message();

                binding.suggestion1.setVisibility(View.GONE);
                binding.suggestion2.setVisibility(View.GONE);
                binding.suggestion3.setVisibility(View.GONE);
                binding.suggestion4.setVisibility(View.GONE);
            }
        });

    }

    public void addtoChat(String messege, String sentby) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(messege, sentby));
                chatAdapter.notifyDataSetChanged();
                binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
            }
        });

    }

    private void callAPI(String userMessage) {

        if (isMentalHealthQuestion(userMessage)) {

            messageList.add(new Message("Typing.. ", Message.SENT_BY_BOT));

            JSONObject jsonBody = null;
            try {
                jsonBody = new JSONObject();
                jsonBody.put("model", "gpt-3.5-turbo");

                JSONArray jsonArray = new JSONArray();
                JSONObject object = new JSONObject();

                object.put("role", "user");
                object.put("content", userMessage);

                jsonArray.put(object);
                jsonBody.put("messages", jsonArray);
//            jsonBody.put("max_tokens", 4000);
//            jsonBody.put("temperature", 0);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer ")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    addResponse("Failed to get Response due to " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("choices");
                            String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                            addResponse(result.trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        addResponse("Failed to get Response due to " + response.body().string());
                    }
                }
            });
        } else {
            addResponse("I'm sorry, i can only provide you information on stress relief and mental health well-being");
        }

    }

    private boolean isMentalHealthQuestion(String userMessage) {
        String[] medicalKeywords = {"medical", "health", "doctor", "hospital", "symptom", "treatment", "prescription", "Mindfulness Meditation", "Cognitive Behavioral Therapy (CBT)", "Yoga for Mental Health",
                "Breathing Exercises", "Positive Affirmations", "Stress Management Techniques", "Gratitude Journaling", "Self-Care Practices", "Healthy Sleep Habits", "Nature Therapy", "Social Connection",
                "Relaxation Techniques", "Coping Strategies", "Emotional Support", "Mind-Body Connection", "Art Therapy", "Laughter Therapy", "Goal Setting for Well-being", "Nutrition for Mental Health",
                "Time Management for Stress Reduction", "Music Therapy", "Physical Exercise for Mood Boost", "Mindful Walking", "Acceptance and Commitment Therapy (ACT)", "Journaling for Emotional Release",
                "Guided Imagery", "Holistic Wellness", "Emotional Intelligence", "Creative Outlets", "Self-Compassion Practices", "Relaxation Techniques", "Deep Breathing Exercises", "Progressive Muscle Relaxation (PMR)",
                "Guided Imagery", "Stress Balls and Fidget Toys", "Aromatherapy", "Laughter Therapy", "Nature Therapy (Ecotherapy)", "Massage Therapy", "Acupressure", "Yoga for Stress Relief", "Tai Chi", "Biofeedback",
                "Time Management Strategies", "Expressive Arts Therapy", "Music Therapy", "Dance Therapy", "Humor Therapy", "ournaling for Stress Management",
                "Gratitude Practices", "Positive Affirmations", "Social Support", "Healthy Sleep Habits", "Physical Exercise for Stress Reduction", "Workplace Stress Management", "Self-Compassion Practices",
                "Progressive Relaxation Techniques", "Stress Reduction Workshops", "stress relief", "anxiety reduction", "relaxation techniques", "mindfulness", "meditation", "deep breathing exercises",
                "emotional well-being", "coping strategies", "mental health support", "self-care practices", "positive mindset", "mood enhancement", "emotional resilience", "stress management", "mental wellness",
                "tranquility", "mental clarity", "serenity", "calmness", "holistic health", "wellness tips", "mood stabilization", "happiness boosters", "emotional balance", "inner peace", "self-compassion",
                "well-being activities", "mental strength", "relaxation methods", "mental rejuvenation", "peace of mind", "coping mechanisms", "positive psychology", "mental fitness", "emotional health",
                "self-reflection", "joyfulness", "stress reduction techniques", "relaxation rituals", "resilience building", "mood elevation", "mental harmony", "optimism", "self-love practices", "mental rejuvenation",
                "therapeutic activities", "holistic healing", "mental fortitude", "emotional support", "wellness resources", "stress", "depression", "feelings", "relaxtion", "depression", "hopless feelings", "yoga", "exercise", "mood","hello", "hi","bye","greetings", "hey", "good morning", "good afternoon", "good evening","thank you","i cannot stop feeling hopeless"};

        for (String keyword : medicalKeywords) {
            if (userMessage.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }

        }
        return false;
    }

    private void addResponse(String s) {
        messageList.remove(messageList.size() - 1);
        addtoChat(s, Message.SENT_BY_BOT);
    }

    private void sendSuggestion1Message() {
        String userMessage = binding.suggestion1.getText().toString().trim();
        binding.userInput.getText().clear();

        if (userMessage.isEmpty()) {
            Toast.makeText(ChatbotActivity.this, "Please Enter Something !", Toast.LENGTH_SHORT).show();

        } else {
            addtoChat(userMessage, Message.SENT_BY_ME);
            binding.userInput.setText("");
            callAPI(userMessage);
            binding.suggestion1.setVisibility(View.GONE);
            binding.suggestion2.setVisibility(View.GONE);
            binding.suggestion3.setVisibility(View.GONE);
            binding.suggestion4.setVisibility(View.GONE);
        }

    }

    private void sendSuggestion2Message() {
        String userMessage = binding.suggestion2.getText().toString().trim();
        binding.userInput.getText().clear();


        addtoChat(userMessage, Message.SENT_BY_ME);

        if (userMessage.isEmpty()) {
            Toast.makeText(ChatbotActivity.this, "Please Enter Something!...", Toast.LENGTH_SHORT).show();

        } else {
            addtoChat(userMessage, Message.SENT_BY_ME);
            binding.userInput.setText("");
            callAPI(userMessage);
            binding.suggestion1.setVisibility(View.GONE);
            binding.suggestion2.setVisibility(View.GONE);
            binding.suggestion3.setVisibility(View.GONE);
            binding.suggestion4.setVisibility(View.GONE);
        }

    }

    private void sendSuggestion3Message() {
        String userMessage = binding.suggestion3.getText().toString().trim();
        binding.userInput.getText().clear();


        addtoChat(userMessage, Message.SENT_BY_ME);

        if (userMessage.isEmpty()) {
            Toast.makeText(ChatbotActivity.this, "Please Enter Something!...", Toast.LENGTH_SHORT).show();

        } else {
            addtoChat(userMessage, Message.SENT_BY_ME);
            binding.userInput.setText("");
            callAPI(userMessage);
            binding.suggestion1.setVisibility(View.GONE);
            binding.suggestion2.setVisibility(View.GONE);
            binding.suggestion3.setVisibility(View.GONE);
            binding.suggestion4.setVisibility(View.GONE);
        }

    }

    private void sendSuggestion4Message() {
        String userMessage = binding.suggestion4.getText().toString().trim();
        binding.userInput.getText().clear();


        addtoChat(userMessage, Message.SENT_BY_ME);

        if (userMessage.isEmpty()) {
            Toast.makeText(ChatbotActivity.this, "Please Enter Something!...", Toast.LENGTH_SHORT).show();

        } else {
            addtoChat(userMessage, Message.SENT_BY_ME);
            binding.userInput.setText("");
            callAPI(userMessage);
            binding.suggestion1.setVisibility(View.GONE);
            binding.suggestion2.setVisibility(View.GONE);
            binding.suggestion3.setVisibility(View.GONE);
            binding.suggestion4.setVisibility(View.GONE);
        }

    }

}