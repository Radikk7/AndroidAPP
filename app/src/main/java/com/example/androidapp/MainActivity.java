package com.example.androidapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private EditText userName;
    private EditText userLogin;
    private EditText editTextTextPassword;
    private EditText userPhone;
    private Button button, auth_button,create;
    private TextView nameApplication;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        userLogin = findViewById(R.id.userLogin);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        userPhone = findViewById(R.id.userPhone);
        button = findViewById(R.id.button);
        nameApplication = findViewById(R.id.NameApplication);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".NewIdea");
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() { //вызываем все по нажатию кнопки на экране
            @Override
            public void onClick(View view) {
                // if (userName.getText().toString().equals("")) { // преобразовываем запрос в строку и проверяем на null
                //     Toast.makeText(MainActivity.this, R.string.error_user_name, Toast.LENGTH_LONG).show(); // в случае ошибки выдаём пользователю баннер на 3 секунды
                // }
                // if (userLogin.getText().toString().equals("")) {
                //     Toast.makeText(MainActivity.this, R.string.error_user_login, Toast.LENGTH_LONG).show();
                // }
                // if (editTextTextPassword.getText().toString().equals("")) {
                //     Toast.makeText(MainActivity.this, R.string.error_user_password, Toast.LENGTH_LONG).show();
                // }
                // if (userPhone.getText().toString().equals("")) {
                //     Toast.makeText(MainActivity.this, R.string.error_user_phone, Toast.LENGTH_LONG).show();
                // } else {

                     String informationUserName = userName.getText().toString();
                     String informationUserLogin = userLogin.getText().toString();
                     String informationUserEditTextTextPassword = editTextTextPassword.getText().toString();
                     String informationUserPhone = userPhone.getText().toString();
                     String informationButton = button.getText().toString();
                //     String url = "http://10.0.2.2:8080/hello"; //В видео говорит что формат должен быть https иначе может не работать
                //    new GetUrlData().execute(url);
                  String url2 = "http://10.0.2.2:8080/user";
                // String url2 = "http://localhost:8080/moin";
               // String url2 = "http://192.168.235.20/moin";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                        response -> Toast.makeText(MainActivity.this, "Succes", Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show()) {



//     @Override
               //     protected Map<String, String> getParams() throws AuthFailureError {
               //         Map<String, String> params = new HashMap<>();
               //         params.put("userName", informationUserName);
               //         params.put("userLogin",informationUserLogin);
               //         params.put("editTextTextPassword",informationUserEditTextTextPassword);
               //         params.put("userPhone",informationUserPhone);
               //         return params;
                    //    }
                };
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);


            }
            //}
        });
    }

    private class GetUrlData extends AsyncTask<String, String, String> {

        //protected void onPreExecute(){
        //    super.onPreExecute();
        //    result_info.setText("Waiting");
        //по идее здесь можно было сделать баннер что грузятся данные , но почему то возникает ошибка
        //}
//
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


        // @Override
        // protected  void  onPostExecute(String result){
        //     super.onPostExecute(result);
//
        //    result_info.Set
        // }
    }
}