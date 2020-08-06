package com.compubase.seven.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.adapter.IteamListAdapter;
import com.compubase.seven.adapter.SalesAdapter;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.SpinnerUtils;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.ItemList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yariksoffice.lingver.Lingver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class HaragFragment extends Fragment {

    RecyclerView rvitems, sallesrv;

    IteamListAdapter rvadapter;
    ArrayList<ItemList> itemLists;

    SalesAdapter salleslistAdapter;
    List<AdsResponse> salesitems = new ArrayList<>();
    @BindView(R.id.et_model)
    EditText etModel;
    @BindView(R.id.et_year)
    EditText etYear;
    @BindView(R.id.et_auto_gear)
    Spinner etAutoGear;
    @BindView(R.id.et_kilo)
    EditText etKilo;
    @BindView(R.id.et_otherAboutCar)
    EditText etOtherAboutCar;
    @BindView(R.id.et_engin)
    EditText etEngin;
    @BindView(R.id.et_price_from)
    EditText etPriceFrom;
    @BindView(R.id.et_price_to)
    EditText etPriceTo;
    @BindView(R.id.lin_car)
    LinearLayout linCar;
    @BindView(R.id.sp_mark)
    Spinner spMark;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.et_room)
    EditText etRoom;
    @BindView(R.id.et_floor)
    EditText etFloor;
    @BindView(R.id.et_departWith)
    Spinner etDepartWith;
    @BindView(R.id.et_departWithExtra)
    EditText etDepartWithExtra;
    @BindView(R.id.lin_department)
    LinearLayout linDepartment;
    @BindView(R.id.et_area_from)
    EditText etAreaFrom;
    @BindView(R.id.et_area_to)
    EditText etAreaTo;
    @BindView(R.id.et_price_from_pro)
    EditText etPriceFromPro;
    @BindView(R.id.et_price_to_pro)
    EditText etPriceToPro;
    @BindView(R.id.et_pro)
    Spinner etPro;
    @BindView(R.id.btn_search_pro)
    Button btnSearchPro;

    private SwipeRefreshLayout swipeContainer;

    ImageButton adde;

    RequestQueue requestQueue;

    ProgressBar progressBar;

    Spinner cityesSpinner, countriesSpinner, departmentSpinner;

    Unbinder unbinder;

    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();
    ArrayList<String> ist_cities = new ArrayList<>();
    ArrayList<String> emarat_cities = new ArrayList<>();
    ArrayList<String> egy_cities = new ArrayList<>();
    ArrayList<String> sudan_cities = new ArrayList<>();
    ArrayList<String> jordan_cities = new ArrayList<>();
    ArrayList<String> gza2er_cities = new ArrayList<>();
    ArrayList<String> selectedDepartmentList = new ArrayList<>();
    ArrayList<String> autoGear = new ArrayList<>();
    ArrayList<String> property = new ArrayList<>();
    ArrayList<String> depart_with = new ArrayList<>();

    TinyDB tinyDB;

    String selectedDepartment, cityName;
    private View view;
    private int cityPosition;
    private List<AdsResponse> adsResponseList2 = new ArrayList<>();


    private List<String> subCategories = new ArrayList<>();
    private String car_mark;
    private String auto_gear;
    private String prorety_item;
    private String departWith;
    private SharedPreferences preferences;
    private String string;

    public HaragFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_harag, container, false);
        unbinder = ButterKnife.bind(this, view);



        preferences = getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(getContext(), string);

        selectedDepartment = "";

        tinyDB = new TinyDB(getActivity());

        progressBar = view.findViewById(R.id.progressBar1);

        sallesrv = view.findViewById(R.id.sales_list);
        departmentSpinner = view.findViewById(R.id.categories_spinner);

        sallesrv.setHasFixedSize(false);

        sallesrv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        salleslistAdapter = new SalesAdapter(salesitems);
        sallesrv.setAdapter(salleslistAdapter);


//        adde = view.findViewById(R.id.addee);
//
//        adde.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(tinyDB.getString("isLoggedIn").equals("True"))
//                {
//                    Intent intent = new Intent(getContext(), AddPostActivity.class);
//                    startActivity(intent);
//                }else
//                {
//                    showMessage("سجل الدخول اولا");
//                }
//            }
//        });


        cityesSpinner = view.findViewById(R.id.cities);
        countriesSpinner = view.findViewById(R.id.countries);


        depart_with.add("مفروشه");
        depart_with.add("غير مفروشه");

        countries.add("كل البلاد");
        countries.add("السعودية");
        countries.add("تركيا");
        countries.add("الأمارات");
        countries.add("مصر");
        countries.add("السودان");
        countries.add("الأردن");
        countries.add("الجزائر");

        autoGear.add("مانيوال");
        autoGear.add("اوتوماتيك");

        property.add("شقه للبيع");
        property.add("شقه للايجار");
        property.add("فلل للبيع");
        property.add("فلل للايجار");
        property.add("عقارات مصايف للبيع");
        property.add("عقارات مصايف للبيع");
        property.add("عقار تجاري للبيع");
        property.add("عقار تجاري للايجار");
        property.add("مباني و اراضي");

        cities.add("كل المدن");
        cities.add("الرياض");
        cities.add("مكة المكرمة");
        cities.add("الدمام");
        cities.add("جده");
        cities.add("المدينة المنورة");
        cities.add("الأحساء");
        cities.add("الطائف");
        cities.add("بريدة");
        cities.add("تبوك");
        cities.add("القطيف");
        cities.add("خميس مشيط");
        cities.add("حائل");
        cities.add("حفر الباطن");
        cities.add("الجبيل");
        cities.add("الخرج");
        cities.add("أبها");
        cities.add("نجران");
        cities.add("ينبع");
        cities.add("القنفذة");
        cities.add("جازان");
        cities.add("القصيم");
        cities.add("عسير");
        cities.add("الباحه");
        cities.add("الظهران");
        cities.add("الخبر");
        cities.add("الدوادمى");
        cities.add("الشرقية");
        cities.add("الحدود الشمالية");
        cities.add("الجوف");
        cities.add("عنيزة");

        ist_cities.add("كل المدن");
        ist_cities.add("أنقره");
        ist_cities.add("أسطنبول");
        ist_cities.add("أزمير");
        ist_cities.add("أضنه");
        ist_cities.add("اديامان");
        ist_cities.add("أنطاليا");
        ist_cities.add("اماسيا");

        emarat_cities.add("كل المدن");
        emarat_cities.add("أماره دبى");
        emarat_cities.add("أماره ابو ظبى");
        emarat_cities.add("أماره عجمان");
        emarat_cities.add("أماره راس الخيمة");
        emarat_cities.add("أماره ام القيوين");
        emarat_cities.add("أماره الشارقة");
        emarat_cities.add("أماره العين");

        egy_cities.add("كل المدن");
        egy_cities.add("الاقصر");
        egy_cities.add("اسوان");
        egy_cities.add("القاهرة");
        egy_cities.add("الجيزة");
        egy_cities.add("الأسكندرية");
        egy_cities.add("بنى سويف");
        egy_cities.add("الاسماعيلية");
        egy_cities.add("بورسعيد");
        egy_cities.add("السويس");

        sudan_cities.add("كل المدن");
        sudan_cities.add("الخرطوم");
        sudan_cities.add("الخرطوم البحرى");
        sudan_cities.add("كسلا");
        sudan_cities.add("بور سودان");
        sudan_cities.add("جوبا");
        sudan_cities.add("نيالا");
        sudan_cities.add("سوكين");

        jordan_cities.add("كل المدن");
        jordan_cities.add("عمان");
        jordan_cities.add("العقبة");
        jordan_cities.add("معان");
        jordan_cities.add("الزقاء");
        jordan_cities.add("المفرق");
        jordan_cities.add("الكرك");
        jordan_cities.add("البلقاء");

        gza2er_cities.add("كل المدن");
        gza2er_cities.add("الجزائر");
        gza2er_cities.add("هران");
        gza2er_cities.add("عنابه");
        gza2er_cities.add("قسنطينة");
        gza2er_cities.add("قالمه");
        gza2er_cities.add("أهراس");
        gza2er_cities.add("البليده");


        selectedDepartmentList.add("كل الاصناف");
        selectedDepartmentList.add("عقارات");
        selectedDepartmentList.add("السيارات");
        selectedDepartmentList.add("وظائف");
        selectedDepartmentList.add("الاجهزه");
        selectedDepartmentList.add("اثاث");
        selectedDepartmentList.add("الخدمات");
        selectedDepartmentList.add("مواشي وحيوانات وطيور");
        selectedDepartmentList.add("الاسره المنتجة");
        selectedDepartmentList.add("قسم غير مصنف");


        subCategories.add("تويوتا");
        subCategories.add("فورد");
        subCategories.add("شيفروليه");
        subCategories.add("نيسان");
        subCategories.add("هيونداى");
        subCategories.add("ليكسز");
        subCategories.add("جى ام سى");
        subCategories.add("شاحنات و معدات ثقيله");
        subCategories.add("مرسيدس");
        subCategories.add("هوندا");
        subCategories.add("بى ام دبليو");
        subCategories.add("قطع غيار و ملحقات");
        subCategories.add("دبابات");
        subCategories.add("كيا");
        subCategories.add("دودج");
        subCategories.add("كريسلر");
        subCategories.add("جيب");
        subCategories.add("ميتسوبيشى");
        subCategories.add("ماذدا");
        subCategories.add("لاندروفر");
        subCategories.add("ايسوزو");
        subCategories.add("كاديلاك");
        subCategories.add("بورش");
        subCategories.add("اودى");
        subCategories.add("سوزوكى");
        subCategories.add("انفينتى");
        subCategories.add("هامار");
        subCategories.add("لينكولن");
        subCategories.add("فولكس واجن");
        subCategories.add("دايهاتسو");
        subCategories.add("جيلى");
        subCategories.add("ميركورى");
        subCategories.add("فولفو");
        subCategories.add("بيجو");
        subCategories.add("بنتلى");
        subCategories.add("جاجوار");
        subCategories.add("سوبارو");
        subCategories.add("ام جى");
        subCategories.add("زد اكس اوتو");
        subCategories.add("رينو");
        subCategories.add("بيوك");
        subCategories.add("مازيراتى");
        subCategories.add("رولز رويس");
        subCategories.add("لامبورجينى");
        subCategories.add("اوبل");
        subCategories.add("سكودا");
        subCategories.add("فيرارى");
        subCategories.add("ستروين");
        subCategories.add("شيرى");
        subCategories.add("سيات");
        subCategories.add("دايو");
        subCategories.add("ساب");
        subCategories.add("فيات");
        subCategories.add("سانج يونج");
        subCategories.add("استون مارتن");
        subCategories.add("بروتون");
        subCategories.add("سيارات مصدومة");
        subCategories.add("سيارات للتنازل");
        subCategories.add("سيارات تراثية");


        final List<String> allCitiesList = new ArrayList<>();
        allCitiesList.add("كل المدن");

        SpinnerUtils.SetSpinnerAdapter(getContext(), countriesSpinner, countries, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spMark, subCategories, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, allCitiesList, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), departmentSpinner, selectedDepartmentList, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etAutoGear, autoGear, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etPro, property, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etDepartWith, depart_with, R.layout.spinner_item_black);


        etPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                prorety_item = property.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etDepartWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departWith = depart_with.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etAutoGear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                auto_gear = autoGear.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                car_mark = subCategories.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String countryName = countries.get(position);

                switch (position) {
                    case 0:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, allCitiesList, R.layout.spinner_item_black);
//                        JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx?op=select_haraj");
                        selectAll();
                        break;

                    case 1:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, cities, R.layout.spinner_item_black);

                        selectByCountry(countryName);

                        break;

                    case 2:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, ist_cities, R.layout.spinner_item_black);

                        selectByCountry(countryName);

                        break;

                    case 3:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, emarat_cities, R.layout.spinner_item_black);

                        selectByCountry(countryName);

                        break;

                    case 4:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, egy_cities, R.layout.spinner_item_black);

                        selectByCountry(countryName);

                        break;

                    case 5:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, sudan_cities, R.layout.spinner_item_black);

                        selectByCountry(countryName);

                        break;

                    case 6:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, jordan_cities, R.layout.spinner_item_black);
                        selectByCountry(countryName);
                        break;

                    case 7:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, gza2er_cities, R.layout.spinner_item_black);
                        selectByCountry(countryName);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cityesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cityPosition = i;

                List<String> citiesList = new ArrayList<>();

                switch (countriesSpinner.getSelectedItemPosition()) {
                    case 1:
                        citiesList = cities;
                        break;
                    case 2:
                        citiesList = ist_cities;
                        break;
                    case 3:
                        citiesList = emarat_cities;
                        break;
                    case 4:
                        citiesList = egy_cities;
                        break;
                    case 5:
                        citiesList = sudan_cities;
                        break;
                    case 6:
                        citiesList = jordan_cities;
                        break;
                    case 7:
                        citiesList = gza2er_cities;
                        break;

                }

                if (i != 0)
                    cityName = citiesList.get(i);


                if (departmentSpinner.getSelectedItemPosition() == 0) {
                    selectByCity(cityName);
                } else {
                    selectByCityAndDepartment(cityName, selectedDepartment);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                selectedDepartment = selectedDepartmentList.get(position);


                if (cityesSpinner.getSelectedItemPosition() == 0) {
                    selectByDepartment(selectedDepartment);
                } else {
                    linCar.setVisibility(View.GONE);
                    linDepartment.setVisibility(View.GONE);
                    selectByCityAndDepartment(cityName, selectedDepartment);
                }


                //                if (selectedDepartment.equals("السيارات")) {
//
//                    linCar.setVisibility(View.GONE);
//                    linDepartment.setVisibility(View.GONE);
////                    select_haraj_by_search_car(cityName, selectedDepartment);
//
//                } else if (selectedDepartment.equals("عقارات")) {
//
//                    linDepartment.setVisibility(View.GONE);
//                    linCar.setVisibility(View.GONE);
////                    select_haraj_by_search_property(cityName, selectedDepartment);
//
//
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                if (cityName == null){
//
////                    cityName = "";
//                    select_haraj_by_search_car(cityName, selectedDepartment);
//
////                }
//            }
//        });

//        btnSearchPro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                if (cityName == null){
////
////                    cityName = "";
//                    select_haraj_by_search_property(cityName, selectedDepartment);
//
////                }
//            }
//        });


        swiptorefresch();

//        selectAll();

        return view;
    }

//    private void select_haraj_by_search_property(String cityName, String selectedDepartment) {
//
//        adsResponseList2.clear();
//
//        String areaFrom = etAreaFrom.getText().toString();
////        String departWith = etDepartWith.getText().toString();
//        String departWithExtra = etDepartWithExtra.getText().toString();
//        String floor = etFloor.getText().toString();
//        String room = etRoom.getText().toString();
//        String areaTo = etAreaTo.getText().toString();
//        String priceFromPro = etPriceFromPro.getText().toString();
//        String priceToPro = etPriceToPro.getText().toString();
////        String year = etYear.getText().toString();
//
////        if (TextUtils.isEmpty(room)) {
////            etRoom.setError("ادخل عدد الغرف");
////        } else if (TextUtils.isEmpty(floor)) {
////            etFloor.setError("ادخل الطابق");
////        }
//////        else if (TextUtils.isEmpty(departWith)) {
//////            etDepartWith.setError("بفرش او بدون");
//////        }
////        else if (TextUtils.isEmpty(departWithExtra)) {
////            etDepartWithExtra.setError("ادخل كماليات");
////        } else if (TextUtils.isEmpty(areaFrom)) {
////            etAreaFrom.setError("المساحه من");
////        } else if (TextUtils.isEmpty(areaTo)) {
////            etAreaTo.setError("المساحه الي");
////        } else if (TextUtils.isEmpty(priceFromPro)) {
////            etPriceFromPro.setError("ادخل السعر من");
////        } else if (TextUtils.isEmpty(priceToPro)) {
////            etPriceToPro.setError("ادخل السعر الي");
////        } else {
//
//        if (cityName == null){
//
//            cityName = "";
//
////            Log.i("hhhhhhh",selectedDepartment,"-"+cityName + "-"+ prorety_item);
//
//            RetrofitClient.getInstant().create(API.class).select_haraj_by_search_property(selectedDepartment,
//                    cityName, prorety_item, room, floor, "", departWithExtra, departWith, priceFromPro, areaFrom,
//                    areaTo, priceToPro).enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();
//
//                    try {
//                        assert response.body() != null;
//
//                        List<AdsResponse> adsResponses
//                                = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));
//
//                        adsResponseList2.addAll(adsResponses);
//
//                        salleslistAdapter.setDataList(adsResponseList2);
//                        salleslistAdapter.notifyDataSetChanged();
//
//                        if (adsResponseList2.isEmpty()) {
//
//                            linDepartment.setVisibility(View.GONE);
//
//                            Toast.makeText(getActivity(), "لايوجد نتيجه للبحث", Toast.LENGTH_LONG).show();
//
////                            etAutoGear.setText("");
//                            etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
//                            etPriceFrom.setText("");
//                            etPriceTo.setText("");
//                            etYear.setText("");
//                        } else {
//
//                            adsResponseList2.addAll(adsResponses);
//
//                            salleslistAdapter.setDataList(adsResponseList2);
//                            salleslistAdapter.notifyDataSetChanged();
//
////                            etAutoGear.setText("");
//                            etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
//                            etPriceFrom.setText("");
//                            etPriceTo.setText("");
//                            etYear.setText("");
//
//
//                            linDepartment.setVisibility(View.GONE);
//
//                        }
//
//
//                    } catch (IOException e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        linDepartment.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    linDepartment.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }
//    }

//    private void select_haraj_by_search_car(String cityName, String selectedDepartment) {
//
//
//        adsResponseList2.clear();
//
////        String auto_gear = etAutoGear.getText().toString();
//        String engin = etEngin.getText().toString();
//        String kilo = etKilo.getText().toString();
//        String model = etModel.getText().toString();
//        String aboutCar = etOtherAboutCar.getText().toString();
//        String priceFrom = etPriceFrom.getText().toString();
//        String priceTo = etPriceTo.getText().toString();
//        String year = etYear.getText().toString();
//
////        if (TextUtils.isEmpty(model)) {
////            etModel.setError("ادخل موديل السياره");
////        } else if (TextUtils.isEmpty(year)) {
////            etYear.setError("ادخل السنة");
////        } else if (TextUtils.isEmpty(kilo)) {
////            etKilo.setError("ادخل عدد الكيلومترات");
////        } else if (TextUtils.isEmpty(aboutCar)) {
////            etOtherAboutCar.setError("ادخل كماليات السياره");
////        } else if (TextUtils.isEmpty(engin)) {
////            etEngin.setError("ادخل سعه المحرك");
////        } else if (TextUtils.isEmpty(priceFrom)) {
////            etPriceFrom.setError("ادخل السعر من");
////        } else if (TextUtils.isEmpty(priceTo)) {
////            etPriceTo.setError("ادخل السعر الي");
////        } else {
//
//        Log.i("bhhbjhbh",selectedDepartment + "-" +cityName + "-" + car_mark + "-" + model + "-" + year + "-" + auto_gear + "-" + kilo + "-" + aboutCar + "-" + priceFrom + "-" + priceTo);
//
//
//        if (cityName == null){
//
//            cityName = "";
//
//            RetrofitClient.getInstant().create(API.class).select_haraj_by_search_car(selectedDepartment, cityName,car_mark,
//                    model, year, auto_gear, kilo, aboutCar, priceFrom, priceTo).enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();
//
//                    try {
//                        assert response.body() != null;
//
//                        List<AdsResponse> adsResponses
//                                = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));
//
//                        adsResponseList2.addAll(adsResponses);
//
//                        salleslistAdapter.setDataList(adsResponseList2);
//                        salleslistAdapter.notifyDataSetChanged();
//
//                        if (adsResponseList2.isEmpty()) {
//
//                            linCar.setVisibility(View.GONE);
//
//                            Toast.makeText(getActivity(), "لايوجد نتيجه للبحث", Toast.LENGTH_LONG).show();
//
////                            etAutoGear.setText("");
//                            etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
//                            etPriceFrom.setText("");
//                            etPriceTo.setText("");
//                            etYear.setText("");
//
//
//                        } else {
//
//                            adsResponseList2.addAll(adsResponses);
//
//                            salleslistAdapter.setDataList(adsResponseList2);
//                            salleslistAdapter.notifyDataSetChanged();
//
////                            etAutoGear.setText("");
//                            etEngin.setText("");
//                            etKilo.setText("");
//                            etModel.setText("");
//                            etOtherAboutCar.setText("");
//                            etPriceFrom.setText("");
//                            etPriceTo.setText("");
//                            etYear.setText("");
//
//
//                            linCar.setVisibility(View.GONE);
//
//                        }
//
//
//                    } catch (IOException e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        linCar.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                    linCar.setVisibility(View.GONE);
//                }
//            });
//
//        }
//    }
//    }

    public void swiptorefresch() {
        // Lookup the swipe container view
        // Setup refresh listener which triggers new data loading

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                salesitems.clear();

//                JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj?");

                selectAll();

                salleslistAdapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);

            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick) {
        String event = addButtonClick.getEvent();

        salesitems.clear();
        salleslistAdapter.notifyDataSetChanged();

        if (cityesSpinner.getSelectedItemPosition() == 0) {

            switch (event) {

                case "0":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;


                case "8":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=قسم غير مصنف");
                    selectedDepartment = "قسم غير مصنف";
                    break;

            }

        } else {

            switch (event) {

                case "0":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;

                case "8":
                    JSON_DATA_WEB_CALL("http://sevenapps.net/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "قسم غير مصنف");
                    selectedDepartment = "قسم غير مصنف";
                    break;
            }

        }


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    private void JSON_DATA_WEB_CALL(String URL) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage("خطأ فى الشبكة");
                        Log.i("errror", "onErrorResponse: " + error.toString());

                    }
                }
        );


        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(String Jobj) {


        try {

            JSONArray js = new JSONArray(Jobj);

            for (int i = 0; i < js.length(); i++) {

                JSONObject childJSONObject = js.getJSONObject(i);

                AdsResponse oursales = new AdsResponse();

                if (childJSONObject.getString("Image") != null) {

                    if (childJSONObject.getString("Image").contains("~")) {
                        String replaced = childJSONObject.getString("Image").replace("~", "");
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
                        oursales.setImage(finalstring);

                    } else {
                        oursales.setImage(childJSONObject.getString("Image"));
                    }

                } else {
                    oursales.setImage("images/imgposting.png");

                }

                oursales.setId(Integer.valueOf(childJSONObject.getString("Id")));

                oursales.setIdMember(childJSONObject.getString("IdMember"));

                oursales.setCountry(childJSONObject.getString("City"));

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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
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
                        String finalstring = "http://sevenapps.net/seven.asmx" + replaced;
                        oursales.setImage8(finalstring);

                    } else {
                        oursales.setImage8(childJSONObject.getString("Image_8"));
                    }

                } else {
                    oursales.setImage8("images/imgposting.png");

                }


                salesitems.add(oursales);

            }

            //salleslistAdapter = new SalesAdapter(salesitems);

            salleslistAdapter.notifyDataSetChanged();


            progressBar.setVisibility(View.GONE);


        } catch (JSONException e) {
            e.printStackTrace();

            progressBar.setVisibility(View.GONE);
            Log.i("errrrrrrrr", "JSON_PARSE_DATA_AFTER_WEBCALL: " + e.toString());
        }
    }


    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }


    private void selectAll() {

        progressBar.setVisibility(View.VISIBLE);

        adsResponseList2.clear();

        RetrofitClient.getInstant().create(API.class).selectAll()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsResponseList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));


                            adsResponseList2.addAll(adsResponseList);

                            salleslistAdapter.setDataList(adsResponseList2);

                            progressBar.setVisibility(View.GONE);


                            Log.i("taaaaaag", "onResponse: " + adsResponseList.toString());


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("taaaaaag", "onResponsefail: " + t.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void selectByCountry(String country) {

        progressBar.setVisibility(View.VISIBLE);

        adsResponseList2.clear();

        RetrofitClient.getInstant().create(API.class).selectCountry(country)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();


                        try {
                            assert response.body() != null;
                            List<AdsResponse> adsResponseList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                            adsResponseList2.addAll(adsResponseList);


                            salleslistAdapter.setDataList(adsResponseList2);

                            progressBar.setVisibility(View.GONE);


                            Log.i("taaaaaag", "onResponse: " + adsResponseList.toString());


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("taaaaaag", "onResponsefail: " + t.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }


    private void selectByCityAndDepartment(String city, String department) {
        progressBar.setVisibility(View.VISIBLE);

        adsResponseList2.clear();

        RetrofitClient.getInstant().create(API.class).selectCityAndDepartment(city, department)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                        sallesrv.removeAllViewsInLayout();


                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsResponseList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                            adsResponseList2.addAll(adsResponseList);

                            salleslistAdapter.setDataList(adsResponseList2);

                            progressBar.setVisibility(View.GONE);


                            Log.i("taaaaaag", "onResponse: " + adsResponseList.toString());


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }


    private void selectByCity(String city) {
        progressBar.setVisibility(View.VISIBLE);

        adsResponseList2.clear();
        RetrofitClient.getInstant().create(API.class).selectByCity(city)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


//                        sallesrv.removeAllViewsInLayout();


                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsResponseList =
                                    Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));

                            adsResponseList2.addAll(adsResponseList);

                            salleslistAdapter.setDataList(adsResponseList2);

                            progressBar.setVisibility(View.GONE);


                            Log.i("taaaaaag", "onResponse: " + adsResponseList.toString());


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }


    private void selectByDepartment(String department) {

        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstant().create(API.class).selectDepartment(department)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        try {
                            assert response.body() != null;
                            assert response.body().toString() != null;
                            List<AdsResponse> adsResponseList = Arrays.asList(gson.fromJson(response.body().string(), AdsResponse[].class));


                            salesitems = adsResponseList;

                            sallesrv.setAdapter(salleslistAdapter);

                            salleslistAdapter.setDataList(adsResponseList);

                            salleslistAdapter.notifyDataSetChanged();


                            progressBar.setVisibility(View.GONE);


                            Log.i("taaaaaag", "onResponse: " + adsResponseList.toString());


                        } catch (Exception e) {
                            Log.i("taaaaaag", "onResponse: " + e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();

        selectAll();
    }
}

