package com.example.androidapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RedactionIdea extends AppCompatActivity {
    private EditText idea_title;
    private EditText description;
    private EditText id;
    private Button enter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.redaction_idea);

        idea_title = findViewById(R.id.redactionIdea);
        description = findViewById(R.id.redactionDescription);
        id = findViewById(R.id.redactionIdIdea);
        enter = findViewById(R.id.enterButtonRedaction);


        enter.setOnClickListener(new View.OnClickListener() { //вызываем все по нажатию кнопки на экране
            @Override
            public void onClick(View view) {

                String informationIdeaTitle = idea_title.getText().toString();
                String informationDescription = description.getText().toString();
                String informationId = id.getText().toString();

                String url2 = "http://10.0.2.2:8080/redaction";


                RequestQueue queue = Volley.newRequestQueue(RedactionIdea.this);


                StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RedactionIdea.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        // id = Integer.parseInt(response);
                        System.out.println(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RedactionIdea.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        int id = Authorization.id;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("title", informationIdeaTitle);
                        params.put("description", informationDescription);
                        params.put("ideaId", informationId);
                        params.put("userId", id + "");
                        return params;
                    }
                };
                queue.add(request);
                Intent intent = new Intent(RedactionIdea.this,Tessst.class);
                startActivity(intent);
            }
        });
    }


}
