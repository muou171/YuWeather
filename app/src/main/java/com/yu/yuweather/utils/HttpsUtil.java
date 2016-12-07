package com.yu.yuweather.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpsUtil {
    public static void sendHttpsRequest(String address, HttpsCallbackListener listener) {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() == HTTP_OK) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                if (listener != null) {
                    listener.onFinish(response.toString());
                }
            }
        } catch (IOException e) {
            listener.onError(e);
        } finally {
            connection.disconnect();
        }
    }

    public interface HttpsCallbackListener {
        void onFinish(String response);
        void onError(Exception e);
    }
}
