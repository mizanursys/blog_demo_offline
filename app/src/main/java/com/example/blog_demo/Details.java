package com.example.blog_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            Bundle b = getIntent().getExtras();
            assert b != null;
            id=b.getString("id");
            Log.e("Dog", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        details();
    }

    public void details() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=fetch_single&id="+id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Log.e("Data", String.valueOf(response));
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("Data", String.valueOf(jsonObject));
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject emailData = data.getJSONObject("posts");
                            Log.e("name_list", String.valueOf(emailData.getString("post_title")));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERRROR>>>>>>>", error.toString());

                    }
                });

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
}