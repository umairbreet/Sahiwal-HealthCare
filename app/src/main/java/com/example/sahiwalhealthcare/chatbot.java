package com.example.sahiwalhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatbot extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText message;
    ImageView send, back_btn;
    List<MessageModels> list;
    MessageAdapters adapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        recyclerView = findViewById(R.id.recyclerView);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        back_btn = findViewById(R.id.backbtnAi);

        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(chatbot.this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapters(list);
        recyclerView.setAdapter(adapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question = message.getText().toString();

                if (question.isEmpty()){
                    Toast.makeText(chatbot.this, "Please write something..", Toast.LENGTH_SHORT).show();
                }
                else {
                    addToChat(question,MessageModels.SENT_BY_ME);
                    message.setText("");

                    callAPI(question);
                }
            }
        });
    }

    private void callAPI(String question) {
        list.add(new MessageModels("Typing...",MessageModels.SENT_BY_BOT));

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model","text-davinci-003");
            jsonObject.put("prompt",question);
            jsonObject.put("max_tokens",4000);
            jsonObject.put("temperature",0);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject1.getJSONArray("choices");

                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    addResponse("Network error try again later " + response.body().toString());
                }

            }
        });

    }

    private void addResponse(String s) {

        list.remove(list.size()-1);
        addToChat(s,MessageModels.SENT_BY_BOT);

    }

    private void addToChat(String question, String sentByMe) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.add(new MessageModels(question,sentByMe));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }
}