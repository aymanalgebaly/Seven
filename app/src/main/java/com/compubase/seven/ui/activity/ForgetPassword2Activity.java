package com.compubase.seven.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.compubase.seven.R;
import com.compubase.seven.helper.RequestHandler;
import com.yariksoffice.lingver.Lingver;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword2Activity extends AppCompatActivity {

    EditText email;
    Button button;
    TextView textView;

    String emailstring;
    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        preferences = getSharedPreferences("lan", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(ForgetPassword2Activity.this, string);

        email = findViewById(R.id.email);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);

        textView.setText("تم ارسال رمز للبريد الألكترونى");
        email.setHint("ادخل الرمز");
        button.setText("تغيير");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            emailstring = extras.getString("email");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                volleyConnection();
            }
        });
    }
    private void volleyConnection()
    {
        String GET_JSON_DATA_HTTP_URL = "http://educareua.com/seven.asmx/return_password?";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showMessage(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.toString());

            }

        }) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email", emailstring);
                params.put("code", email.getText().toString());
                return params;
            }

        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }
}
