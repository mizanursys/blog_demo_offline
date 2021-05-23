package com.example.blog_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blog_demo.localdata.LocalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText phone_no,password;
    Button login_btn;
    TextView signup;
    Context context = this;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone_no = findViewById(R.id.phone_no);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                sendAndRequestResponse();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, Registration.class);
                startActivity(signup);
            }
        });
    }

    private void login2() {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String mob2 =phone_no.getText().toString();
        final String inputpass = password.getText().toString();



        StringRequest sr = new StringRequest(Request.Method.GET,
                "https://192.168.1.142/blog/api_call.php?action=login&phone_no="+mob2+"&password="+inputpass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Testing",response);
                        Log.e("Testing","https://localhost/blog/api_call.php?action=login");
                        Intent intent =new Intent(MainActivity.this, HomePage.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Testing","https://localhost/blog/api_call.php?action=login&phone_no="+mob2+"&password="+inputpass);

                Toast.makeText(MainActivity.this, "Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);


    }
    private void login() {
        final String mob2 =phone_no.getText().toString();
        final String inputpass = password.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.142/blog/api_call.php?action=login",
                response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.getJSONObject("data");

                        Log.e("Cheking_Responce", String.valueOf(jsonObject));
                        Log.e("Cheking_Responce", String.valueOf(jsonObject));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("ERRROR>>>>>>>", error.toString())){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",mob2);
                params.put("password",inputpass);
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }
    public void df(){
        RequestQueue queue = Volley.newRequestQueue(context);

        final String mob2 =phone_no.getText().toString();

        final String inputpass = password.getText().toString();


        StringRequest sr = new StringRequest(Request.Method.POST,
                "https://192.168.1.142/blog/api_call.php?action=login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent intent =new Intent(MainActivity.this, HomePage.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",mob2);
                params.put("password",inputpass);
                Log.e("test_params", String.valueOf(params));



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
    private void sendAndRequestResponse() {
        final String mob2 =phone_no.getText().toString();
        final String inputpass = password.getText().toString();
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.142/blog/api_call.php?action=login&phone_no="+mob2+"&password="+inputpass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Data", String.valueOf(jsonObject));
                    String result = jsonObject.getString("result");
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                    Log.e("Data", String.valueOf(result));


                    if (result.equals("1")){
                        LocalData.setId(jsonObject1.getString("user_id"));
                        Intent intent= new Intent(MainActivity.this,HomePage.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(),"Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("TAG","Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}