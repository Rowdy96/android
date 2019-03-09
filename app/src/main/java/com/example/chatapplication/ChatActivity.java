package com.example.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatapplication.Models.User;
import com.example.chatapplication.Models.messages;
import com.example.chatapplication.services.ServiceBuilder;
import com.example.chatapplication.services.UserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+".my_prefs", Context.MODE_PRIVATE);
        final String token  = sharedPreferences.getString("Token","N/A");

        UserService userService = ServiceBuilder.createService(UserService.class);
        Call<ArrayList<messages>> createRequest = userService.GetMessages(token);

        createRequest.enqueue(new Callback<ArrayList<messages>>() {
            @Override
            public void onResponse(Call<ArrayList<messages>> call, Response<ArrayList<messages>> response) {

                Toast.makeText(ChatActivity.this,"this is chat", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<messages>> call, Throwable t) {

            }
        });
    }
}
