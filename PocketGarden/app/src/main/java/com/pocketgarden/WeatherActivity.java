package com.pocketgarden;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        listView = findViewById(R.id.plantListRecycler);
    }
}
//this was with the open weather, now we are using accuweather
/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {

    TextView cityName;
    ImageButton searchButton;
    TextView result;

//First String means URL is in the String, void = nothing, third string means return type will be a string
    class Weather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... address) {
            //String... means multiple addresses can be sent and it acts as an array
            //to check if URL is valid or not
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //establishes connection to the address
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //retrieves the data and returns as String
                int data = isr.read();
                String content = "";
                char ch;
                while(data != -1){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();

                }
                return content;

            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
               e.printStackTrace();
            }

            return null;
        }
    }

    public void search(View view){

        cityName = findViewById(R.id.zipField);
        searchButton = findViewById(R.id.zipSearchButton);
        result = findViewById(R.id.temperatureText);


        String cName = cityName.getText().toString();



        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=" +
                     cName +"&appid=b6907d289e10d714a6e88b30761fae22").get();
            //check data if it is retrieved alright
            Log.i( "contentData", content);

            //JSON
            JSONObject jsnObject = new JSONObject(content);
            String weatherData = jsnObject.getString( "weather");
            String mainTemperature = jsnObject.getString( "main");//this main is not part of weather array it seperates variables like weather
            Double visibility;

            Log.i("weatherData", weatherData);


            //weather data is in array
            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";
            String temperature = "";

            for(int i = 0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString( "main");
                description = weatherPart.getString( "description");
            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");



            Log.i("Temperature", temperature);

            /*Log.i(tag: "main", main);
            Log.i(tag: "description", description);*/

      /*      String resultText = "Main:  "+ main + " " +
                    "\nDescription : " + description +
                    "\nTemperature : " +temperature;

            result.setText(resultText);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);



    }
}
*/
