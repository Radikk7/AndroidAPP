package com.example.androidapp;

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

public class AllIdeas extends AppCompatActivity {
    private EditText id;
    private EditText title;
    private EditText description;
    private EditText likes;
    private EditText timestamp;
    private EditText author;
    private EditText favorite;
    private Button buttonAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.allideas);
        String url2 = "http://10.0.2.2:8080/personals";

        RequestQueue queue = Volley.newRequestQueue(AllIdeas.this);


        StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AllIdeas.this, "Data added to API", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AllIdeas.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userId", id.toString());
                return params;
            }
        };
        queue.add(request);
       // Intent intent = new Intent(".NewIdea");
        //startActivity(intent);

        buttonAuth.setOnClickListener(new View.OnClickListener() { //вызываем все по нажатию кнопки на экране


            @Override
            public void onClick(View view) {
            //    queue.add(request);
            //    Intent intent = new Intent(".NewIdea");
            //    startActivity(intent);

                // requestQueue = Volley.newRequestQueue(Authorization.this);
                //requestQueue.add(stringRequest);
            }
            //}
            // ПОПРОБОВАТЬ ЭТО ВСЕ ЗАПУСТИТЬ
        });


    }
}
