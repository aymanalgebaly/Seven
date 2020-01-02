package com.compubase.seven.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.seven.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.seven.adapter.NotificationMessageAdapter;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.NotificationMessageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MessagesActivity extends AppCompatActivity {


    public static final String TAG = "ass13";

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView title;

    NotificationMessageAdapter notificationMessageAdapter;
    ArrayList<NotificationMessageItem> notificationMessageItems = new ArrayList<>();

    RequestQueue requestQueue;

    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        tinyDB = new TinyDB(this);

        progressBar = findViewById(R.id.progressBar1);
        recyclerView = findViewById(R.id.rv);
        title = findViewById(R.id.title);

        progressBar.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationMessageAdapter = new NotificationMessageAdapter(notificationMessageItems);

        title.setText("الرسائل");

        JSON_DATA_WEB_CALL();
    }


    private void JSON_DATA_WEB_CALL() {

        progressBar.setVisibility(View.VISIBLE);


        String url;

        url = "http://educareua.com/seven.asmx/select_message_recevice?id_user_recive=" + tinyDB.getString("user_id");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        // showMessage(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage("خطأ فى الشبكة");


                    }
                }
        );


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(String Jobj) {


        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                NotificationMessageItem notificationMessageItem = new NotificationMessageItem();

                notificationMessageItem.setHeader(childJSONObject.getString("sender"));

                notificationMessageItem.setBody(childJSONObject.getString("messagedetails"));

                notificationMessageItem.setDate(childJSONObject.getString("datee"));

                notificationMessageItem.setImg(childJSONObject.getString("img"));

                notificationMessageItem.setSenderID(childJSONObject.getString("id_user_send"));

                notificationMessageItem.setPostID(childJSONObject.getString("id_post"));

                notificationMessageItems.add(notificationMessageItem);

            }


            recyclerView.setAdapter(notificationMessageAdapter);

            notificationMessageAdapter.notifyDataSetChanged();


            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


}
