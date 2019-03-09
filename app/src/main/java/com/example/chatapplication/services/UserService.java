package com.example.chatapplication.services;

import com.example.chatapplication.Models.User;
import com.example.chatapplication.Models.messages;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @GET("user")
    Call<ArrayList<User>> GetUsers(@Header("Authorization") String authToken);

    @GET("api/chat/{userId}")
    Call<ArrayList<messages>>GetMessages(@Header("Authorization") String authToken);

    @POST("user/login")
    Call<User> AddUser(@Body User newUser);



}
