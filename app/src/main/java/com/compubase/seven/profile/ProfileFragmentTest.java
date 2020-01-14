package com.compubase.seven.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.compubase.seven.R;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.ui.activity.HomeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ProfileFragmentTest extends Fragment {

    @BindView(R.id.btn_log_out)
    Button btnLogOut;
    @BindView(R.id.btn_settings)
    Button btnSettings;
    @BindView(R.id.btn_hesaby)
    Button btnMyAccount;
    private Unbinder unbinder;

    public ProfileFragmentTest() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile_fragment_test, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        changeBetweenFragments(new MyAccountFrament());


        return inflate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //
    @OnClick({R.id.btn_log_out, R.id.btn_settings, R.id.btn_hesaby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_log_out:
                TinyDB tinyDB = new TinyDB(getActivity());
                tinyDB.putBoolean("login",false);

                startActivity(new Intent(getContext(), HomeActivity.class));
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.btn_settings:
                changeBetweenFragments(new SettingsFragment());
                break;
            case R.id.btn_hesaby:
                changeBetweenFragments(new MyAccountFrament());
                break;
        }
    }


    private void changeBetweenFragments(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


}
