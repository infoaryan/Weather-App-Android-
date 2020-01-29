package infoaryan.in.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText editText;
    TextView desc,latlon,temp,winddir;
    Button button;

    RequestQueue requestQueue;    //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	//
	//Finding the views
        textview=findViewById(R.id.showtext);
        editText=findViewById(R.id.edittext);
        button=findViewById(R.id.button);        //Triggers the action to request the volley
        desc=findViewById(R.id.desc);            //showing the description of weather
        latlon = findViewById(R.id.latlon);      //showing the latitude and longitude
        temp = findViewById(R.id.temperature);   //showint temperature
        winddir = findViewById(R.id.winddirec);  //showing the direction of wind

       //getting instance of the request que *
        requestQueue=VolleySingleton.getInstance(this).getmRequestQueue(); //*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAPIrequest();
            }
        });
    }


    // here in thtese methods we wil request for the elements
    //basically the there is a large JSON object so we will cast it to JSONObject

    private void sendAPIrequest(){
	//Prepare the url to be feeded to Volley 

	//In place of "GENERATED_API_KEY" please include your own API_KEY 
	//The API_KEY may be obtained from OpenWeatherMap official site and signing in 
	//The API may be setup by choosing weather data api and then going on to the API keys (Which is alpha numeric long key)

        String url="http://api.openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&APPID=GENERATED_API_KEY";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //casting the response data to JSONArray
		    //May proceed as per convinience
                    JSONArray jsonArray=response.getJSONArray("weather");
                    JSONObject Coord=response.getJSONObject("coord");
                    JSONObject Temp = response.getJSONObject("main");
                    JSONObject wind = response.getJSONObject("wind");
                    //settind up
                    latlon.setText("Latt: "+Coord.getString("lat")+" Long: "+Coord.getString("lon"));
                    temp.setText(Temp.getString("temp")+" F");
                    winddir.setText(wind.getString("deg") + "deg");

                    for(int i=0;i<jsonArray.length();++i){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                         String myweather=jsonObject.getString("main");
                         String description = jsonObject.getString("description");
                         desc.setText("Desc: "+description);
                         textview.setText(myweather);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
		Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

	//Adding the request to the request queue
	//An easy Alternative to this is remove VolleySingleton class and all the lines which are marked by * as comment
	//Now include Following line below
	//requestQueue= Volley.newRequestQueue(this);
        //requestQueue.add(request);

        VolleySingleton.getInstance(MainActivity.this).addTorequestque(request);  //*
    }
}
