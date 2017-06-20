package com.example.xyzreader.remote;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

class Config {

    private static String TAG = Config.class.toString();

    private static final String STRING_URL = "https://raw.githubusercontent.com/TNTest/xyzreader/master/data.json";

    static final URL BASE_URL;

    static {
        URL url;
        try {
            url = new URL(STRING_URL);
        } catch (MalformedURLException e) {
            Log.d(TAG, "Could not parse url: " + STRING_URL, e);
            throw new IllegalStateException(e);
        }

        BASE_URL = url;
    }
}
