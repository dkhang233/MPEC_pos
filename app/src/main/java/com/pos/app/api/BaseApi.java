package com.pos.app.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.app.util.ConfigLoader;
import okhttp3.*;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

// Lớp cơ sở cho các API
public class BaseApi {
    public static final String apiUrl = ConfigLoader.getApiUrl();
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .header("Authorization", "Bearer " + getToken())  // Thêm token vào header
                        .header("Content-Type", "application/json");

                return chain.proceed(builder.build());
            })
            .build();

    // Lấy token
    private static String getToken() {
        return "your-access-token";
    }

    // Xử lý response chung
    private static String handleResponse(Response response) throws IOException {
        // Nếu response không thành công
        if (!response.isSuccessful()) {
            int statusCode = response.code();
            switch (statusCode) {
                case 401 -> throw new IOException("Unauthorized");
                case 403 -> throw new IOException("Forbidden");
                case 404 -> throw new IOException("Not Found");
                case 500 -> throw new IOException("Internal Server Error");
                default -> throw new IOException("HTTP Error: " + statusCode);
            }
        }

        // Nếu response thành công
        return response.body() != null ? response.body().string() : "";
    }


    // Gửi request 
    public static <T> String request(String endpoint, String method, T data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = RequestBody.create(objectMapper.writeValueAsString(data), MediaType.get("application/json; charset=utf-8"));
        Request.Builder builder = new Request.Builder().url(apiUrl + endpoint);

        switch (method.toUpperCase()) {
            case "GET" -> builder.get();
            case "POST" -> builder.post(requestBody);
            case "PUT" -> builder.put(requestBody);
            case "DELETE" -> builder.delete(requestBody);
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        }

        try (Response response = client.newCall(builder.build()).execute()) {
            return handleResponse(response);
        }
    }
}
