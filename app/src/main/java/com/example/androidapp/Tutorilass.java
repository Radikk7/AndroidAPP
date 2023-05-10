package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tutorilass extends AppCompatActivity {

    private String[] strings = new String[]{"Futbol", "Barcelona champion", "Donna D'Errico feierte am vergangenen Donnerstag ihren 55. Geburtstag. Als Geschenk gab es ein heißes Halbnackt-Bild für ihre Fans: Im roten Einteiler posiert die Schauspielerin darauf am Strand.", "Visca Barca", "1"};
    private ListView listView, listView2;
    private String[] strings2 = new String[]{"City", "Munchen", "Bayern", "Deutschland", "1"};
    String news = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //    Idea idea = new Idea();
        //   User user77 = new User();
        //  user77.setId(777);
        // user77.setName("Cuckold");
        //user77.setPhone("+4972212332");
        //idea.setId(777);
        //idea.setAuthor(user77);
        //idea.setDescription("Pridurki");
        //idea.setTitle("Kto takie?");
        //Timestamp today = new Timestamp(System.currentTimeMillis());
        //Idea idea1 = new Idea();
        //idea1.setId(888);
        //idea1.setAuthor(user77);
        //idea.setDescription("Germanyyyy");
        //idea1.setTitle("Hamburg");
        //idea1.setTimestamp(today);


        //  idea.setTimestamp(today);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        listView = findViewById(R.id.list_item_ideas);
        listView2 = findViewById(R.id.list_item_ideas2);


        List<Idea> ideaList = new ArrayList<>();
        //ideaList.addAll(ideas());
        //ideaList.add(idea1);
        ArrayAdapter<Idea> ideaArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ideaList);
        // ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        //ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings2);

       // listView.setAdapter(ideaArrayAdapter);
        // listView.setAdapter(stringArrayAdapter);
        //listView2.setAdapter(stringArrayAdapter2);

        String url2 = "http://10.0.2.2:8080/personals";
        RequestQueue queue = Volley.newRequestQueue(Tutorilass.this);


        StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Tutorilass.this, "Data added to API", Toast.LENGTH_SHORT).show();
                news = news + response;


             //   ideaList.addAll(ideas());

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//
                Toast.makeText(Tutorilass.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
//
                Map<String, String> params = new HashMap<String, String>();
                int id = Authorization.id;
                params.put("userId", String.valueOf(id));
                return params;
            }
        };
        queue.add(request);
        ideaList.addAll(convert(news));
      // ArrayAdapter<Idea> ideaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ideaList);
        listView.setAdapter(ideaArrayAdapter);
    }

    public List<Idea> convert(String stroka) {
        //  Gson gson = new Gson();
        // JsonParser parser = new JsonParser();
        // JsonObject object = (JsonObject) parser.parse(stroka);// response will be the json String
        //Idea idea = gson.fromJson(object, Idea.class);

        String[] strings = stroka.split(",\\{");
        List<Idea> ideaList = new ArrayList<>();
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

    public static Timestamp convertStringToTimestamp(String strDate) {
        try {
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public List<Idea> ideas() {
        Idea idea = new Idea();
        User user77 = new User();
        user77.setId(777);
        user77.setName("Cuckold");
        user77.setPhone("+4972212332");
        idea.setId(777);
        idea.setAuthor(user77);
        idea.setDescription("Pridurki");
        idea.setTitle("Kto takie?");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<Idea>ideaList22 = new ArrayList<>();
        ideaList22.add(idea);
        return ideaList22;
    }

}
