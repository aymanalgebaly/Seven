package com.compubase.seven.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.compubase.seven.R;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.ui.activity.AboutUsActivity;
import com.compubase.seven.ui.activity.FlagsActivity;
import com.compubase.seven.ui.activity.HomeActivity;
import com.compubase.seven.ui.activity.LoginActivity;
import com.compubase.seven.ui.activity.MessagesActivity;
import com.compubase.seven.ui.activity.NotificationActivity;
import com.compubase.seven.ui.activity.PolicyActivity;
import com.compubase.seven.ui.activity.RegisterActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    @BindView(R.id.txt_about_app)
    TextView txtAboutApp;
    @BindView(R.id.txt_msg)
    TextView txtMsg;
    @BindView(R.id.txt_noti)
    TextView txtNoti;
    @BindView(R.id.txt_policy)
    TextView txtPolicy;
    @BindView(R.id.txt_flag)
    TextView txtFlag;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.txt_logOut)
    TextView txtLogOut;

    private Unbinder unbinder;
    private HomeActivity homeActivity;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);

        homeActivity = (HomeActivity) getActivity();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_about_app, R.id.txt_msg, R.id.txt_noti, R.id.txt_policy, R.id.txt_flag, R.id.txt_login, R.id.txt_register, R.id.txt_logOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_about_app:
                startActivity(new Intent(homeActivity, AboutUsActivity.class));
                break;
            case R.id.txt_msg:
                startActivity(new Intent(homeActivity, MessagesActivity.class));
                break;
            case R.id.txt_noti:
                startActivity(new Intent(homeActivity, NotificationActivity.class));
                break;
            case R.id.txt_policy:
                startActivity(new Intent(homeActivity, PolicyActivity.class));
                break;
            case R.id.txt_flag:
                startActivity(new Intent(homeActivity, FlagsActivity.class));
                break;
            case R.id.txt_login:
                startActivity(new Intent(homeActivity, LoginActivity.class));
                break;
            case R.id.txt_register:
                startActivity(new Intent(homeActivity, RegisterActivity.class));
                break;
            case R.id.txt_logOut:
                TinyDB tinyDB = new TinyDB(getActivity());
                tinyDB.putBoolean("login",false);

                startActivity(new Intent(getContext(), HomeActivity.class));
                Objects.requireNonNull(getActivity()).finish();
                break;
        }
    }
}
