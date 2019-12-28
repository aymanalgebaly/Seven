package com.compubase.seven.ui.activity;

import android.os.Bundle;

import com.compubase.seven.R;
import com.compubase.seven.ui.fragment.HaragFragment;
import com.compubase.seven.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;

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
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_more:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.navigation_profile:
//                    mTextMessage.setText(R.string.title_notifications);
                    ProfileFragment profileFragment = new ProfileFragment();
                    displaySelectedFragment(profileFragment);
                    return true;

                case R.id.navigation_add:
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

        HaragFragment haragFragment = new HaragFragment();
        displaySelectedFragment(haragFragment);
    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
