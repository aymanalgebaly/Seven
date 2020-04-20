package com.compubase.seven.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.seven.R;
import com.yariksoffice.lingver.Lingver;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageActivity extends AppCompatActivity {

    @BindView(R.id.btn_ar)
    TextView btnAr;
    @BindView(R.id.btn_en)
    TextView btnEn;
    @BindView(R.id.btn_tr)
    TextView btnTr;
    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("lan", MODE_PRIVATE);
        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(LanguageActivity.this, string);


        SharedPreferences.Editor editor = getSharedPreferences("lan", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("lan", MODE_PRIVATE);

        btnAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lingver.getInstance().setLocale(LanguageActivity.this,"ar");
                editor.putString("lan", "ar");

                editor.apply();

                startActivity(new Intent(LanguageActivity.this, HomeActivity.class));
            }
        });

        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lingver.getInstance().setLocale(LanguageActivity.this, "en");
                editor.putString("lan", "en");
                startActivity(new Intent(LanguageActivity.this, HomeActivity.class));

                editor.apply();
            }
        });

        btnTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lingver.getInstance().setLocale(LanguageActivity.this, "tr");
                editor.putString("lan", "tr");
                startActivity(new Intent(LanguageActivity.this, HomeActivity.class));

                editor.apply();
            }
        });
    }
}
