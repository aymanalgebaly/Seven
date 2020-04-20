package com.compubase.seven.helper;

import android.app.Application;
import android.content.SharedPreferences;


import com.yariksoffice.lingver.Lingver;

import java.util.Objects;

public class LanguageClass extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            SharedPreferences preferences = getSharedPreferences("lan",MODE_PRIVATE);
            Lingver.init(this, Objects.requireNonNull(preferences.getString("lan", "en")));
        }
    }

