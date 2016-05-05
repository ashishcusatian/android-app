package com.example.ashish.lookatthemoon;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button moon,funfact,locatemoon;
    TextView infor;
    TextView textView;


    RequestQueue requestQueue;



    public int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count=0;
        moon = (Button) findViewById(R.id.btn);
        locatemoon=(Button) findViewById(R.id.locate_moon);
        funfact=(Button) findViewById(R.id.funfacts);
        infor =(TextView) findViewById(R.id.moon_data);
        requestQueue= Volley.newRequestQueue(this);
        textView=(TextView)findViewById(R.id.count);
        moon.setOnClickListener(this);
        funfact.setOnClickListener(this);
        locatemoon.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn) {
            count++;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.burningsoul.in/moon",
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                JSONObject query1 = response.getJSONObject("FM");
                                JSONObject query2 = response.getJSONObject("NNM");
                                String ndt = query2.getString("DT");
                                String stage = response.getString("stage");
                                String ut = query1.getString("UT");
                                String dt = query1.getString("DT");

                                infor.setText("FULL MOON : " + dt + "\nNEW MOON : " + ndt + "\nSTATUS : " + stage);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Network not found", Toast.LENGTH_SHORT).show();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
            textView.setText("Count: " + count);
        }
        else if(view.getId()==R.id.locate_moon)
        {
            Intent intent=new Intent(this,Locate_moon.class);
            startActivity(intent);
        }

        else if(view.getId()==R.id.funfacts)
        {
            Intent intent=new Intent(this,Fun_facts.class);
            startActivity(intent);
        }
    }
}
