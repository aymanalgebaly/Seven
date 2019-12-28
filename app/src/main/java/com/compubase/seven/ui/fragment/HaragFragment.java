package com.compubase.seven.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.compubase.seven.R;
import com.compubase.seven.adapter.IteamListAdapter;
import com.compubase.seven.adapter.SalesAdapter;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.ItemList;
import com.compubase.seven.model.SalesItems;
import com.compubase.seven.ui.activity.AddPostActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HaragFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView rvitems,sallesrv;

    IteamListAdapter rvadapter;
    ArrayList<ItemList> itemLists;

    SalesAdapter salleslistAdapter;
    ArrayList<SalesItems> salesitems = new ArrayList<>();

    private SwipeRefreshLayout swipeContainer;

    ImageButton adde;

    RequestQueue requestQueue;

    ProgressBar progressBar;

    Spinner cityesSpinner,countriesSpinner;

    ArrayAdapter<String> adapter,adapter2;

    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();

    TinyDB tinyDB;

    String selectedDepartment;
    private View view;


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

        sallesrv.setHasFixedSize(false);

        sallesrv.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL,false));

        salleslistAdapter = new SalesAdapter(salesitems);

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


        countries.add("كل الدول");
        countries.add("السعودية");
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


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cities);
        adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countries);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityesSpinner.setAdapter(adapter);
        cityesSpinner.setOnItemSelectedListener(HaragFragment.this);

        adapter.notifyDataSetChanged();

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countriesSpinner.setAdapter(adapter2);
        countriesSpinner.setOnItemSelectedListener(HaragFragment.this);

        adapter2.notifyDataSetChanged();

        swiptorefresch();

        return view;
    }

    public void swiptorefresch()
    {
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

                JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj?");

                salleslistAdapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);

            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick)
    {
        String event = addButtonClick.getEvent();

        salesitems.clear();
        salleslistAdapter.notifyDataSetChanged();

        if(cityesSpinner.getSelectedItemPosition()==0)
        {

            switch (event)
            {

                case "0":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;


                case "8":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department=قسم غير مصنف");
                    selectedDepartment = "قسم غير مصنف";
                    break;

            }

        }else
        {

            switch (event)
            {

                case "0":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"عقارات");
                    selectedDepartment = "عقارات";
                    break;

                case "1":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"سيارات");
                    selectedDepartment = "سيارات";
                    break;

                case "2":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"وظائف");
                    selectedDepartment = "وظائف";
                    break;


                case "3":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"الاجهزه");
                    selectedDepartment = "الاجهزه";
                    break;


                case "4":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"اثاث");
                    selectedDepartment = "اثاث";
                    break;


                case "5":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"الخدمات");
                    selectedDepartment = "الخدمات";
                    break;


                case "6":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"مواشى وحيوانات وطيور");
                    selectedDepartment = "مواشى وحيوانات وطيور";
                    break;


                case "7":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"الاسرة المنتجة");
                    selectedDepartment = "الاسرة المنتجة";
                    break;

                case "8":
                    JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cityesSpinner.getSelectedItem().toString()+"&department="+"قسم غير مصنف");
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



    private void JSON_DATA_WEB_CALL(String URL){

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,

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

                SalesItems oursales = new SalesItems();

                if (childJSONObject.getString("Image") != null) {

                    if (childJSONObject.getString("Image").contains("~")) {
                        String replaced = childJSONObject.getString("Image").replace("~", "");
                        String finalstring = "http://alosboiya.com.sa" + replaced;
                        oursales.setSellseimage(finalstring);

                    } else {
                        oursales.setSellseimage(childJSONObject.getString("Image"));
                    }

                } else {
                    oursales.setSellseimage("images/imgposting.png");

                }

                oursales.setID(childJSONObject.getString("Id"));

                oursales.setIdMember(childJSONObject.getString("IdMember"));

                oursales.setLocation(childJSONObject.getString("City"));

                oursales.setSalesdate(childJSONObject.getString("datee_c"));

                oursales.setSalesname(childJSONObject.getString("Title"));

                oursales.setSallername(childJSONObject.getString("NameMember"));

                oursales.setDescription(childJSONObject.getString("Des"));

                oursales.setDepartment(childJSONObject.getString("Department"));

                oursales.setUrl(childJSONObject.getString("URL"));

                oursales.setPhone(childJSONObject.getString("Phone"));

                oursales.setEmail(childJSONObject.getString("Email"));

                oursales.setSubdepartment(childJSONObject.getString("SubDep"));

                if (childJSONObject.getString("Image_2") != null) {

                    if (childJSONObject.getString("Image_2").contains("~")) {
                        String replaced = childJSONObject.getString("Image_2").replace("~", "");
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
                        String finalstring = "http://alosboiya.com.sa" + replaced;
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
            sallesrv.setAdapter(salleslistAdapter);

            salleslistAdapter.notifyDataSetChanged();


            progressBar.setVisibility(View.GONE);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        salesitems.clear();


        if(position==0)
        {
            if(selectedDepartment.isEmpty())
            {
                JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj?");
            }else
            {
                JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_Department?Department="+selectedDepartment);
            }

        }else
        {
            if(selectedDepartment.isEmpty())
            {
                JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city?city="+ cities.get(position));
            }else
            {
                JSON_DATA_WEB_CALL("http://alosboiya.com.sa/wsnew.asmx/select_haraj_by_search_city_and_department?city="+ cities.get(position)+"&department="+selectedDepartment);
            }

        }

        salleslistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
