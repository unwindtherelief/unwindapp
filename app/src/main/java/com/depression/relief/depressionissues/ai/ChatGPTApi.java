package com.depression.relief.depressionissues.ai;


import com.google.gson.JsonObject;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGPTApi {

    private static final String GPT3_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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"; // Replace with your actual API key

    public static void sendRequest(String userMessage, ChatGPTCallback callback) {
        OkHttpClient client = new OkHttpClient();

        JsonObject jsonInput = new JsonObject();
        jsonInput.addProperty("prompt", userMessage);
        jsonInput.addProperty("max_tokens", 100);

        // Log the request body for debugging
        Log.d("Request body", jsonInput.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonInput.toString());
        Request request = new Request.Builder()
                .url(GPT3_ENDPOINT)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> callback.onFailure("Error communicating with GPT-3: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    Log.d("Response code", String.valueOf(response.code()));

                    if (!response.isSuccessful()) {
                        // Log as an error
                        String responseBody = response.body().string();
                        Log.e("Error response body", responseBody);
                        runOnUiThread(() -> callback.onFailure("Unexpected code " + response.code() + ": " + responseBody));
                        return;
                    }

                    String responseBody = response.body().string();
                    Log.d("Response body", responseBody);

                    JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                    JsonArray choices = jsonResponse.getAsJsonArray("choices");

                    if (choices != null && choices.size() > 0) {
                        String botReply = choices.get(0).getAsJsonObject().get("text").getAsString();
                        runOnUiThread(() -> callback.onSuccess(botReply));
                    } else {
                        runOnUiThread(() -> callback.onFailure("No valid response from GPT-3"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> callback.onFailure("Error processing GPT-3 response"));
                }
            }
        });
    }

    private static void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public interface ChatGPTCallback {
        void onSuccess(String response);

        void onFailure(String errorMessage);
    }
}
