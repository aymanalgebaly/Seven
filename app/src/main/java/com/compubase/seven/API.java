package com.compubase.seven;

import com.compubase.seven.model.AdsResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("select_haraj")
    Call<ResponseBody> selectAll();


    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("select_comment_profile")
    Call<ResponseBody> getComments(@Field("id_member") String id_member);


    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("select_expire_post_for_member")
    Call<ResponseBody> getExpiredAds(@Field("id_member") String id_member);

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("select_post_for_member")
    Call<ResponseBody> getAllAds(@Field("id_member") String id_member);


    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("add_post")
    Call<ResponseBody> addPost(@Field("id_member") String id_member,
                               @Field("title") String title,
                               @Field("des") String des,
                               @Field("ciiiiiiiiiiiiiiiiiiiiiiity") String ciiiiiiiiiiiiiiiiiiiiiiity,
                               @Field("category") String department,
                               @Field("price") String price,
                               @Field("tel") String tel,
                               @Field("x") String x,
                               @Field("x_2") String x2,
                               @Field("x_3") String x3,
                               @Field("x_4") String x4,
                               @Field("x_5") String x5,
                               @Field("x_6") String x6,
                               @Field("x_7") String x7,
                               @Field("x_8") String x8,
                               @Field("device") String device,
                               @Field("city_2") String city2,
                               @Field("city_3") String city3,
                               @Field("city_4") String city4,
                               @Field("counttttry") String counttttry,
                               @Field("country_2") String country_2,
                               @Field("country_3") String country_3,
                               @Field("country_4") String country_4,
                               @Field("sub") String sub);


    @FormUrlEncoded
    @POST("select_haraj_by_search_country")
    Call<ResponseBody> selectCountry(
            @Field("country") String country
    );

    @FormUrlEncoded
    @POST("select_haraj_by_search_city")
    Call<ResponseBody> selectByCity(
            @Field("city") String city
    );

    @FormUrlEncoded
    @POST("select_haraj_by_search_city_and_department")
    Call<ResponseBody> selectCityAndDepartment(
            @Field("city") String city,
            @Field("department") String department
    );

    @FormUrlEncoded
    @POST("select_haraj_by_Department")
    Call<ResponseBody> selectDepartment(
            @Field("Department") String Department
    );

    @FormUrlEncoded
    @POST("search_app")
    Call<ResponseBody> search(
            @Field("text_search") String text_search
    );

    @FormUrlEncoded
    @POST("edite_profile")
    Call<ResponseBody> edite_profile(
            @Field("id_member") String id_member,
            @Field("city") String city,
            @Field("password") String password,
            @Field("image") String image,
            @Field("phone") String phone,
            @Field("name") String name
    );
}
