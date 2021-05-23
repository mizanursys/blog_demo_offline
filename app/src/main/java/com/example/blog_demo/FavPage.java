package com.example.blog_demo;

import android.os.Bundle;
import android.util.Log;

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

public class FavPage extends AppCompatActivity {
    List<JSONObject> all_list;
    RecyclerView list_all_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);
        list_all_recyclerview = findViewById(R.id.post_list);
        //swipe=findViewById(R.id.swipe);
        //cheack_list=findViewById(R.id.cheack_list);

        list_all_recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        all_list = new ArrayList<>();
        loadalldata();
    }
    public void loadalldata() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=fetch_fav&user_id="+ LocalData.getId(),
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
                                MyListAdapter adapter = new MyListAdapter(FavPage.this, all_list);
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
}