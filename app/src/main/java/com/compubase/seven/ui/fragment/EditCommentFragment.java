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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.helper.RequestHandler;
import com.compubase.seven.helper.TinyDB;
import com.yariksoffice.lingver.Lingver;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


public class EditCommentFragment extends DialogFragment {

    ImageView quit;
    EditText text;
    Button submit;

    TinyDB tinyDB;

    String id,content;
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

        tinyDB = new TinyDB(getActivity());

        id = tinyDB.getString("commentID");
        content = tinyDB.getString("commentContent");

        this.getDialog().setTitle("تعديل تعليق");

        quit = rootView.findViewById(R.id.quit);
        text = rootView.findViewById(R.id.text);
        submit = rootView.findViewById(R.id.submit);

        text.setText(content);

        submit.setText("تعديل تعليق");

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editComment();
            }
        });


        return rootView;


    }


    private void editComment()
    {
        String GET_JSON_DATA_HTTP_URL = "http://alosboiya.com.sa/wsnew.asmx/edite_comment?";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showMessage(response);

                        EventBus.getDefault().post(new AddButtonClick(text.getText().toString()));
                        dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.toString());

            }

        }) {

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_comment", id);
                params.put("new_comment", text.getText().toString());
                return params;
            }



        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void showMessage(String _s) {
        Toast.makeText(getActivity(), _s, Toast.LENGTH_LONG).show();
    }

}
