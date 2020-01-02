package com.compubase.seven.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.adapter.ProfileCommentsAdapter;
import com.compubase.seven.adapter.ProfilePostsAdapter;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.SalesItems;
import com.compubase.seven.model.SallesCommentItems;
import com.compubase.seven.ui.activity.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.hoang8f.android.segmented.SegmentedGroup;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ass10";

    TextView username, userbalance, usernameedit, userphoneedit, useremailedit, usercityedit, usercountyedit;

    TextView username2, usernameedit2, userphoneedit2, useremailedit2, usercityedit2, usercountyedit2;

    ImageView userimage2;

    ImageView userimage;

    TinyDB tinyDB;

    LinearLayout notabslayout;

    RequestQueue requestQueue;

    RecyclerView commentsrecycler, postsrecycler;

    ProfileCommentsAdapter adapter1;

    ProfilePostsAdapter adapter2;

    List<SallesCommentItems> commentItems = new ArrayList<>();

    List<AdsResponse> postItems = new ArrayList<>();

    String id;

//    SegmentedGroup tabsgroup;

    Button comments, expire, all;
    private LinearLayout lin_hesaby;

    private Button btn_log_out, btn_hesaby, btn_settings;
    private Fragment fragment;
    private LinearLayout lin_settingd;
    private LinearLayout lin_buttons;
    private LinearLayout lin_rcv;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn_hesaby = view.findViewById(R.id.btn_hesaby);
        btn_settings = view.findViewById(R.id.btn_settings);

        lin_buttons = view.findViewById(R.id.lin_buttons);
        lin_hesaby = view.findViewById(R.id.lin_hesaby);
        lin_settingd = view.findViewById(R.id.lin_settings);
        lin_rcv = view.findViewById(R.id.lin_rcv);



        btn_hesaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lin_hesaby.setVisibility(View.VISIBLE);
                lin_settingd.setVisibility(View.GONE);

            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lin_settingd.setVisibility(View.VISIBLE);
                lin_hesaby.setVisibility(View.GONE);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tinyDB = new TinyDB(getContext());

        username2 = getActivity().findViewById(R.id.username2);
        usernameedit2 = getActivity().findViewById(R.id.usernameedit2);
        userphoneedit2 = getActivity().findViewById(R.id.userphoneedit2);
        useremailedit2 = getActivity().findViewById(R.id.useremailedit2);
        usercityedit2 = getActivity().findViewById(R.id.usercityedit2);
        usercountyedit2 = getActivity().findViewById(R.id.usercountryedit2);

        userimage2 = getActivity().findViewById(R.id.userimage2);


        usernameedit2.setText(tinyDB.getString("user_name"));
        userphoneedit2.setText(tinyDB.getString("user_phone"));
        useremailedit2.setText(tinyDB.getString("user_email"));
        usercityedit2.setText(tinyDB.getString("user_city"));
        usercountyedit2.setText(tinyDB.getString("user_country"));

//        userbalance2.setText(tinyDB.getString("user_balance"));
        username2.setText(tinyDB.getString("user_name"));


        if (tinyDB.getString("user_img").equals("images/imgposting.png") || tinyDB.getString("user_img").equals("")) {
            Glide.with(this).load(R.drawable.user).into(userimage2);

        } else if (tinyDB.getString("user_img").contains("~")) {
            String replaced = tinyDB.getString("user_img").replace("~", "");
            String finalstring = "http://educareua.com/seven.asmx" + replaced;
            Glide.with(this).load(finalstring).into(userimage2);

        } else {
            Glide.with(this).load(tinyDB.getString("user_img")).into(userimage2);
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tinyDB = new TinyDB(getContext());

        id = tinyDB.getString("user_id");

        notabslayout = Objects.requireNonNull(getActivity()).findViewById(R.id.notabslayout);

        noTaps();

        comments = getActivity().findViewById(R.id.button1);
        expire = getActivity().findViewById(R.id.button2);
        all = getActivity().findViewById(R.id.button3);

        commentsrecycler = getActivity().findViewById(R.id.commentsrecycler);

        commentsrecycler.setHasFixedSize(false);

        commentsrecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        JSON_DATA_WEB_CALL();

        postsrecycler = getActivity().findViewById(R.id.postsrecycler);

        postsrecycler.setHasFixedSize(false);

        postsrecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                notabslayout.setVisibility(View.GONE);
//                postsrecycler.setVisibility(View.GONE);
                lin_buttons.setVisibility(View.GONE);
//                lin_hesaby.setVisibility(View.VISIBLE);
                lin_rcv.setVisibility(View.VISIBLE);
                commentsrecycler.setVisibility(View.VISIBLE);


            }
        });

        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postItems.clear();

                JSON_DATA_WEB_CALL2("select_expire_post_for_member");

                notabslayout.setVisibility(View.GONE);
                commentsrecycler.setVisibility(View.GONE);
                lin_buttons.setVisibility(View.GONE);
                lin_hesaby.setVisibility(View.VISIBLE);
                lin_rcv.setVisibility(View.VISIBLE);
                postsrecycler.setVisibility(View.VISIBLE);



            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postItems.clear();

                JSON_DATA_WEB_CALL2("select_post_for_member");

                notabslayout.setVisibility(View.GONE);
                commentsrecycler.setVisibility(View.GONE);
                lin_buttons.setVisibility(View.GONE);
                lin_hesaby.setVisibility(View.VISIBLE);
                lin_rcv.setVisibility(View.VISIBLE);
                postsrecycler.setVisibility(View.VISIBLE);


            }
        });

    }

    private void noTaps() {

        username = Objects.requireNonNull(getActivity()).findViewById(R.id.username);
//        userbalance = getActivity().findViewById(R.id.userbalance);
        usernameedit = getActivity().findViewById(R.id.usernameedit);
        userphoneedit = getActivity().findViewById(R.id.userphoneedit);
        useremailedit = getActivity().findViewById(R.id.useremailedit);
        usercityedit = getActivity().findViewById(R.id.usercityedit);
        usercountyedit = getActivity().findViewById(R.id.usercountryedit);

        userimage = getActivity().findViewById(R.id.userimage);


        usernameedit.setText(tinyDB.getString("user_name"));
        userphoneedit.setText(tinyDB.getString("user_phone"));
        useremailedit.setText(tinyDB.getString("user_email"));
        usercityedit.setText(tinyDB.getString("user_city"));
        usercountyedit.setText(tinyDB.getString("user_country"));

//        userbalance.setText(tinyDB.getString("user_balance"));
        username.setText(tinyDB.getString("user_name"));


        if (tinyDB.getString("user_img").equals("images/imgposting.png")) {
            Glide.with(this).load(R.drawable.user).into(userimage);

        } else if (tinyDB.getString("user_img").contains("~")) {
            String replaced = tinyDB.getString("user_img").replace("~", "");
            String finalstring = "http://educareua.com/seven.asmx" + replaced;
            Glide.with(this).load(finalstring).into(userimage);

        } else {
            Glide.with(this).load(tinyDB.getString("user_img")).into(userimage);
        }

        notabslayout.setVisibility(View.VISIBLE);

    }


    // comments
    private void JSON_DATA_WEB_CALL() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://educareua.com/seven.asmx/select_comment_profile?id_member=" + id,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage("No Connection");


                    }
                }
        );


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        requestQueue.add(stringRequest);
    }
    public void JSON_PARSE_DATA_AFTER_WEBCALL(String Jobj) {


        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                SallesCommentItems oursales = new SallesCommentItems();


                oursales.setID(childJSONObject.getString("Id"));

                oursales.setScommentuser(childJSONObject.getString("name"));

                oursales.setScommentdate(childJSONObject.getString("datee"));

                oursales.setScommenttext(childJSONObject.getString("Comment"));


                commentItems.add(oursales);

            }

//            adapter1 = new ProfileCommentsAdapter(commentItems);
//            commentsrecycler.setAdapter(adapter1);

            adapter1.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void JSON_DATA_WEB_CALL2(String part) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://educareua.com/seven.asmx/" + part + "?id_member=" + id,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL2(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage("No Connection");


                    }
                }
        );


        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        requestQueue.add(stringRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL2(String Jobj) {


        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                AdsResponse oursales = new AdsResponse();

                if (childJSONObject.getString("Image") != null) {

                    if (childJSONObject.getString("Image").contains("~")) {
                        String replaced = childJSONObject.getString("Image").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage(finalstring);

                    } else {
                        oursales.setImage(childJSONObject.getString("Image"));
                    }

                } else {
                    oursales.setImage("images/imgposting.png");

                }

                oursales.setId(childJSONObject.getString("Id"));

                oursales.setIdMember(childJSONObject.getString("IdMember"));

                oursales.setCity(childJSONObject.getString("City"));

                oursales.setDatee(childJSONObject.getString("datee"));

                oursales.setTitle(childJSONObject.getString("Title"));

                oursales.setNameMember(childJSONObject.getString("NameMember"));

                oursales.setDes(childJSONObject.getString("Des"));

                oursales.setDepartment(childJSONObject.getString("Department"));

                oursales.setURL(childJSONObject.getString("URL"));

                oursales.setPhone(childJSONObject.getString("Phone"));

                oursales.setEmail(childJSONObject.getString("Email"));

                oursales.setSubDep(childJSONObject.getString("SubDep"));

                if (childJSONObject.getString("Image_2") != null) {

                    if (childJSONObject.getString("Image_2").contains("~")) {
                        String replaced = childJSONObject.getString("Image_2").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage2(finalstring);

                    } else {
                        oursales.setImage2(childJSONObject.getString("Image_2"));
                    }

                } else {
                    oursales.setImage2("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_3") != null) {

                    if (childJSONObject.getString("Image_3").contains("~")) {
                        String replaced = childJSONObject.getString("Image_3").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage3(finalstring);

                    } else {
                        oursales.setImage3(childJSONObject.getString("Image_3"));
                    }

                } else {
                    oursales.setImage3("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_4") != null) {

                    if (childJSONObject.getString("Image_4").contains("~")) {
                        String replaced = childJSONObject.getString("Image_4").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage4(finalstring);

                    } else {
                        oursales.setImage4(childJSONObject.getString("Image_4"));
                    }

                } else {
                    oursales.setImage4("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_5") != null) {

                    if (childJSONObject.getString("Image_5").contains("~")) {
                        String replaced = childJSONObject.getString("Image_5").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage5(finalstring);

                    } else {
                        oursales.setImage5(childJSONObject.getString("Image_5"));
                    }

                } else {
                    oursales.setImage5("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_6") != null) {

                    if (childJSONObject.getString("Image_6").contains("~")) {
                        String replaced = childJSONObject.getString("Image_6").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage6(finalstring);

                    } else {
                        oursales.setImage6(childJSONObject.getString("Image_6"));
                    }

                } else {
                    oursales.setImage6("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_7") != null) {

                    if (childJSONObject.getString("Image_7").contains("~")) {
                        String replaced = childJSONObject.getString("Image_7").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage7(finalstring);

                    } else {
                        oursales.setImage7(childJSONObject.getString("Image_7"));
                    }

                } else {
                    oursales.setImage7("images/imgposting.png");

                }

                if (childJSONObject.getString("Image_8") != null) {

                    if (childJSONObject.getString("Image_8").contains("~")) {
                        String replaced = childJSONObject.getString("Image_8").replace("~", "");
                        String finalstring = "http://educareua.com/seven.asmx" + replaced;
                        oursales.setImage8(finalstring);

                    } else {
                        oursales.setImage8(childJSONObject.getString("Image_8"));
                    }

                } else {
                    oursales.setImage8("images/imgposting.png");

                }


                postItems.add(oursales);

            }

//            adapter2 = new ProfilePostsAdapter(postItems);
//            postsrecycler.setAdapter(adapter2);

            adapter2.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }


}
