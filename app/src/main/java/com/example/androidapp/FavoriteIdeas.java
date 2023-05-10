package com.example.androidapp;

import static com.example.androidapp.Idea.convert;
import static com.example.androidapp.Tessst.convertStringToTimestamp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteIdeas extends AppCompatActivity {
    String news = "";
    private Button enter;
    private EditText deleteNews;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Idea> ideaList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_idea);
        ListView listView = findViewById(R.id.AllIdeas_list);
        enter = findViewById(R.id.UsersIdeaEnterButton);
        deleteNews = findViewById(R.id.DeleteFavoriteNews);


        String url2 = "http://10.0.2.2:8080/favoritesIdea";
        RequestQueue queue = Volley.newRequestQueue(FavoriteIdeas.this);

        StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(FavoriteIdeas.this, "Data added to API", Toast.LENGTH_SHORT).show();
                news = news + response;
                if(response.equals("The user has no ideas")){
                    Toast.makeText(FavoriteIdeas.this, "The user has no ideas", Toast.LENGTH_SHORT).show();
                }
                else {
                    ideaList.addAll(convert(response));
                    System.out.println(ideaList);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavoriteIdeas.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                int id = Authorization.id;
                params.put("userId", String.valueOf(id));
                return params;
            }
        };
        // ideaList.addAll(convert(news));

        queue.add(request);
        ArrayAdapter<Idea> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ideaList);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();

                listView.setAdapter(stringArrayAdapter);
            }
        }, 1000);   //время задержки


        enter.setOnClickListener(new View.OnClickListener(){
            // работает удаление лайка
            @Override
            public void onClick(View v) {
                String informationLike = deleteNews.getText().toString();
                System.out.println("Hello");
                String url2 = "http://10.0.2.2:8080/delete";
                RequestQueue queue = Volley.newRequestQueue(FavoriteIdeas.this);

                StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FavoriteIdeas.this, "Data added to API", Toast.LENGTH_SHORT).show();

                        System.out.println(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(FavoriteIdeas.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        int id = Authorization.id;
                        params.put("ideaId",informationLike);
                        params.put("userId",id+"");
                        return params;
                    }
                };
                queue.add(request);
                Intent intent = new Intent(FavoriteIdeas.this,FavoriteIdeas.class);
                startActivity(intent);
            }
        });
    }
    public List <Idea> convert(String stroka) {

        String[] strings = stroka.split(",\\{");
        List<Idea>ideaList = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace("\"", "").replace("[{", "").replace("}]", "");
            String[] array = strings[i].split(",", 3);
            String[] arrayId = array[0].split(":");
            String[] titleArray = array[1].split(":");
            String[] desriptionArray = array[2].split(":");
            String description = desriptionArray[1].substring(0, desriptionArray[1].length() - 6);
            String like = desriptionArray[2].split(",")[0];
            Timestamp timestamp = convertStringToTimestamp(desriptionArray[3].substring(0, desriptionArray[3].length() - 3));
            String[] id = desriptionArray[8].split(",");
            String[] userName = desriptionArray[9].split(",");
            String favorite = desriptionArray[11].substring(0, desriptionArray[11].length() - 1);
            Boolean aBooleanFavorite = Boolean.valueOf(favorite);
            Idea idea = new Idea();
            idea.setId(Integer.parseInt(arrayId[1]));
            idea.setTitle(titleArray[1]);
            idea.setDescription(description);
            idea.setLikes(Integer.parseInt(like));
            idea.setTimestamp(timestamp);
            User user = new User();
            user.setId(Integer.parseInt(id[0]));
            user.setName(userName[0]);
            idea.setAuthor(user);
            idea.setFavorite(aBooleanFavorite);
            ideaList.add(idea);
        }

        return ideaList;
    }


}
