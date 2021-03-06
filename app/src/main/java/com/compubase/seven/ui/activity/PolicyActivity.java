package com.compubase.seven.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

import com.compubase.seven.R;
import com.yariksoffice.lingver.Lingver;

public class PolicyActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        preferences = getSharedPreferences("lan", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(PolicyActivity.this, string);


        WebView webView = findViewById(R.id.webView);

        webView.loadUrl("");
    }
}
