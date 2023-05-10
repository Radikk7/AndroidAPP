package com.example.androidapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Authorization extends AppCompatActivity {
    RequestQueue requestQueue;
    private EditText userLogin;
    private EditText editTextTextPassword;
    private String stringLatitude = "";

    private Button buttonAuth;
    private TextView textView;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public static int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        userLogin = findViewById(R.id.userLogin);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        buttonAuth = findViewById(R.id.AuthorizationButton);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView = (TextView) findViewById(R.id.Clock);

        Calendar c = Calendar.getInstance();

        System.out.println("Current time => " + c.getTime());

        //   @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //String formattedDate = df.format(c.getTime());
       // textView.setText(formattedDate); закоментил старое решение

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                     stringLatitude = Double.toString(location.getTime()); // не работает почему то

                }
            }
        });

        textView.setText("Time" + stringLatitude);






        buttonAuth.setOnClickListener(new View.OnClickListener() { //вызываем все по нажатию кнопки на экране


            @Override
            public void onClick(View view) {
                String informationUserLogin = userLogin.getText().toString();
                String informationUserEditTextTextPassword = editTextTextPassword.getText().toString();

                String url2 = "http://10.0.2.2:8080/userauAuthorization";



                RequestQueue queue = Volley.newRequestQueue(Authorization.this);


                StringRequest request = new StringRequest(Request.Method.POST, url2, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Authorization.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        id = Integer.parseInt(response);
                        System.out.println(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Authorization.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("login", informationUserLogin);
                        params.put("password", informationUserEditTextTextPassword);

                        return params;
                    }
                };
                queue.add(request);

        //      StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
        //          @Override
        //          public void onResponse(String response) {
        //              Toast.makeText(Authorization.this, "Data added to API", Toast.LENGTH_SHORT).show();
        //              name = response;
        //              System.out.println(response);
        //          }
        //      }, new com.android.volley.Response.ErrorListener() {
        //          @Override
        //          public void onErrorResponse(VolleyError error) {

        //              Toast.makeText(Authorization.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
        //          }
        //      }) {
        //          @Override
        //          protected Map<String, String> getParams() {
        //              Map<String, String> params = new HashMap<String, String>();
        //              params.put("userId", String.valueOf(id));
        //              return params;
        //          }
        //      };
        //      queue.add(stringRequest);
        //      System.out.println(name);
                Intent intent = new Intent(Authorization.this, NewIdea.class);
                startActivity(intent);

            }
        });
    }

    private class GetUrlData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();//http соединение
                connection.connect();//

                InputStream stream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(stream));// считываем поток
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {//цикл длится до тех пор пока считывание не ровно null
                    buffer.append(line).append("\n");
                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null)
                    connection.disconnect();

                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
