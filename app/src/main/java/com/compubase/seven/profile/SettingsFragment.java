package com.compubase.seven.profile;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.compubase.seven.API;
import com.compubase.seven.R;
import com.compubase.seven.helper.RetrofitClient;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.ui.activity.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
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
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    @BindView(R.id.userimage)
    CircleImageView userimage;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.useremailedit)
    EditText useremailedit;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.btn_save)
    Button btnSave;
    private Unbinder unbinder;
    private TinyDB tinyDB;
    private String id;

    private int GALLERY_REQUEST_CODE = 1;
    StorageReference storageReference;
    FirebaseStorage storage;

    Uri filePath;

    String imageURL;
    private String user_img;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        FirebaseApp.initializeApp(getActivity());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        tinyDB = new TinyDB(getActivity());
        tvUsername.setText(tinyDB.getString("user_name"));
        etCity.setText(tinyDB.getString("user_city"));
        etPassword.setText(tinyDB.getString("user_pass"));
        etPhone.setText(tinyDB.getString("user_phone"));
        etUserName.setText(tinyDB.getString("user_name"));
        id = tinyDB.getString("user_id");
        user_img = tinyDB.getString("user_img");

        Glide.with(getActivity()).load(user_img).placeholder(R.drawable.user).into(userimage);

        return inflate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.userimage, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userimage:
                pickFromGallery();
                break;
            case R.id.btn_save:
                editProfile();
                break;
        }
    }

    private void editProfile() {

        String user_name = etUserName.getText().toString();
        String phone = etPhone.getText().toString();
        String pass_user = etPassword.getText().toString();
        String city = etCity.getText().toString();

        if (TextUtils.isEmpty(user_name)){
            etUserName.setError("من فضلك ادخل الاسم");
        }else if (TextUtils.isEmpty(phone)){
            etPhone.setError("من فضلك ادخل رقم الجوال");
        }else if (TextUtils.isEmpty(pass_user)){
            etPassword.setError("من فضلك ادخل كلمة المرور");
        }else if (TextUtils.isEmpty(city)){
            etCity.setError("من فضلك ادخل اسم المدينة");
        }else {

            RetrofitClient.getInstant().create(API.class).edite_profile(id,city,pass_user,imageURL,phone,user_name)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            try {
                                assert response.body() != null;
                                String string = response.body().string();
                                if (string.equals("True")){

                                    Toast.makeText(getActivity(), "تم التعديل بنجاح", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getActivity(), HomeActivity.class));

                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                userimage.setImageBitmap(bitmap);

                uploadImage(filePath);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        Glide.with(Objects.requireNonNull(getContext())).
                load(user_img).placeholder(R.drawable.user).into(userimage);
//        txtName.setText(name);
    }

    private void uploadImage(Uri customfilepath) {

        if (customfilepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
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

                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                                    imageURL = uri.toString();

                                  tinyDB.putString("user_img",imageURL);


                                    Glide.with(getContext()).load(imageURL).placeholder(R.drawable.user).into(userimage);

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
}
