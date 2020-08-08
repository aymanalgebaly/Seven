package com.compubase.seven.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.adapter.SalesAdapter;
import com.compubase.seven.adapter.SearchAdapter;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.SpinnerUtils;
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.NewModel;
import com.compubase.seven.model.SearchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yariksoffice.lingver.Lingver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    public static final String TAG = "ass8";

    EditText search;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    SearchAdapter salleslisttAdapter;
    ArrayList<SearchResponse> salesitems = new ArrayList<>();

    RequestQueue requestQueue;
    //    @BindView(R.id.search)
//    EditText search;
    @BindView(R.id.et_pro)
    Spinner etPro;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.et_room)
    Spinner etRoom;
    @BindView(R.id.et_floor)
    Spinner etFloor;
    @BindView(R.id.et_departWith)
    Spinner etDepartWith;
    @BindView(R.id.et_departWithExtra)
    Spinner etDepartWithExtra;
    @BindView(R.id.et_area_from)
    Spinner etAreaFrom;
    @BindView(R.id.et_area_to)
    Spinner etAreaTo;
    @BindView(R.id.et_price_from_pro)
    EditText etPriceFromPro;
    @BindView(R.id.et_price_to_pro)
    EditText etPriceToPro;
    @BindView(R.id.lin_department)
    LinearLayout linDepartment;
    @BindView(R.id.sp_mark)
    Spinner spMark;
    @BindView(R.id.et_model)
    Spinner etModel;
    @BindView(R.id.et_year)
    Spinner etYear;
    @BindView(R.id.et_auto_gear)
    Spinner etAutoGear;
    @BindView(R.id.et_kilo)
    Spinner etKilo;
    @BindView(R.id.et_otherAboutCar)
    Spinner etOtherAboutCar;
    @BindView(R.id.et_engin)
    EditText etEngin;
    @BindView(R.id.et_price_from)
    EditText etPriceFrom;
    @BindView(R.id.et_price_to)
    EditText etPriceTo;
    @BindView(R.id.lin_car)
    LinearLayout linCar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.btn_cars)
    Button btnCars;
    @BindView(R.id.btn_depart)
    Button btnDepart;

    private ArrayList<SearchResponse> searchResponseList = new ArrayList<>();
    private SharedPreferences preferences;
    private String string;
    private List<AdsResponse> adsResponseList2 = new ArrayList<>();


    private Button btnSearch, btnSearchPro;
    private Unbinder unbinder;
    ArrayList<String> propertyList = new ArrayList<>();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> floors = new ArrayList<>();
    ArrayList<String> areas = new ArrayList<>();
    ArrayList<String> other_property = new ArrayList<>();
    ArrayList<String> type_lucx = new ArrayList<>();


    ArrayList<String> modelList = new ArrayList<>();
    ArrayList<String> yearlList = new ArrayList<>();
    ArrayList<String> kilolList = new ArrayList<>();
    ArrayList<String> otherCarList = new ArrayList<>();
    ArrayList<String> autoMoveList = new ArrayList<>();


    private String prorety_item;
    private String departWith;
    private String floorItem;
    private String areaItemTo;
    private String areaItemFrom;
    private String roomItem;
    private String otherPropertyItem;

    private SalesAdapter salleslistAdapter;


    private String autoMoveItem;
    private String kiloItem;
    private String modelItem;
    private String yearItem;
    private String otherCarItem;
    private String markItem;


    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        btnSearch = view.findViewById(R.id.btn_search);
        btnSearchPro = view.findViewById(R.id.btn_search_pro);
        recyclerView = view.findViewById(R.id.rv);


        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(getContext(), string);

        setupRecycler();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (cityName == null){

//                    cityName = "";
                select_haraj_by_search_car();

//                }
            }
        });

        btnSearchPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (cityName == null){
//
//                    cityName = "";
                select_haraj_by_search_property();

//                }
            }
        });


        etPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                prorety_item = propertyList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etDepartWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departWith = type_lucx.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floorItem = floors.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etAreaFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaItemFrom = areas.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etAreaTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaItemTo = areas.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roomItem = rooms.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etDepartWithExtra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                otherPropertyItem = other_property.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etAutoGear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                autoMoveItem = autoMoveList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etKilo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kiloItem = kilolList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modelItem = modelList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearItem = yearlList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etOtherAboutCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                otherCarItem = otherCarList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                markItem = propertyList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (string.equals("ar")){

            supDep("ar");
            room("ar");
            floor("ar");
            area("ar");
            other_property("ar");
            lucx("ar");


            model("ar");
            year("ar");
            kilo("ar");
            otherCar("ar");
            autoMove("ar");
        }else if (string.equals("en")){

            supDep("en");
            room("en");
            floor("en");
            area("en");
            other_property("en");
            lucx("en");


            model("en");
            year("en");
            kilo("en");
            otherCar("en");
            autoMove("en");
        }else {

            supDep("tr");
            room("tr");
            floor("tr");
            area("tr");
            other_property("tr");
            lucx("tr");


            model("tr");
            year("tr");
            kilo("tr");
            otherCar("tr");
            autoMove("tr");
        }





        btnCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linCar.setVisibility(View.VISIBLE);
                linDepartment.setVisibility(View.GONE);
            }
        });

        btnDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linDepartment.setVisibility(View.VISIBLE);
                linCar.setVisibility(View.GONE);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_haraj_by_search_car();
            }
        });

        btnSearchPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_haraj_by_search_property();
            }
        });

        return view;
    }

    private void autoMove(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_car_SubDep_model_year_kilo_other_car_auto_move("auto_move",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        autoMoveList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etAutoGear, autoMoveList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void otherCar(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_car_SubDep_model_year_kilo_other_car_auto_move("other_car",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        otherCarList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etOtherAboutCar, otherCarList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void kilo(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_car_SubDep_model_year_kilo_other_car_auto_move("kilo",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        kilolList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etKilo, kilolList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void year(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_car_SubDep_model_year_kilo_other_car_auto_move("year",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        yearlList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etYear, yearlList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void model(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_car_SubDep_model_year_kilo_other_car_auto_move("model",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        modelList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etModel, modelList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void lucx(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("type_lucx",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        type_lucx.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etDepartWith, type_lucx, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void other_property(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("other_property",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        other_property.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etDepartWithExtra, other_property, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void area(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("area",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        areas.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etAreaTo, areas, R.layout.spinner_item_black);
                    SpinnerUtils.SetSpinnerAdapter(getContext(), etAreaFrom, areas, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void floor(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("floor",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        floors.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etFloor, floors, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void room(String ar) {

        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("room",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        rooms.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etRoom, rooms, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void supDep(String ar) {
        RetrofitClient.getInstant().create(API.class)
                .select_droblist_proparty_SubDep_room_floor_area_type_lucx_other_property("SubDep",ar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<NewModel> newModels = Arrays.asList(gson.fromJson(response.body().string(), NewModel[].class));

                    for (int i = 0; i < newModels.size(); i++) {

                        String name = newModels.get(i).getName();

                        propertyList.add(name);
                    }

                    SpinnerUtils.SetSpinnerAdapter(getContext(), etPro, propertyList, R.layout.spinner_item_black);
                    SpinnerUtils.SetSpinnerAdapter(getContext(), spMark, propertyList, R.layout.spinner_item_black);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void select_haraj_by_search_car() {


        adsResponseList2.clear();

//        String auto_gear = etAutoGear.getText().toString();
        String engin = etEngin.getText().toString();
//        String kilo = etKilo.getText().toString();
//        String model = etModel.getText().toString();
//        String aboutCar = etOtherAboutCar.getText().toString();
        String priceFrom = etPriceFrom.getText().toString();
        String priceTo = etPriceTo.getText().toString();
//        String year = etYear.getText().toString();

//        if (TextUtils.isEmpty(model)) {
//            etModel.setError("ادخل موديل السياره");
//        } else if (TextUtils.isEmpty(year)) {
//            etYear.setError("ادخل السنة");
//        } else if (TextUtils.isEmpty(kilo)) {
//            etKilo.setError("ادخل عدد الكيلومترات");
//        } else if (TextUtils.isEmpty(aboutCar)) {
//            etOtherAboutCar.setError("ادخل كماليات السياره");
//        } else if (TextUtils.isEmpty(engin)) {
//            etEngin.setError("ادخل سعه المحرك");
//        } else if (TextUtils.isEmpty(priceFrom)) {
//            etPriceFrom.setError("ادخل السعر من");
//        } else if (TextUtils.isEmpty(priceTo)) {
//            etPriceTo.setError("ادخل السعر الي");
//        } else {

//        Log.i("bhhbjhbh", selectedDepartment + "-" + cityName + "-" + car_mark + "-" + model + "-" + year + "-" + auto_gear + "-" + kilo + "-" + aboutCar + "-" + priceFrom + "-" + priceTo);


//        if (cityName == null) {
//
//            cityName = "";

        Log.i( "select_haraj",markItem+" "+modelItem+" "+yearItem+" "+autoMoveItem+" "+kiloItem+" "+otherCarItem);
        RetrofitClient.getInstant().create(API.class).select_haraj_by_search_car("سيارات", "", markItem,
                modelItem, yearItem, autoMoveItem, kiloItem, otherCarItem, priceFrom, priceTo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;

                    List<AdsResponse> adsResponses
                            = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                    adsResponseList2.addAll(adsResponses);

                    salleslistAdapter = new SalesAdapter(adsResponseList2);
                    salleslistAdapter.setDataList(adsResponseList2);
                    recyclerView.setAdapter(salleslistAdapter);
                    salleslistAdapter.notifyDataSetChanged();

                    if (adsResponseList2.isEmpty()) {

                        linCar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "لايوجد نتيجه للبحث", Toast.LENGTH_LONG).show();

//                            etAutoGear.setText("");
                        etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
                        etPriceFrom.setText("");
                        etPriceTo.setText("");
//                            etYear.setText("");


                    } else {

                        adsResponseList2.addAll(adsResponses);

                        salleslistAdapter = new SalesAdapter(adsResponseList2);
                        salleslistAdapter.setDataList(adsResponseList2);
                        recyclerView.setAdapter(salleslistAdapter);
                        salleslistAdapter.notifyDataSetChanged();

//                            etAutoGear.setText("");
                        etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
                        etPriceFrom.setText("");
                        etPriceTo.setText("");
//                            etYear.setText("");


                        linCar.setVisibility(View.GONE);

                    }


                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    linCar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                linCar.setVisibility(View.GONE);
            }
        });

    }
//    }

    private void select_haraj_by_search_property() {

        adsResponseList2.clear();

//        String areaFrom = etAreaFrom.getText().toString();
////        String departWith = etDepartWith.getText().toString();
//        String departWithExtra = etDepartWithExtra.getText().toString();
//        String floor = etFloor.getText().toString();
//        String room = etRoom.getText().toString();
//        String areaTo = etAreaTo.getText().toString();
        String priceFromPro = etPriceFromPro.getText().toString();
        String priceToPro = etPriceToPro.getText().toString();
//        String year = etYear.getText().toString();

//        if (TextUtils.isEmpty(room)) {
//            etRoom.setError("ادخل عدد الغرف");
//        } else if (TextUtils.isEmpty(floor)) {
//            etFloor.setError("ادخل الطابق");
//        }
////        else if (TextUtils.isEmpty(departWith)) {
////            etDepartWith.setError("بفرش او بدون");
////        }
//        else if (TextUtils.isEmpty(departWithExtra)) {
//            etDepartWithExtra.setError("ادخل كماليات");
//        } else if (TextUtils.isEmpty(areaFrom)) {
//            etAreaFrom.setError("المساحه من");
//        } else if (TextUtils.isEmpty(areaTo)) {
//            etAreaTo.setError("المساحه الي");
//        } else if (TextUtils.isEmpty(priceFromPro)) {
//            etPriceFromPro.setError("ادخل السعر من");
//        } else if (TextUtils.isEmpty(priceToPro)) {
//            etPriceToPro.setError("ادخل السعر الي");
//        } else {

//        if (cityName == null) {
//
//            cityName = "";

//            Log.i("hhhhhhh",selectedDepartment,"-"+cityName + "-"+ prorety_item);

        RetrofitClient.getInstant().create(API.class).select_haraj_by_search_property("عقارات",
                "", prorety_item, roomItem, floorItem, "", otherPropertyItem, departWith, priceFromPro, areaItemFrom,
                areaItemTo, priceToPro).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;

                    List<AdsResponse> adsResponses
                            = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                    adsResponseList2.addAll(adsResponses);

                    salleslistAdapter = new SalesAdapter(adsResponseList2);
                    salleslistAdapter.setDataList(adsResponseList2);
                    recyclerView.setAdapter(salleslistAdapter);
                    salleslistAdapter.notifyDataSetChanged();

                    if (adsResponseList2.isEmpty()) {

                        linDepartment.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "لايوجد نتيجه للبحث", Toast.LENGTH_LONG).show();

//                            etAutoGear.setText("");
                        etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
                        etPriceFrom.setText("");
                        etPriceTo.setText("");
//                            etYear.setText("");
                    } else {

                        adsResponseList2.addAll(adsResponses);

                        salleslistAdapter = new SalesAdapter(adsResponseList2);
                        salleslistAdapter.setDataList(adsResponseList2);
                        recyclerView.setAdapter(salleslistAdapter);
                        salleslistAdapter.notifyDataSetChanged();

//                            etAutoGear.setText("");
                        etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
                        etPriceFrom.setText("");
                        etPriceTo.setText("");
//                            etYear.setText("");


                        linDepartment.setVisibility(View.GONE);

                    }


                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    linDepartment.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                linDepartment.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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

                        if (response.isSuccessful()) {

                            progressBar.setVisibility(View.GONE);
                            try {
                                assert response.body() != null;
                                List<SearchResponse> searchResponses =
                                        Arrays.asList(gson.fromJson(response.body().string(), SearchResponse[].class));

                                searchResponseList = new ArrayList<>();
                                searchResponseList.addAll(searchResponses);

                                salleslisttAdapter = new SearchAdapter(searchResponseList);
                                recyclerView.setAdapter(salleslisttAdapter);
                                salleslisttAdapter.notifyDataSetChanged();
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
