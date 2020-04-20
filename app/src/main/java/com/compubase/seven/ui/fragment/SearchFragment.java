package com.compubase.seven.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.adapter.SalesAdapter;
import com.compubase.seven.adapter.SearchAdapter;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.SalesItems;
import com.compubase.seven.model.SearchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yariksoffice.lingver.Lingver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    public static final String TAG = "ass8";

    EditText search;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    SearchAdapter salleslistAdapter;
    ArrayList<SearchResponse> salesitems = new ArrayList<>();

    RequestQueue requestQueue;
    private ArrayList<SearchResponse> searchResponseList = new ArrayList<>();
    private SharedPreferences preferences;
    private String string;


    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(getContext(), string);

        return view;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        search = getActivity().findViewById(R.id.search);
        progressBar = getActivity().findViewById(R.id.progressBar1);
        recyclerView = getActivity().findViewById(R.id.rv);

        progressBar.setVisibility(View.GONE);

//        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL,false));

        setupRecycler();

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    salesitems.clear();
//                    JSON_DATA_WEB_CALL();

                    searchData();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    return true;
                }
                return false;
            }
        });



    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    private void searchData() {


        searchResponseList.clear();
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstant().create(API.class).search(search.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        if (response.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            try {
                                assert response.body() != null;
                                List<SearchResponse> searchResponses =
                                        Arrays.asList(gson.fromJson(response.body().string(), SearchResponse[].class));

                                searchResponseList = new ArrayList<>();
                                searchResponseList.addAll(searchResponses);

                                salleslistAdapter = new SearchAdapter(searchResponseList);
                                recyclerView.setAdapter(salleslistAdapter);
                                salleslistAdapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


//    private void JSON_DATA_WEB_CALL(){
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                "http://educareua.com/seven.asmx/search_app?text_search="+search.getText().toString(),
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        showMessage("No Connection");
//
//
//                    }
//                }
//        );
//
//
//        requestQueue = Volley.newRequestQueue(getContext());
//
//        requestQueue.add(stringRequest);
//    }
//
//    public void JSON_PARSE_DATA_AFTER_WEBCALL(String Jobj) {
//
//
//        try {
//
//            JSONArray js = new JSONArray(Jobj);
//
//            for (int i = 0; i < js.length(); i++) {
//
//                JSONObject childJSONObject = js.getJSONObject(i);
//
//                AdsResponse oursales = new AdsResponse();
//
//                if (childJSONObject.getString("Image") != null) {
//
//                    if (childJSONObject.getString("Image").contains("~")) {
//                        String replaced = childJSONObject.getString("Image").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage(finalstring);
//
//                    } else {
//                        oursales.setImage(childJSONObject.getString("Image"));
//                    }
//
//                } else {
//                    oursales.setImage("images/imgposting.png");
//
//                }
//
//                oursales.setId(childJSONObject.getString("Id"));
//
//                oursales.setIdMember(childJSONObject.getString("IdMember"));
//
//                oursales.setCity(childJSONObject.getString("City"));
//
//                oursales.setDatee(childJSONObject.getString("datee"));
//
//                oursales.setTitle(childJSONObject.getString("Title"));
//
//                oursales.setNameMember(childJSONObject.getString("NameMember"));
//
//                oursales.setDes(childJSONObject.getString("Des"));
//
//                oursales.setDepartment(childJSONObject.getString("Department"));
//
//                oursales.setURL(childJSONObject.getString("URL"));
//
//                oursales.setPhone(childJSONObject.getString("Phone"));
//
//                oursales.setEmail(childJSONObject.getString("Email"));
//
//                oursales.setSubDep(childJSONObject.getString("SubDep"));
//
//                if (childJSONObject.getString("Image_2") != null) {
//
//                    if (childJSONObject.getString("Image_2").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_2").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage2(finalstring);
//
//                    } else {
//                        oursales.setImage2(childJSONObject.getString("Image_2"));
//                    }
//
//                } else {
//                    oursales.setImage2("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_3") != null) {
//
//                    if (childJSONObject.getString("Image_3").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_3").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage3(finalstring);
//
//                    } else {
//                        oursales.setImage3(childJSONObject.getString("Image_3"));
//                    }
//
//                } else {
//                    oursales.setImage3("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_4") != null) {
//
//                    if (childJSONObject.getString("Image_4").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_4").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage4(finalstring);
//
//                    } else {
//                        oursales.setImage4(childJSONObject.getString("Image_4"));
//                    }
//
//                } else {
//                    oursales.setImage4("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_5") != null) {
//
//                    if (childJSONObject.getString("Image_5").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_5").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage5(finalstring);
//
//                    } else {
//                        oursales.setImage5(childJSONObject.getString("Image_5"));
//                    }
//
//                } else {
//                    oursales.setImage5("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_6") != null) {
//
//                    if (childJSONObject.getString("Image_6").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_6").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage6(finalstring);
//
//                    } else {
//                        oursales.setImage6(childJSONObject.getString("Image_6"));
//                    }
//
//                } else {
//                    oursales.setImage6("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_7") != null) {
//
//                    if (childJSONObject.getString("Image_7").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_7").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage7(finalstring);
//
//                    } else {
//                        oursales.setImage7(childJSONObject.getString("Image_7"));
//                    }
//
//                } else {
//                    oursales.setImage7("images/imgposting.png");
//
//                }
//
//                if (childJSONObject.getString("Image_8") != null) {
//
//                    if (childJSONObject.getString("Image_8").contains("~")) {
//                        String replaced = childJSONObject.getString("Image_8").replace("~", "");
//                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
//                        oursales.setImage8(finalstring);
//
//                    } else {
//                        oursales.setImage8(childJSONObject.getString("Image_8"));
//                    }
//
//                } else {
//                    oursales.setImage8("images/imgposting.png");
//
//                }
//
//
//                salesitems.add(oursales);
//
//            }
//
//            salleslistAdapter = new SalesAdapter(salesitems);
//            recyclerView.setAdapter(salleslistAdapter);
//
//            salleslistAdapter.notifyDataSetChanged();
//
//
//            progressBar.setVisibility(View.GONE);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }


}
