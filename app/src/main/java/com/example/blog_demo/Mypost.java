package com.example.blog_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class Mypost extends AppCompatActivity {
    List<JSONObject> all_list;
    List<String> all_list2;
    ArrayList<JSONObject> list;
    RecyclerView list_all_recyclerview;
    //SwipeRefreshLayout swipe;
    LinearLayout cheack_list;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);


        list_all_recyclerview = findViewById(R.id.post_list);
        //swipe=findViewById(R.id.swipe);
        //cheack_list=findViewById(R.id.cheack_list);

        list_all_recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        all_list = new ArrayList<>();

        //swipe down for refresh data
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadalldata();
//                swipe.setRefreshing(false);
//            }
//        });

        //Loading All data from "http://rental.datasysbd.net/api"



        loadalldata();
        //loadalldatafromlocal();

        setupWindowAnimations();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadalldatafromlocal() {
        MyListAdapter adapter = new MyListAdapter(Mypost.this, all_list);
        list_all_recyclerview.setAdapter(adapter);
        try {

            JSONObject obj = new JSONObject(LocalData.getJson());

            Log.d("My App", obj.toString());
            all_list.add(obj);
            Log.e("HOOJSON2", String.valueOf(obj));
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + LocalData.getJson() + "\"");
        }
        //insserting all data inside array list



    }


    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }



    public void loadalldata() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=fetchpostbyuser&user_id="+ LocalData.getId(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        all_list.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray emailData = data.getJSONArray("posts");


                            for (int i = 0; i < emailData.length(); i++) {
                                Log.e("category_list", emailData.get(i).toString());
                                JSONObject dat = (JSONObject) emailData.get(i);
                                //RecyclerView Adapter
                                MyListAdapter adapter = new MyListAdapter(Mypost.this, all_list);
                                list_all_recyclerview.setAdapter(adapter);

                                //insserting all data inside array list

                                all_list.add(dat);
                                //list.add(dat);
                                LocalData.setJson(String.valueOf(dat));
                                Log.e("HOOJSON", String.valueOf(LocalData.getJson()));

                                adapter.notifyDataSetChanged();

                            }

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Mypost.this, HomePage.class);
        startActivity(intent);
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Log.e("Menudata","add");
            return(true);
        case R.id.mypost:
            Intent intent = new Intent(Mypost.this,Mypost.class);
            startActivity(intent);
            Log.e("Menudata","mypost");
            return(true);
        case R.id.about:
            Log.e("Menudata","about");
            return(true);
        case R.id.exit:
            Log.e("Menudata","exit");
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}