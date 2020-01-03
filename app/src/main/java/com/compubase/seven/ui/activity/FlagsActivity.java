package com.compubase.seven.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.seven.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlagsActivity extends AppCompatActivity {

    @BindView(R.id.egy_img)
    ImageView egyImg;
    @BindView(R.id.jordon_img)
    ImageView jordonImg;
    @BindView(R.id.turky_img)
    ImageView turkyImg;
    @BindView(R.id.saudi_img)
    ImageView saudiImg;
    @BindView(R.id.emarat_img)
    ImageView emaratImg;
    @BindView(R.id.gzaer_img)
    ImageView gzaerImg;
    @BindView(R.id.suden_img)
    ImageView sudenImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.egy_img, R.id.jordon_img, R.id.turky_img, R.id.saudi_img, R.id.emarat_img, R.id.gzaer_img, R.id.suden_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.egy_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.jordon_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.turky_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.saudi_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.emarat_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.gzaer_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
            case R.id.suden_img:
                Toast.makeText(this, "تم تغيير الدولة بنجاح", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
