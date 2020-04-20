package com.compubase.seven.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.helper.RequestHandler;
import com.compubase.seven.helper.SpinnerUtils;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.ui.fragment.ConfirmFragment;
import com.yariksoffice.lingver.Lingver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Spinner countrey ,  elamdenaa;
    EditText regname , regemail ,regpass,regpass2 ,regphone;
    Button register;
    String GET_JSON_DATA_HTTP_URL;
    TinyDB tinyDB ;
    private ProgressBar progressBar;

    Spinner cityesSpinner, countriesSpinner, departmentSpinner;


    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();
    ArrayList<String> ist_cities = new ArrayList<>();
    ArrayList<String> emarat_cities = new ArrayList<>();
    ArrayList<String> egy_cities = new ArrayList<>();
    ArrayList<String> sudan_cities = new ArrayList<>();
    ArrayList<String> jordan_cities = new ArrayList<>();
    ArrayList<String> gza2er_cities = new ArrayList<>();
    private int cityPosition;
    private String cityName;
    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        preferences = getSharedPreferences("lan", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        Lingver.getInstance().setLocale(RegisterActivity.this, string);


        tinyDB = new TinyDB(getApplicationContext());

        regname =findViewById(R.id.reg_name);
        regemail =findViewById(R.id.reg_email);
        regpass =findViewById(R.id.reg_pass);
        regpass2 =findViewById(R.id.reg_pass2);
        regphone =findViewById(R.id.reg_phone);

        progressBar = findViewById(R.id.progress);

        cityesSpinner = findViewById(R.id.cities);
        countriesSpinner = findViewById(R.id.countries);

        countries.add("البلد");
        countries.add("السعودية");
        countries.add("تركيا");
        countries.add("الأمارات");
        countries.add("مصر");
        countries.add("السودان");
        countries.add("الأردن");
        countries.add("الجزائر");

        cities.add("المدينة");
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

        ist_cities.add("المدينة");
        ist_cities.add("أنقره");
        ist_cities.add("أسطنبول");
        ist_cities.add("أزمير");
        ist_cities.add("أضنه");
        ist_cities.add("اديامان");
        ist_cities.add("أنطاليا");
        ist_cities.add("اماسيا");

        emarat_cities.add("المدينة");
        emarat_cities.add("أماره دبى");
        emarat_cities.add("أماره ابو ظبى");
        emarat_cities.add("أماره عجمان");
        emarat_cities.add("أماره راس الخيمة");
        emarat_cities.add("أماره ام القيوين");
        emarat_cities.add("أماره الشارقة");
        emarat_cities.add("أماره العين");

        egy_cities.add("المدينة");
        egy_cities.add("الاقصر");
        egy_cities.add("اسوان");
        egy_cities.add("القاهرة");
        egy_cities.add("الجيزة");
        egy_cities.add("الأسكندرية");
        egy_cities.add("بنى سويف");
        egy_cities.add("الاسماعيلية");
        egy_cities.add("بورسعيد");
        egy_cities.add("السويس");

        sudan_cities.add("المدينة");
        sudan_cities.add("الخرطوم");
        sudan_cities.add("الخرطوم البحرى");
        sudan_cities.add("كسلا");
        sudan_cities.add("بور سودان");
        sudan_cities.add("جوبا");
        sudan_cities.add("نيالا");
        sudan_cities.add("سوكين");

        jordan_cities.add("المدينة");
        jordan_cities.add("عمان");
        jordan_cities.add("العقبة");
        jordan_cities.add("معان");
        jordan_cities.add("الزقاء");
        jordan_cities.add("المفرق");
        jordan_cities.add("الكرك");
        jordan_cities.add("البلقاء");

        gza2er_cities.add("المدينة");
        gza2er_cities.add("الجزائر");
        gza2er_cities.add("هران");
        gza2er_cities.add("عنابه");
        gza2er_cities.add("قسنطينة");
        gza2er_cities.add("قالمه");
        gza2er_cities.add("أهراس");
        gza2er_cities.add("البليده");
        


        final List<String> allCitiesList = new ArrayList<>();
        allCitiesList.add("المدينة");

        SpinnerUtils.SetSpinnerAdapter(this, countriesSpinner, countries, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(this, cityesSpinner, allCitiesList, R.layout.spinner_item_black);


        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String countryName = countries.get(position);

                switch (position) {
                    case 0:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this, cityesSpinner,
                                allCitiesList, R.layout.spinner_item_black);
//                        JSON_DATA_WEB_CALL("http://educareua.com/seven.asmx?op=select_haraj");
                        break;

                    case 1:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this, cityesSpinner,
                                cities, R.layout.spinner_item_black);
                        
                        break;

                    case 2:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, ist_cities, R.layout.spinner_item_black);
                        
                        break;

                    case 3:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, emarat_cities, R.layout.spinner_item_black);
                        
                        break;

                    case 4:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, egy_cities, R.layout.spinner_item_black);
                        
                        break;

                    case 5:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, sudan_cities, R.layout.spinner_item_black);
                        
                        break;

                    case 6:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, jordan_cities, R.layout.spinner_item_black);
                        break;

                    case 7:
                        SpinnerUtils.SetSpinnerAdapter(RegisterActivity.this,
                                cityesSpinner, gza2er_cities, R.layout.spinner_item_black);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        register = findViewById(R.id.registration_user);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(regphone.getText().toString().startsWith("05") && regphone.getText().toString().length()==10)
                {
                    //volleyConnection();
                    volleyConnection2("112267");
                }else
                {
                    showMessage("الرقم خطأ");
                }

            }
        });
    }


//    public void volleyConnection()
//    {
//        GET_JSON_DATA_HTTP_URL = "educareua.com/seven.asmx/register?";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if(response.contains("sent code")){
//
//                            final FragmentManager fm = getFragmentManager();
//                            ConfirmFragment confirmFragment = new ConfirmFragment();
//
//                            confirmFragment.show(fm,"TV_tag");
//
//
//                        }else {
//                            showMessage(response);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                //showMessage(error.toString());
//                register.setEnabled(false);
//
//                final FragmentManager fm = getFragmentManager();
//                ConfirmFragment confirmFragment = new ConfirmFragment();
//
//                confirmFragment.show(fm,"TV_tag");
//
//            }
//        }) {
//
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("name", regname.getText().toString());
//                params.put("email", regemail.getText().toString());
//                params.put("password", regpass.getText().toString());
//                params.put("phone", regphone.getText().toString());
//                params.put("countryy", countrey.getSelectedItem().toString());
//                params.put("cityyyyyyyyyyyyyyyy", elamdenaa.getSelectedItem().toString());
//                return params;
//            }
//
//        };
//
//        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }


    public void volleyConnection2(final String code)


    {
        if(code.equals("nosms"))
        {
            GET_JSON_DATA_HTTP_URL = "http://sevenapps.net/seven.asmx/register_nosendsms?";
            progressBar.setVisibility(View.VISIBLE);

        }else
        {
            GET_JSON_DATA_HTTP_URL = "http://sevenapps.net/seven.asmx/register_save?";
            progressBar.setVisibility(View.VISIBLE);

        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        try {

                            if(code.equals("nosms"))
                            {
                                showMessage("تم التسجيل و سيتم تنشيط الحساب قريباً");
                            }else
                            {
                                showMessage("تم التسجيل بنجاح");

                                tinyDB.putString("isLoggedIn","True");

                                JSONArray js = new JSONArray(response);

                                JSONObject userdate = js.getJSONObject(0);

                                String user_id = String.valueOf(userdate.get("Id")) ;
                                String user_name = (String) userdate.get("Name");
                                String user_email = (String) userdate.get("Email");
                                String user_pass = (String) userdate.get("Password");
                                String user_phone= (String) userdate.get("Phone");
                                String user_country = (String) userdate.get("Country");
                                String user_city = (String) userdate.get("City");
                                String user_url = (String) userdate.get("URL");
                                String user_img = (String) userdate.get("ImageProfile");
                                String user_balance = (String) userdate.get("Balance");

                                tinyDB.putString("user_id",user_id);
                                tinyDB.putString("user_name",user_name);
                                tinyDB.putString("user_email",user_email);
                                tinyDB.putString("user_pass",user_pass);
                                tinyDB.putString("user_phone",user_phone);
                                tinyDB.putString("user_country",user_country);
                                tinyDB.putString("user_city",user_city);
                                tinyDB.putString("user_url",user_url);
                                tinyDB.putString("user_img","images/imgposting.png");
                                tinyDB.putString("user_balance",user_balance);

                                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                finish();
                            }

                            onBackPressed();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.toString());
                progressBar.setVisibility(View.GONE);

            }
        }) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("name", regname.getText().toString());
                params.put("cityyyyyyyyyyyyyyyy", cityesSpinner.getSelectedItem().toString());
                params.put("countryy", countriesSpinner.getSelectedItem().toString());
                params.put("email", regemail.getText().toString());
                params.put("password", regpass.getText().toString());
                params.put("phone", regphone.getText().toString());
                params.put("code",code);
                return params;
            }

        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick)
    {

        String name = addButtonClick.getEvent();

        volleyConnection2(name);

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



    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }
}
