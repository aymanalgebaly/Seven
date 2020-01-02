package com.compubase.seven.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.SpinnerUtils;
import com.google.android.gms.common.api.Api;

import java.io.IOException;
import java.util.ArrayList;
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

public class AddPostFragment extends Fragment {


    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.sp_department)
    Spinner spDepartment;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_addImgs)
    TextView tvAddImgs;
    @BindView(R.id.add_desc)
    EditText etDesc;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.sp_country)
    Spinner spCountry;
    @BindView(R.id.sp_city2)
    Spinner spCity2;
    @BindView(R.id.sp_country2)
    Spinner spCountry2;
    @BindView(R.id.sp_city3)
    Spinner spCity3;
    @BindView(R.id.sp_country3)
    Spinner spCountry3;
    @BindView(R.id.add_post)
    Button addPost;
    private Unbinder unbinder;


    ArrayList<String> cities = new ArrayList<>();
    ArrayList<String> countries = new ArrayList<>();
    ArrayList<String> ist_cities = new ArrayList<>();
    ArrayList<String> emarat_cities = new ArrayList<>();
    ArrayList<String> egy_cities = new ArrayList<>();
    ArrayList<String> sudan_cities = new ArrayList<>();
    ArrayList<String> jordan_cities = new ArrayList<>();
    ArrayList<String> gza2er_cities = new ArrayList<>();

    ArrayList<String> cities2 = new ArrayList<>();
    ArrayList<String> countries2 = new ArrayList<>();
    ArrayList<String> ist_cities2 = new ArrayList<>();
    ArrayList<String> emarat_cities2 = new ArrayList<>();
    ArrayList<String> egy_cities2 = new ArrayList<>();
    ArrayList<String> sudan_cities2 = new ArrayList<>();
    ArrayList<String> jordan_cities2 = new ArrayList<>();
    ArrayList<String> gza2er_cities2 = new ArrayList<>();

    ArrayList<String> cities3 = new ArrayList<>();
    ArrayList<String> countries3 = new ArrayList<>();
    ArrayList<String> ist_cities3 = new ArrayList<>();
    ArrayList<String> emarat_cities3 = new ArrayList<>();
    ArrayList<String> egy_cities3 = new ArrayList<>();
    ArrayList<String> sudan_cities3 = new ArrayList<>();
    ArrayList<String> jordan_cities3 = new ArrayList<>();
    ArrayList<String> gza2er_cities3 = new ArrayList<>();
    private List<String> selectedDepartmentList = new ArrayList<>();


    private String cityName = "";
    private String cityName2 = "";
    private String cityName3 = "";
    private String countryName = "";
    private String countryName2 = "";
    private String countryName3 = "";
    private String department = "";


    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add_post, container, false);
        unbinder = ButterKnife.bind(this, inflate);


        countries2.add("البلد (اختياري) ");
        countries2.add("السعودية");
        countries2.add("تركيا");
        countries2.add("الأمارات");
        countries2.add("مصر");
        countries2.add("السودان");
        countries2.add("الأردن");
        countries2.add("الجزائر");


        countries3.add("البلد (اختياري) ");
        countries3.add("السعودية");
        countries3.add("تركيا");
        countries3.add("الأمارات");
        countries3.add("مصر");
        countries3.add("السودان");
        countries3.add("الأردن");
        countries3.add("الجزائر");


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


        cities2.add("المدينة (اختياري) ");
        cities2.add("الرياض");
        cities2.add("مكة المكرمة");
        cities2.add("الدمام");
        cities2.add("جده");
        cities2.add("المدينة المنورة");
        cities2.add("الأحساء");
        cities2.add("الطائف");
        cities2.add("بريدة");
        cities2.add("تبوك");
        cities2.add("القطيف");
        cities2.add("خميس مشيط");
        cities2.add("حائل");
        cities2.add("حفر الباطن");
        cities2.add("الجبيل");
        cities2.add("الخرج");
        cities2.add("أبها");
        cities2.add("نجران");
        cities2.add("ينبع");
        cities2.add("القنفذة");
        cities2.add("جازان");
        cities2.add("القصيم");
        cities2.add("عسير");
        cities2.add("الباحه");
        cities2.add("الظهران");
        cities2.add("الخبر");
        cities2.add("الدوادمى");
        cities2.add("الشرقية");
        cities2.add("الحدود الشمالية");
        cities2.add("الجوف");
        cities2.add("عنيزة");

        ist_cities2.add("المدينة");
        ist_cities2.add("أنقره");
        ist_cities2.add("أسطنبول");
        ist_cities2.add("أزمير");
        ist_cities2.add("أضنه");
        ist_cities2.add("اديامان");
        ist_cities2.add("أنطاليا");
        ist_cities2.add("اماسيا");

        emarat_cities2.add("المدينة");
        emarat_cities2.add("أماره دبى");
        emarat_cities2.add("أماره ابو ظبى");
        emarat_cities2.add("أماره عجمان");
        emarat_cities2.add("أماره راس الخيمة");
        emarat_cities2.add("أماره ام القيوين");
        emarat_cities2.add("أماره الشارقة");
        emarat_cities2.add("أماره العين");

        egy_cities2.add("المدينة");
        egy_cities2.add("الاقصر");
        egy_cities2.add("اسوان");
        egy_cities2.add("القاهرة");
        egy_cities2.add("الجيزة");
        egy_cities2.add("الأسكندرية");
        egy_cities2.add("بنى سويف");
        egy_cities2.add("الا  سماعيلية");
        egy_cities2.add("بورسعيد");
        egy_cities2.add("السويس");

        sudan_cities2.add("المدينة");
        sudan_cities2.add("الخرطوم");
        sudan_cities2.add("الخرطوم البحرى");
        sudan_cities2.add("كسلا");
        sudan_cities2.add("بور سودان");
        sudan_cities2.add("جوبا");
        sudan_cities2.add("نيالا");
        sudan_cities2.add("سوكين");

        jordan_cities2.add("المدينة");
        jordan_cities2.add("عمان");
        jordan_cities2.add("العقبة");
        jordan_cities2.add("معان");
        jordan_cities2.add("الزقاء");
        jordan_cities2.add("المفرق");
        jordan_cities2.add("الكرك");
        jordan_cities2.add("البلقاء");

        gza2er_cities2.add("المدينة");
        gza2er_cities2.add("الجزائر");
        gza2er_cities2.add("هران");
        gza2er_cities2.add("عنابه");
        gza2er_cities2.add("قسنطينة");
        gza2er_cities2.add("قالمه");
        gza2er_cities2.add("أهراس");
        gza2er_cities2.add("البليده");


        cities3.add("المدينة (اختياري) ");
        cities3.add("الرياض");
        cities3.add("مكة المكرمة");
        cities3.add("الدمام");
        cities3.add("جده");
        cities3.add("المدينة المنورة");
        cities3.add("الأحساء");
        cities3.add("الطائف");
        cities3.add("بريدة");
        cities3.add("تبوك");
        cities3.add("القطيف");
        cities3.add("خميس مشيط");
        cities3.add("حائل");
        cities3.add("حفر الباطن");
        cities3.add("الجبيل");
        cities3.add("الخرج");
        cities3.add("أبها");
        cities3.add("نجران");
        cities3.add("ينبع");
        cities3.add("القنفذة");
        cities3.add("جازان");
        cities3.add("القصيم");
        cities3.add("عسير");
        cities3.add("الباحه");
        cities3.add("الظهران");
        cities3.add("الخبر");
        cities3.add("الدوادمى");
        cities3.add("الشرقية");
        cities3.add("الحدود الشمالية");
        cities3.add("الجوف");
        cities3.add("عنيزة");

        ist_cities3.add("المدينة");
        ist_cities3.add("أنقره");
        ist_cities3.add("أسطنبول");
        ist_cities3.add("أزمير");
        ist_cities3.add("أضنه");
        ist_cities3.add("اديامان");
        ist_cities3.add("أنطاليا");
        ist_cities3.add("اماسيا");

        emarat_cities3.add("المدينة");
        emarat_cities3.add("أماره دبى");
        emarat_cities3.add("أماره ابو ظبى");
        emarat_cities3.add("أماره عجمان");
        emarat_cities3.add("أماره راس الخيمة");
        emarat_cities3.add("أماره ام القيوين");
        emarat_cities3.add("أماره الشارقة");
        emarat_cities3.add("أماره العين");

        egy_cities3.add("المدينة");
        egy_cities3.add("الاقصر");
        egy_cities3.add("اسوان");
        egy_cities3.add("القاهرة");
        egy_cities3.add("الجيزة");
        egy_cities3.add("الأسكندرية");
        egy_cities3.add("بنى سويف");
        egy_cities3.add("الاسماعيلية");
        egy_cities3.add("بورسعيد");
        egy_cities3.add("السويس");

        sudan_cities3.add("المدينة");
        sudan_cities3.add("الخرطوم");
        sudan_cities3.add("الخرطوم البحرى");
        sudan_cities3.add("كسلا");
        sudan_cities3.add("بور سودان");
        sudan_cities3.add("جوبا");
        sudan_cities3.add("نيالا");
        sudan_cities3.add("سوكين");

        jordan_cities3.add("المدينة");
        jordan_cities3.add("عمان");
        jordan_cities3.add("العقبة");
        jordan_cities3.add("معان");
        jordan_cities3.add("الزقاء");
        jordan_cities3.add("المفرق");
        jordan_cities3.add("الكرك");
        jordan_cities3.add("البلقاء");

        gza2er_cities3.add("المدينة");
        gza2er_cities3.add("الجزائر");
        gza2er_cities3.add("هران");
        gza2er_cities3.add("عنابه");
        gza2er_cities3.add("قسنطينة");
        gza2er_cities3.add("قالمه");
        gza2er_cities3.add("أهراس");
        gza2er_cities3.add("البليده");


        selectedDepartmentList.add("القسم");
        selectedDepartmentList.add("عقارات");
        selectedDepartmentList.add("السيارات");
        selectedDepartmentList.add("وظائف");
        selectedDepartmentList.add("الاجهزه");
        selectedDepartmentList.add("اثاث");
        selectedDepartmentList.add("الخدمات");
        selectedDepartmentList.add("مواشي وحيوانات وطيور");
        selectedDepartmentList.add("الاسره المنتجة");
        selectedDepartmentList.add("قسم غير مصنف");


        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry, countries, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry2, countries2, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry3, countries3, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, cities, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, cities2, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, cities3, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spDepartment, selectedDepartmentList, R.layout.spinner_item_black);


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                countryName = countries.get(position);

                switch (position) {
                    case 0:
                        List<String> stringList = new ArrayList<>();
                        stringList.add("المدينة");
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, stringList, R.layout.spinner_item_black);
                        break;

                    case 1:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, cities, R.layout.spinner_item_black);

                        break;

                    case 2:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, ist_cities, R.layout.spinner_item_black);
                        break;

                    case 3:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, emarat_cities, R.layout.spinner_item_black);

                        break;

                    case 4:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, egy_cities, R.layout.spinner_item_black);

                        break;

                    case 5:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, sudan_cities, R.layout.spinner_item_black);

                        break;

                    case 6:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, jordan_cities, R.layout.spinner_item_black);
                        break;

                    case 7:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, gza2er_cities, R.layout.spinner_item_black);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<String> citiesList = new ArrayList<>();

                switch (spCountry.getSelectedItemPosition()) {
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

        spCountry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                countryName2 = countries2.get(position);

                switch (position) {
                    case 0:
                        List<String> stringList = new ArrayList<>();
                        stringList.add("المدينة");
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, stringList, R.layout.spinner_item_black);
                        break;

                    case 1:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, cities2, R.layout.spinner_item_black);

                        break;

                    case 2:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, ist_cities2, R.layout.spinner_item_black);
                        break;

                    case 3:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, emarat_cities2, R.layout.spinner_item_black);

                        break;

                    case 4:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, egy_cities2, R.layout.spinner_item_black);

                        break;

                    case 5:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, sudan_cities2, R.layout.spinner_item_black);

                        break;

                    case 6:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, jordan_cities2, R.layout.spinner_item_black);
                        break;

                    case 7:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, gza2er_cities2, R.layout.spinner_item_black);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<String> citiesList = new ArrayList<>();

                switch (spCountry2.getSelectedItemPosition()) {
                    case 1:
                        citiesList = cities2;
                        break;
                    case 2:
                        citiesList = ist_cities2;
                        break;
                    case 3:
                        citiesList = emarat_cities2;
                        break;
                    case 4:
                        citiesList = egy_cities2;
                        break;
                    case 5:
                        citiesList = sudan_cities2;
                        break;
                    case 6:
                        citiesList = jordan_cities2;
                        break;
                    case 7:
                        citiesList = gza2er_cities2;
                        break;

                }

                if (i != 0)
                    cityName2 = citiesList.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCountry3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                countryName3 = countries3.get(position);

                switch (position) {
                    case 0:
                        List<String> stringList = new ArrayList<>();
                        stringList.add("المدينة");
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, stringList, R.layout.spinner_item_black);
                        break;

                    case 1:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, cities3, R.layout.spinner_item_black);

                        break;

                    case 2:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, ist_cities3, R.layout.spinner_item_black);
                        break;

                    case 3:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, emarat_cities3, R.layout.spinner_item_black);

                        break;

                    case 4:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, egy_cities3, R.layout.spinner_item_black);

                        break;

                    case 5:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, sudan_cities3, R.layout.spinner_item_black);

                        break;

                    case 6:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, jordan_cities3, R.layout.spinner_item_black);
                        break;

                    case 7:
                        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, gza2er_cities3, R.layout.spinner_item_black);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<String> citiesList = new ArrayList<>();

                switch (spCountry3.getSelectedItemPosition()) {
                    case 1:
                        citiesList = cities3;
                        break;
                    case 2:
                        citiesList = ist_cities3;
                        break;
                    case 3:
                        citiesList = emarat_cities3;
                        break;
                    case 4:
                        citiesList = egy_cities3;
                        break;
                    case 5:
                        citiesList = sudan_cities3;
                        break;
                    case 6:
                        citiesList = jordan_cities3;
                        break;
                    case 7:
                        citiesList = gza2er_cities3;
                        break;

                }

                if (i != 0)
                    cityName3 = citiesList.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                department = selectedDepartmentList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return inflate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.add_post)
    public void onViewClicked() {
        addPost();
    }


    private void addPost(){

        RetrofitClient.getInstant().create(API.class)
                .addPost("13" , etAddress.getText().toString(),etDesc.getText().toString(),
                        cityName,department,etPrice.getText().toString(),etPhone.getText().toString(),"img",
                        "","","","","","","","",cityName2,cityName3,"",countryName,
                        countryName2,countryName3,"", "")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            try {
                                assert response.body() != null;
                                String string = response.body().string();


                                Log.i("tesssssst", "onResponse: " + string);

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
}
