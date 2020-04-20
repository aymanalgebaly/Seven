package com.compubase.seven.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.ui.activity.AboutUsActivity;
import com.yariksoffice.lingver.Lingver;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends DialogFragment {


    ImageView quit;
    EditText text;
    Button submit,submit2;
    private SharedPreferences preferences;
    private String string;

    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);


        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(getContext(), string);

        Objects.requireNonNull(this.getDialog()).setTitle("تأكيد التسجيل");

        quit = view.findViewById(R.id.quit);
        text = view.findViewById(R.id.text);
        submit = view.findViewById(R.id.submit);
        submit2 = view.findViewById(R.id.submit2);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new AddButtonClick(text.getText().toString()));
                dismiss();
            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new AddButtonClick("nosms"));
                dismiss();
            }
        });

        return view;
    }

}
