package com.compubase.seven.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.compubase.seven.model.SalesItems;
import com.compubase.seven.ui.activity.AddPostActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    private SwipeRefreshLayout swipeContainer;

    ImageButton adde;

    RequestQueue requestQueue;

    ProgressBar progressBar;

    Spinner cityesSpinner, countriesSpinner, departmentSpinner;


    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();
    ArrayList<String> ist_cities = new ArrayList<>();
    ArrayList<String> emarat_cities = new ArrayList<>();
    ArrayList<String> egy_cities = new ArrayList<>();
    ArrayList<String> sudan_cities = new ArrayList<>();
    ArrayList<String> jordan_cities = new ArrayList<>();
    ArrayList<String> gza2er_cities = new ArrayList<>();
    ArrayList<String> selectedDepartmentList = new ArrayList<>();

    TinyDB tinyDB;

    String selectedDepartment, cityName;
    private View view;
    private int cityPosition;
    private List<AdsResponse> adsResponseList2 = new ArrayList<>();


    public HaragFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_harag, container, false);

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


        countries.add("كل البلاد");
        countries.add("السعودية");
        countries.add("تركيا");
        countries.add("الأمارات");
        countries.add("مصر");
        countries.add("السودان");
        countries.add("الأردن");
        countries.add("الجزائر");

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


        final List<String> allCitiesList = new ArrayList<>();
        allCitiesList.add("كل المدن");

        SpinnerUtils.SetSpinnerAdapter(getContext(), countriesSpinner, countries, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, allCitiesList, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), departmentSpinner, selectedDepartmentList, R.layout.spinner_item_black);


        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String countryName = countries.get(position);

                switch (position) {
                    case 0:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), cityesSpinner, allCitiesList, R.layout.spinner_item_black);
//                        JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx?op=select_haraj");
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
                    selectByCityAndDepartment(cityName, selectedDepartment);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        swiptorefresch();

        return view;
    }

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

                JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj?");

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
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;


                case "8":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_Department?Department=قسم غير مصنف");
                    selectedDepartment = "قسم غير مصنف";
                    break;

            }

        } else {

            switch (event) {

                case "0":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;

                case "8":
                    JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx/wsnew.asmx/select_haraj_by_search_city_and_department?city=" + cityesSpinner.getSelectedItem().toString() + "&department=" + "قسم غير مصنف");
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
                            List<AdsResponse> adsResponseList = Arrays.asList(gson.fromJson(response.body().string(),AdsResponse[].class));


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


}
