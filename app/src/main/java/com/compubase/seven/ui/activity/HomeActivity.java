package com.compubase.seven.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.compubase.seven.R;
import com.compubase.seven.profile.ProfileFragmentTest;
import com.compubase.seven.ui.fragment.AddPostFragment;
import com.compubase.seven.ui.fragment.HaragFragment;
import com.compubase.seven.ui.fragment.MoreFragment;
import com.compubase.seven.ui.fragment.ProfileFragment;
import com.compubase.seven.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ImageView imageView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    HaragFragment haragFragment = new HaragFragment();
                    displaySelectedFragment(haragFragment);
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_search:
                    displaySelectedFragment(new SearchFragment());
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_more:
                    displaySelectedFragment(new MoreFragment());
                    return true;

                case R.id.navigation_profile:
//                    mTextMessage.setText(R.string.title_notifications);
                    displaySelectedFragment(new ProfileFragmentTest());
                    return true;

                case R.id.navigation_add:
                    displaySelectedFragment(new AddPostFragment());
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        imageView = findViewById(R.id.img_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AddPostNewActivity.class));
            }
        });
    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
