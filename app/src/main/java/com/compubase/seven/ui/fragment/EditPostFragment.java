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
import com.compubase.seven.helper.RequestHandler;
import com.compubase.seven.helper.TinyDB;
import com.yariksoffice.lingver.Lingver;

import java.util.HashMap;
import java.util.Map;


public class EditPostFragment extends DialogFragment {

    ImageView quit;
    EditText text1,text2,text3;
    Button submit;

    TinyDB tinyDB;

    String id,phone,title,content;
    private SharedPreferences preferences;
    private String string;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_edit_post,container);


        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Lingver.getInstance().setLocale(getContext(), string);
        }


        tinyDB = new TinyDB(getActivity());

        id = tinyDB.getString("postID");
        phone = tinyDB.getString("postPhone");
        title = tinyDB.getString("postTitle");
        content = tinyDB.getString("postDescription");

        this.getDialog().setTitle("تعديل الأعلان");

        quit = rootView.findViewById(R.id.quit);
        text1 = rootView.findViewById(R.id.text1);
        text2 = rootView.findViewById(R.id.text2);
        text3 = rootView.findViewById(R.id.text3);
        submit = rootView.findViewById(R.id.submit);

        text1.setText(phone);
        text2.setText(title);
        text3.setText(content);

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
        String GET_JSON_DATA_HTTP_URL = "http://alosboiya.com.sa/wsnew.asmx/edite_post?";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("True"))
                        {
                            showMessage("تم تحديث الأعلان");
                            dismiss();
                        }else
                            {
                                showMessage("خطأ");
                            }

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
                params.put("id_post", id);
                params.put("phone", text1.getText().toString());
                params.put("address", text2.getText().toString());
                params.put("des", text3.getText().toString());
                return params;
            }



        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void showMessage(String _s) {
        Toast.makeText(getActivity(), _s, Toast.LENGTH_LONG).show();
    }
}
