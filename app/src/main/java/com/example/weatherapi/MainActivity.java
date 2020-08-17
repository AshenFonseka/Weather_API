package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView t1_temp,t2_city,t3_description,t4_date,t5_wind,t6_wind1,t7_humidity,t8_pressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_temp = (TextView)findViewById(R.id.textView);
        t2_city = (TextView)findViewById(R.id.textView3);
        t3_description = (TextView)findViewById(R.id.textView4);
        t4_date = (TextView)findViewById(R.id.textView2);
        t5_wind = (TextView)findViewById(R.id.textView6);
        t6_wind1 = (TextView)findViewById(R.id.textView11);
        t7_humidity = (TextView)findViewById(R.id.textView12);
        t8_pressure = (TextView)findViewById(R.id.textView13);




        find_weather();
    }

    public void find_weather()
    {

        String url = "http://api.openweathermap.org/data/2.5/weather?lat=-37.915047&lon=145.129272&units=imperial&appid=57df42a409e4c7c20a3221979d61174d";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONObject wind_object = response.getJSONObject("wind");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String humidity = String.valueOf(main_object.getDouble("humidity"));
                    String pressure = String.valueOf(main_object.getDouble("pressure"));
                    String description = object.getString("description");
                    String city = response.getString("name");
                    String wind = String.valueOf(wind_object.getDouble("speed"));
                    String wind1 = String.valueOf(wind_object.getDouble("deg"));


                   // t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);
                    t5_wind.setText(wind);
                    t6_wind1.setText(wind1);
                    t7_humidity.setText(humidity);
                    t8_pressure.setText(pressure);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-DD");
                    String formatted_date = sdf.format(calendar.getTime());

                    t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int -32)/1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));




                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }
}