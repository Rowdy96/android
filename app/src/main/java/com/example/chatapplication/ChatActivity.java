package com.example.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.Models.User;
import com.example.chatapplication.Models.messages;
import com.example.chatapplication.services.ServiceBuilder;
import com.example.chatapplication.services.UserService;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    TextView userName;
    ListView messageView;
    EditText chatbox;
    Button button;
    ArrayAdapter<String> adapter;
    String chat;
    ArrayList<messages> messageList = new ArrayList<>();
    ArrayList<String> messageStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Context context = this;

        messageStringList = new ArrayList<>();
        userName = (TextView) findViewById(R.id.userName);
        messageView = (ListView) findViewById(R.id.message);
        chatbox = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.btnsend);

        Gson gson = new Gson();
        User user = gson.fromJson(getIntent().getStringExtra("userDetails"),User.class);

        final int id = user.getId();
        userName.setText(user.getName());

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+".my_prefs", Context.MODE_PRIVATE);
        final String token  = sharedPreferences.getString("Token","N/A");

        final UserService userService = ServiceBuilder.createService(UserService.class);
        Call<ArrayList<messages>> createRequest = userService.GetMessages(token,id);

        createRequest.enqueue(new Callback<ArrayList<messages>>() {
            @Override
            public void onResponse(Call<ArrayList<messages>> call, Response<ArrayList<messages>> response) {

              messageList = response.body();

              for (int i=0 ;i<messageList.size();i++){
                 messageStringList.add(messageList.get(i).getMessage());
             }
                adapter = new ArrayAdapter<String>(ChatActivity.this,android.R.layout.simple_list_item_1
                        ,messageStringList);
                messageView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<messages>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "failed!!", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat = chatbox.getText().toString();
                //UserService userService = ServiceBuilder.createService(UserService.class);
                Call<messages> sendMessage = userService.SendMessage(token,id,chat);
                sendMessage.enqueue(new Callback<messages>() {
                    @Override
                    public void onResponse(Call<messages> call, Response<messages> response) {

      //                  Toast.makeText(ChatActivity.this, chat, Toast.LENGTH_SHORT).show();

                        messageStringList.add(chat);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<messages> call, Throwable t) {
                        Toast.makeText(ChatActivity.this, "Failed to send", Toast.LENGTH_SHORT).show();
                 }
              });
            }
        });
    }
}
