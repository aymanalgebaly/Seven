package com.compubase.seven.profile;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.adapter.ProfileCommentsAdapter;
import com.compubase.seven.adapter.ProfilePostsAdapter;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.CommentsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFrament extends Fragment {


    @BindView(R.id.btn_allAds)
    Button btnAllAds;
    @BindView(R.id.btn_expiredAds)
    Button btnExpiredAds;
    @BindView(R.id.btn_comments)
    Button btnComments;
    @BindView(R.id.userimage2)
    CircleImageView userimage2;
    @BindView(R.id.username2)
    TextView username2;
    @BindView(R.id.usernameedit2)
    TextView tv_name;
    @BindView(R.id.userphoneedit2)
    TextView tv_phone;
    @BindView(R.id.useremailedit2)
    TextView tv_mail;
    @BindView(R.id.usercityedit2)
    TextView tv_city;
    @BindView(R.id.usercountryedit2)
    TextView tv_country;
    @BindView(R.id.lin_buttons)
    LinearLayout linButtons;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.li_comments)
    LinearLayout liComments;
    @BindView(R.id.rv_exirpedAds)
    RecyclerView rvExirpedAds;
    @BindView(R.id.li_expiredAds)
    LinearLayout liExpiredAds;
    @BindView(R.id.rv_allAds)
    RecyclerView rvAllAds;
    @BindView(R.id.li_allAds)
    LinearLayout liAllAds;
    private Unbinder unbinder;


    private List<AdsResponse> adsResponsesList = new ArrayList<>();
    private List<CommentsResponse> commentsResponseList = new ArrayList<>();


    private ProfileCommentsAdapter commentsAdapter;
    private ProfilePostsAdapter allPostsAdapter, expiredPostsAdapter;
    private TinyDB tinyDB;
    private String user_id;


    public MyAccountFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_my_account_frament, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        tinyDB = new TinyDB(getActivity());
        user_id = tinyDB.getString("user_id");


        setupCommentsRecycler();
        setupAllPostsRecycler();
        setupExpiredPostsRecycler();

        return inflate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @OnClick({R.id.btn_allAds, R.id.btn_expiredAds, R.id.btn_comments, R.id.userimage2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_allAds:
                allAdsView();
                break;
            case R.id.btn_expiredAds:
                expiredAdsView();
                break;
            case R.id.btn_comments:
                commentsView();
                break;
            case R.id.userimage2:
                break;
        }
    }


    private void commentsView() {
        linButtons.setVisibility(View.GONE);
        liComments.setVisibility(View.VISIBLE);
        liAllAds.setVisibility(View.GONE);
        liExpiredAds.setVisibility(View.GONE);

        getComments();


        btnComments.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.bg_clicked_btn)));
        btnAllAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
        btnExpiredAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
    }

    private void expiredAdsView() {
        linButtons.setVisibility(View.GONE);
        liComments.setVisibility(View.GONE);
        liAllAds.setVisibility(View.GONE);
        liExpiredAds.setVisibility(View.VISIBLE);

        getExpiredAds();


        btnComments.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
        btnAllAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
        btnExpiredAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.bg_clicked_btn)));
    }

    private void allAdsView() {

        linButtons.setVisibility(View.GONE);
        liComments.setVisibility(View.GONE);
        liAllAds.setVisibility(View.VISIBLE);
        liExpiredAds.setVisibility(View.GONE);

        getAlldAds();


        btnComments.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
        btnAllAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.bg_clicked_btn)));
        btnExpiredAds.setBackground((ContextCompat.getDrawable(getActivity(), R.drawable.stroke_blue)));
    }


    private void setupCommentsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        commentsAdapter = new ProfileCommentsAdapter();
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(commentsAdapter);
    }


    private void setupAllPostsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        allPostsAdapter = new ProfilePostsAdapter();
        rvAllAds.setLayoutManager(linearLayoutManager);
        rvAllAds.setAdapter(allPostsAdapter);
    }

    private void setupExpiredPostsRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        expiredPostsAdapter = new ProfilePostsAdapter();
        rvExirpedAds.setLayoutManager(linearLayoutManager);
        rvExirpedAds.setAdapter(expiredPostsAdapter);
    }


    private void getComments() {

        commentsResponseList.clear();

        RetrofitClient.getInstant().create(API.class)
                .getComments(user_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            List<CommentsResponse> commentsList = Arrays.asList(gson.fromJson(response.body().string(), CommentsResponse[].class));

                            commentsResponseList.addAll(commentsList);

                            commentsAdapter.setdataList(commentsResponseList);

                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


    private void getExpiredAds() {

        adsResponsesList.clear();

        RetrofitClient.getInstant().create(API.class).getExpiredAds(user_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                            adsResponsesList.addAll(adsList);


                            expiredPostsAdapter.setDataList(adsResponsesList);


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

    }


    private void getAlldAds() {

        adsResponsesList.clear();

        RetrofitClient.getInstant().create(API.class).getAllAds(user_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                            adsResponsesList.addAll(adsList);

                            allPostsAdapter.setDataList(adsResponsesList);

                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

    }


}
