package com.compubase.seven.ui.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.yariksoffice.lingver.Lingver;

import org.greenrobot.eventbus.EventBus;


public class SendMessageFragment extends DialogFragment {

    ImageView quit;
    EditText text;
    Button submit;
    TextView title;
    private SharedPreferences preferences;
    private String string;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_add_comment,container);



        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Lingver.getInstance().setLocale(getContext(), string);
        }

        this.getDialog().setTitle("ارسال رسالة");

        quit = rootView.findViewById(R.id.quit);
        text = rootView.findViewById(R.id.text);
        submit = rootView.findViewById(R.id.submit);
        title = rootView.findViewById(R.id.title);

        text.setHint("اكتب الرسالة");
        submit.setText("ارسل");
        title.setText("ارسال رسالة");

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new AddButtonClick(text.getText().toString(),"message"));
                dismiss();
            }
        });


        return rootView;


    }

}
