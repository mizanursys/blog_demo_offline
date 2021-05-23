package com.example.blog_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blog_demo.localdata.LocalData;
import com.google.android.material.textfield.TextInputEditText;

public class Createpost extends AppCompatActivity {

    TextInputEditText post_title,post_discription;
    Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);

        post_title = findViewById(R.id.post_title);
        post_discription = findViewById(R.id.post_description);
        post = findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });
    }

    private void postdata() {

        Log.e("SADasdasd","like2");
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=insert&user_id="+ LocalData.getId()+"&post_title="+
                post_title.getText().toString()+"&post_description="+post_discription.getText().toString(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SADasdasd","like2");


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERRROR>>>>>>>", error.toString());

                    }
                });

        final RequestQueue requestQueue = Volley.newRequestQueue(Createpost.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }
}