package com.compubase.seven.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.compubase.seven.R;
import com.compubase.seven.helper.TinyDB;
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
import android.widget.Toast;

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
                    if (login){

                        displaySelectedFragment(new ProfileFragmentTest());
                    }else {
                        Toast.makeText(HomeActivity.this, "سجل الدخول اولا", Toast.LENGTH_SHORT).show();
                    }
                    return true;

                case R.id.navigation_add:
                    if (login){

                        displaySelectedFragment(new AddPostFragment());
                    }else {
                        Toast.makeText(HomeActivity.this, "سجل الدخول اولا", Toast.LENGTH_SHORT).show();
                    }
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
    private TinyDB tinyDB;
    private String user_id;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        tinyDB = new TinyDB(getApplicationContext());
        user_id = tinyDB.getString("user_id");


        imageView = findViewById(R.id.img_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login){
                    startActivity(new Intent(HomeActivity.this,AddPostNewActivity.class));
                }else {
                    Toast.makeText(HomeActivity.this, "سجل الدخول اولا", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        alartExit();
    }

    public void alartExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit ?").setCancelable(false).setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);

                        //Main2Activity.this.finish();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

}
