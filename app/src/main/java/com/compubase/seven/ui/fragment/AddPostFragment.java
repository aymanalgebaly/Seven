package com.compubase.seven.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.helper.PathUtil;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.SpinnerUtils;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.ui.activity.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

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
    TextView pic;
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
    @BindView(R.id.lin_car)
    LinearLayout linCar;
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
    @BindView(R.id.sp_mark)
    Spinner spMark;
    @BindView(R.id.et_pro)
    Spinner etPro;
    private Unbinder unbinder;

    final int PICK_IMAGE_REQUEST_CAMERA = 71;

    final int PICK_IMAGE_REQUEST_GALLERY = 72;

    final int SELECT_VIDEO = 1;

    final int PICK_VIDEO = 15;
    private SharedPreferences preferences;

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

    ArrayList<String> car_mark = new ArrayList<>();
    ArrayList<String> sup_depa = new ArrayList<>();


    private String cityName = "";
    private String cityName2 = "";
    private String cityName3 = "";
    private String countryName = "";
    private String countryName2 = "";
    private String countryName3 = "";
    private String department = "";
    private TinyDB tinyDB;
    private String user_id;

    private String sup_depar;
    private ArrayList<String> depart_with = new ArrayList<>();
    private String departWith;
    private ArrayList<String> autoGear = new ArrayList<>();
    private String auto_gear;
    private String address;
    private String desc;
    private String price;
    private String phone;
    private String model;
    private String year;
    private String otherAboutCar;
    private String room;
    private String floor;
    private String departmentWithExtra;
    private String area;
    private String kilo;


    FirebaseStorage storage;
    StorageReference storageReference;

    String pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8;

    Uri filePath;

    Uri selectedVideoPath;
    private String user_img;

    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add_post, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        FirebaseApp.initializeApp(getActivity());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        tinyDB = new TinyDB(getActivity());
        user_id = tinyDB.getString("user_id");
        user_img = tinyDB.getString("user_img");
        username.setText(tinyDB.getString("user_name"));

        Glide.with(getActivity()).load(user_img).placeholder(R.drawable.user).into(profileImage);

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


        depart_with.add("مفروشه");
        depart_with.add("غير مفروشه");


        car_mark.add("تويوتا");
        car_mark.add("فورد");
        car_mark.add("شيفروليه");
        car_mark.add("نيسان");
        car_mark.add("هيونداى");
        car_mark.add("ليكسز");
        car_mark.add("جى ام سى");
        car_mark.add("شاحنات و معدات ثقيله");
        car_mark.add("مرسيدس");
        car_mark.add("هوندا");
        car_mark.add("بى ام دبليو");
        car_mark.add("قطع غيار و ملحقات");
        car_mark.add("دبابات");
        car_mark.add("كيا");
        car_mark.add("دودج");
        car_mark.add("كريسلر");
        car_mark.add("جيب");
        car_mark.add("ميتسوبيشى");
        car_mark.add("ماذدا");
        car_mark.add("لاندروفر");
        car_mark.add("ايسوزو");
        car_mark.add("كاديلاك");
        car_mark.add("بورش");
        car_mark.add("اودى");
        car_mark.add("سوزوكى");
        car_mark.add("انفينتى");
        car_mark.add("هامار");
        car_mark.add("لينكولن");
        car_mark.add("فولكس واجن");
        car_mark.add("دايهاتسو");
        car_mark.add("جيلى");
        car_mark.add("ميركورى");
        car_mark.add("فولفو");
        car_mark.add("بيجو");
        car_mark.add("بنتلى");
        car_mark.add("جاجوار");
        car_mark.add("سوبارو");
        car_mark.add("ام جى");
        car_mark.add("زد اكس اوتو");
        car_mark.add("رينو");
        car_mark.add("بيوك");
        car_mark.add("مازيراتى");
        car_mark.add("رولز رويس");
        car_mark.add("لامبورجينى");
        car_mark.add("اوبل");
        car_mark.add("سكودا");
        car_mark.add("فيرارى");
        car_mark.add("ستروين");
        car_mark.add("شيرى");
        car_mark.add("سيات");
        car_mark.add("دايو");
        car_mark.add("ساب");
        car_mark.add("فيات");
        car_mark.add("سانج يونج");
        car_mark.add("استون مارتن");
        car_mark.add("بروتون");
        car_mark.add("سيارات مصدومة");
        car_mark.add("سيارات للتنازل");
        car_mark.add("سيارات تراثية");


        sup_depa.add("شقه للبيع");
        sup_depa.add("شقه للايجار");
        sup_depa.add("فلل للبيع");
        sup_depa.add("فلل للايجار");
        sup_depa.add("عقارات مصايف للبيع");
        sup_depa.add("عقارات مصايف للبيع");
        sup_depa.add("عقار تجاري للبيع");
        sup_depa.add("عقار تجاري للايجار");
        sup_depa.add("مباني و اراضي");


        autoGear.add("مانيوال");
        autoGear.add("اوتوماتيك");


        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry, countries, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry2, countries2, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCountry3, countries3, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity, cities, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity2, cities2, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spCity3, cities3, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spDepartment, selectedDepartmentList, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etDepartWith, depart_with, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etAutoGear, autoGear, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), spMark, car_mark, R.layout.spinner_item_black);
        SpinnerUtils.SetSpinnerAdapter(getContext(), etPro, sup_depa, R.layout.spinner_item_black);


        etAutoGear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                auto_gear = autoGear.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sup_depar = sup_depa.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sup_depar = car_mark.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

                if (department.equals("عقارات")) {
                    linDepartment.setVisibility(View.VISIBLE);
                    linCar.setVisibility(View.GONE);
                } else if (department.equals("السيارات")) {
                    linDepartment.setVisibility(View.GONE);
                    linCar.setVisibility(View.VISIBLE);
                } else {
                    linDepartment.setVisibility(View.GONE);
                    linCar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tinyDB = new TinyDB(getContext());

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    showPicturDialog();
                }
            }
        });


        return inflate;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showPicturDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("قم بألختيار");
        String[] pictureDlialogItem = {"اختر صورة من المعرض",
                "التقط فيديو", "اختر فيديو", "قم بألتقاط صورة"};
        pictureDialog.setItems(pictureDlialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takeVideoFromCamera();
                        break;
                    case 2:
                        chooseVideoFromGallery();
                        break;
                    case 3:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(pictureIntent, PICK_IMAGE_REQUEST_CAMERA);
        }
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, PICK_VIDEO);
        }
    }

    @SuppressLint("IntentReset")
    public void chooseVideoFromGallery() {
        @SuppressLint("IntentReset") Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3);
        startActivityForResult(i, SELECT_VIDEO);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CAMERA || requestCode == PICK_IMAGE_REQUEST_GALLERY && resultCode ==
                RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST_CAMERA) {
                try {
                    Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    filePath = getImageUri(getContext().getApplicationContext(), bitmap);

                    if (pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ١ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٢ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٣ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٤ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٥ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٦ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار عدد ٧ صورة");
                        uploadImage(filePath);
                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && !pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                        pic.setText("تم اختيار الحد الأقصى من الصور");
                        pic.setEnabled(false);
                        uploadImage(filePath);
                    }

                } catch (Exception e) {
                    pic.setText("");
                }


            } else {
                filePath = data.getData();

                if (pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ١ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٢ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٣ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٤ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٥ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٦ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار عدد ٧ صورة");
                    uploadImage(filePath);
                } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && !pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                    pic.setText("تم اختيار الحد الأقصى من الصور");
                    pic.setEnabled(false);
                    uploadImage(filePath);
                }
            }

        } else if (requestCode == SELECT_VIDEO || requestCode == PICK_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                pic.setText("تم اختيار فيديو");
                pic.setEnabled(false);
                selectedVideoPath = data.getData();

                try {
                    String filePath = PathUtil.getPath(getContext(), selectedVideoPath);
                    MediaPlayer mp = MediaPlayer.create(getContext(), Uri.parse(filePath));
                    int duration = mp.getDuration();

                    if (duration > 46) {
                        uploadVideo(selectedVideoPath);
                        Bitmap bMap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
                        Uri ass = getImageUri(getContext().getApplicationContext(), bMap);
                        uploadThumb(ass);
                    } else {
                        showMessage("الفيديو اطول من ٣٠ ثانية");
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                pic.setText("");
                pic.setEnabled(true);
            }

        }
    }


    private void uploadImage(Uri customfilepath) {

        if (customfilepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("جارى الرفع٠٠٠");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    showMessage("تم الرفع بنجاح");

                                    if (pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic1 = uri.toString();
                                        //showMessage(pic1);
                                    } else if (!pic1.equals("images/imgposting.png") && pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic2 = uri.toString();
                                        //showMessage(pic2);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic3 = uri.toString();
                                        //showMessage(pic3);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic4 = uri.toString();
                                        //showMessage(pic4);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic5 = uri.toString();
                                        //showMessage(pic5);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic6 = uri.toString();
                                        //showMessage(pic6);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic7 = uri.toString();
                                        //showMessage(pic7);
                                    } else if (!pic1.equals("images/imgposting.png") && !pic2.equals("images/imgposting.png") && !pic3.equals("images/imgposting.png") && !pic4.equals("images/imgposting.png") && !pic5.equals("images/imgposting.png") && !pic6.equals("images/imgposting.png") && !pic7.equals("images/imgposting.png") && pic8.equals("images/imgposting.png")) {
                                        pic8 = uri.toString();
                                        //showMessage(pic8);
                                    }


                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showMessage("فشل");
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void uploadThumb(Uri customfilepath) {

        if (customfilepath != null) {

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    pic1 = uri.toString();

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }


    private void uploadVideo(Uri video) {
        if (video != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            final StorageReference ref = storageReference.child("videos/" + UUID.randomUUID().toString());
            ref.putFile(video)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    showMessage("تم الرفع بنجاح");

                                    pic2 = uri.toString();
                                    pic3 = "images/imgposting.png";
                                    pic4 = "images/imgposting.png";
                                    pic5 = "images/imgposting.png";
                                    pic6 = "images/imgposting.png";
                                    pic7 = "images/imgposting.png";
                                    pic8 = "images/imgposting.png";
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.add_post)
    public void onViewClicked() {
        if (department.equals("عقارات")) {
            addPostDepartment();
        } else if (department.equals("السيارات")) {
            addPostCar();
        } else {
            addPost();
        }
    }


    private void addPostDepartment() {

        address = etAddress.getText().toString();
        desc = etDesc.getText().toString();
        price = etPrice.getText().toString();
        phone = etPhone.getText().toString();
        room = etRoom.getText().toString();
        floor = etFloor.getText().toString();
        area = etArea.getText().toString();
        departmentWithExtra = etDepartWithExtra.getText().toString();


        if (TextUtils.isEmpty(address)) {
            etAddress.setError("ادخل العنوان");
        } else if (TextUtils.isEmpty(desc)) {
            etDesc.setError("ادخل الوصف");
        } else if (TextUtils.isEmpty(price)) {
            etPrice.setError("ادخل السعر");
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.setError("ادخل رقم الهاتف");
        } else if (TextUtils.isEmpty(room)) {
            etRoom.setError("ادخل عدد الغرف");
        } else if (TextUtils.isEmpty(floor)) {
            etFloor.setError("ادخل رقم الطابق");
        } else if (TextUtils.isEmpty(area)) {
            etArea.setError("ادخل المساحه");
        } else if (TextUtils.isEmpty(departmentWithExtra)) {
            etDepartWithExtra.setError("ادخل الكماليات");
        } else {


            Log.i("hhhhhh", department + auto_gear + model);


            RetrofitClient.getInstant().create(API.class).addPostDepartment(user_id, address,
                    desc, cityName, department, price, phone, "img", "img", "img", "img", "img", "img", "img", "img",
                    "hbh", cityName2, cityName3, "", countryName, countryName2, countryName3, "", room, floor, area, departmentWithExtra,
                    departWith, sup_depar, "m", "1", "m", "m", "1").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        String string = null;
                        try {
                            assert response.body() != null;
                            string = response.body().string();

                            Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), HomeActivity.class));

                            getActivity().finish();
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    private void addPostCar() {

        address = etAddress.getText().toString();
        desc = etDesc.getText().toString();
        price = etPrice.getText().toString();
        phone = etPhone.getText().toString();
        model = etModel.getText().toString();
        year = etYear.getText().toString();
        otherAboutCar = etOtherAboutCar.getText().toString();
        kilo = etKilo.getText().toString();


        if (TextUtils.isEmpty(address)) {
            etAddress.setError("ادخل العنوان");
        } else if (TextUtils.isEmpty(desc)) {
            etDesc.setError("ادخل الوصف");
        } else if (TextUtils.isEmpty(price)) {
            etPrice.setError("ادخل السعر");
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.setError("ادخل رقم الهاتف");
        } else if (TextUtils.isEmpty(model)) {
            etModel.setError("ادخل الموديل");
        } else if (TextUtils.isEmpty(year)) {
            etYear.setError("ادخل سنة الموديل");
        } else if (TextUtils.isEmpty(otherAboutCar)) {
            etDepartWithExtra.setError("ادخل الكماليات");
        } else if (TextUtils.isEmpty(kilo)) {
            etKilo.setError("ادخل عدد الكيلومترات");
        } else {

            RetrofitClient.getInstant().create(API.class).addPostCar(user_id, address,
                    desc, cityName, department, price, phone, "img", "img", "img", "img", "img", "img", "img",
                    "img",
                    "hbh", cityName2, cityName3, "", countryName, countryName2, countryName3, "",
                    "1", "1", "1", "m",
                    "m", sup_depar, model, year, auto_gear, otherAboutCar, kilo).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        String string = null;
                        try {
                            assert response.body() != null;
                            string = response.body().string();

                            Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), HomeActivity.class));

                            getActivity().finish();
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void addPost() {

        Log.i("ccccccc", department + auto_gear + model);


        address = etAddress.getText().toString();
        desc = etDesc.getText().toString();
        price = etPrice.getText().toString();
        phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(address)) {
            etAddress.setError("ادخل العنوان");
        } else if (TextUtils.isEmpty(desc)) {
            etDesc.setError("ادخل الوصف");
        } else if (TextUtils.isEmpty(price)) {
            etPrice.setError("ادخل السعر");
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.setError("ادخل رقم الهاتف");
        } else {

//        if (sup_depar == null){
//            sup_depar = "mm";
//        }else if (auto_gear == null){
//            auto_gear = "mm";
//        }else if (model == null){
//            model = "mm";
//        }else if (room == null){
//            room = "1";
//        }else if (floor == null){
//            floor = "1";
//        }else if (kilo == null){
//            kilo = "10";
//        }else if (otherAboutCar == null){
//            otherAboutCar = "mm";
//        }else if (year == null){
//            year = "1";
//        }else if (departmentWithExtra == null){
//            departmentWithExtra = "mm";
//        }else if (area == null){
//            area = "1";
//        }else {


            RetrofitClient.getInstant().create(API.class).addPost(user_id, address,
                    desc, cityName, department, price, phone, "img", "img", "img", "img", "img", "img", "img", "img",
                    "hbh", cityName2, cityName3, "", countryName, countryName2, countryName3, "",
                    "1", "1", "1", "m",
                    "m", "m", "m", "1", "m", "m", "1")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {

                                String string = null;
                                try {
                                    assert response.body() != null;
                                    string = response.body().string();

                                    Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(getActivity(), HomeActivity.class));

                                    getActivity().finish();
                                } catch (IOException e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

//    private void addPost() {
//
//        RetrofitClient.getInstant().create(API.class)
//                .addPost(user_id, etAddress.getText().toString(), etDesc.getText().toString(),
//                        cityName, department, etPrice.getText().toString(), etPhone.getText().toString(), "img",
//                        "", "", "", "", "", "", "", "", cityName2, cityName3, "", countryName,
//                        countryName2, countryName3, "", "", etModel.getText().toString(),
//                        etYear.getText().toString(), etAutoGear.getText().toString(), etOtherAboutCar.getText().toString(),
//                        etRoom.getText().toString(), etFloor.getText().toString(), etArea.getText().toString(),
//                        etDepartWithExtra.getText().toString(), etDepartWith.getText().toString(), etKilo.getText().toString())
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                        if (response.isSuccessful()) {
//                            try {
//                                assert response.body() != null;
//                                String string = response.body().string();
//
//
//                                Log.i("tesssssst", "onResponse: " + string);
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                    }
//                });
//
//    }

    private void showMessage(String _s) {
        Toast.makeText(getContext().getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }
}
