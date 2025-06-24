package com.javatpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Gptintegration {
	private static final String API_KEY = "";
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";

	public static void main(String[] args) {
		
        Scanner GPTInput = new Scanner (System.in);
        System.out.print ("Please enter your query to chatGPT" +"\n");
        String input = GPTInput.nextLine();
        String getInput = input;
		
	    String prompt = input;
	    String response = getChatGPTResponse(prompt);
	    System.out.println(response);
	    }
	
    public static String getChatGPTResponse(String prompt) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)  // Connection timeout
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Write timeout
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)     // Read timeout
                .build();;

        // Create the request body as JSON
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("model", "gpt-4"); // Specify the model
        jsonMap.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
        
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        
        // Convert map to JSON string
        Gson gson = new Gson();
        String json = gson.toJson(jsonMap);

        // Create the request
        Request request = new Request.Builder()
                .url(API_URL)
//                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .post(RequestBody.create(json, JSON))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Parse and return the response
            String responseBody = response.body().string();
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
            return jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to get response from ChatGPT API.";
        }
	
	}
}
