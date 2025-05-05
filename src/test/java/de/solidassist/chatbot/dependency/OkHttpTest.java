package de.solidassist.chatbot.dependency;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * This class tests the integration of the OkHttp dependency by making a GET request
 * to a sample public API (https://jsonplaceholder.typicode.com/todos/1). It verifies
 * that OkHttp can successfully send requests and handle the response in JSON format.
 *
 * Expected result:
 * - A JSON response from the API, indicating that the OkHttp dependency is functioning
 *   correctly.
 *
 * Dependencies tested:
 * - OkHttp (com.squareup.okhttp3:okhttp)
 */

public class OkHttpTest {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        // Send HTTP request to a test API
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/todos/1")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Print the response body
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
