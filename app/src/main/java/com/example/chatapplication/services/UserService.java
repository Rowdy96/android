package com.example.chatapplication.services;

import com.example.chatapplication.Models.User;
import com.example.chatapplication.Models.messages;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("user")
    Call<ArrayList<User>> GetUsers(@Header("Authorization") String authToken);


    @GET("chat/{userId}")
    Call<ArrayList<messages>>GetMessages(@Header("Authorization") String authToken , @Path("userId") int id);

    @POST("user/login")
    Call<User> AddUser(@Body User newUser);

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST ("chat")
    Call<messages> SendMessage(@Header("Authorization") String authToken ,@Field("toUserId") int userId,@Field("message") String message);

}
