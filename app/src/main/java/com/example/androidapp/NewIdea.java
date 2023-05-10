package com.example.androidapp;

import static com.example.androidapp.Authorization.id;

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

public class NewIdea extends AppCompatActivity {


    private EditText idea_title;
    private EditText description;
    private Button button,allIdeas,personalIdeas,favoriteIdea,redactionsIdea;
    public static String name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("idUse " + Authorization.id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_idea);
        String url = "http://10.0.2.2:8080/finduser";
        RequestQueue queue = Volley.newRequestQueue(NewIdea.this);


        idea_title= findViewById(R.id.addIdea);
        description= findViewById(R.id.description);
        button = findViewById(R.id.AuthorizationButton);
        allIdeas = findViewById(R.id.AllIdeas);
        personalIdeas = findViewById(R.id.PersonalsIdeas);
        favoriteIdea = findViewById(R.id.FavoriteIdea);
        redactionsIdea = findViewById(R.id.redactionIdeaAll);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NewIdea.this, "Data added to API", Toast.LENGTH_SHORT).show();
                name = response;
                System.out.println(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NewIdea.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", String.valueOf(Authorization.id));
                return params;
            }
        };
        queue.add(stringRequest);
        System.out.println("nameeee" + name);





        redactionsIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewIdea.this,RedactionIdea.class);// Здесь указываем переход на какую страницу идём после новой идеи
                startActivity(intent);
            }
        });


        allIdeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewIdea.this,Tessst.class);// Здесь указываем переход на какую страницу идём после новой идеи
                startActivity(intent);
            }
        });

        favoriteIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewIdea.this,FavoriteIdeas.class);
                startActivity(intent);
            }
        });


        personalIdeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(NewIdea.this,UserIdeas.class);
                startActivity(intent1);
            }
        });


        button.setOnClickListener(new View.OnClickListener() { //вызываем все по нажатию кнопки на экране
            @Override
            public void onClick(View view) {
                int id = Authorization.id;





                String informationIdeaTitle = idea_title.getText().toString();
                String informationDescription = description.getText().toString();

                String url2 = "http://10.0.2.2:8080/newidea";


                RequestQueue queue = Volley.newRequestQueue(NewIdea.this);


                StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NewIdea.this, "Data added to API", Toast.LENGTH_SHORT).show();
                       // id = Integer.parseInt(response);
                        System.out.println(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(NewIdea.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        int id = Authorization.id;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idea_title",informationIdeaTitle);
                        params.put("description",informationDescription);
                        params.put("id",id+"");
                        return params;
                    }
                };
                queue.add(request);


               Intent intent = new Intent(".NewIdea");
               startActivity(intent);

            }
            //}
        });
    }
}




