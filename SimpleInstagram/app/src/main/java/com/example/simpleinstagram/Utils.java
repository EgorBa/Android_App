package com.example.simpleinstagram;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class Utils {
    private static final String VK_API_BASE_URL = "https://api.vk.com/";
    private static final String VK_USERS_GET = "/method/photos.search";
    private static final String PRAM_USERS_ID = "access_token";
    private static final String PARAM_VERSION = "v";
    private static final String PARAM_VERSION1 = "q";

    static URL generatedURL() {
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PRAM_USERS_ID, BuildConfig.API_KEY)
                .appendQueryParameter(PARAM_VERSION, "5.8")
                .appendQueryParameter(PARAM_VERSION1, "itmo")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean flag = scanner.hasNext();
            if (flag){
                return  scanner.next();
            }else{
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
