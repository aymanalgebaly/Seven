package com.compubase.seven.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.compubase.seven.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    @BindView(R.id.userimage)
    CircleImageView userimage;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_userName)
    TextView etUserName;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.useremailedit)
    TextView useremailedit;
    @BindView(R.id.et_password)
    TextView etPassword;
    @BindView(R.id.et_city)
    TextView etCity;
    @BindView(R.id.btn_save)
    Button btnSave;
    private Unbinder unbinder;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, inflate);


        return inflate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.userimage, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userimage:
                break;
            case R.id.btn_save:
                break;
        }
    }
}
