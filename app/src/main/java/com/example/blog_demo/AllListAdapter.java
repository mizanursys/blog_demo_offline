package com.example.blog_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blog_demo.app.GsuiteApp;
import com.example.blog_demo.localdata.LocalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.blog_demo.AllListAdapter.AllDataViewHolder.post_id;

public class AllListAdapter extends RecyclerView.Adapter<AllListAdapter.AllDataViewHolder> {

    private Context mCtx;
    private List<JSONObject> all_list =new ArrayList<>();


    public AllListAdapter(Context mCtx, List<JSONObject> all_list) {
        this.mCtx = mCtx;
        this.all_list = all_list;
//        int size=all_list.size();
  //      String total_data= String.valueOf(size);
    }

    @Override
    public AllDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //ViewHolder For RecyclerView
        View view = inflater.inflate(R.layout.all_post, parent, false);
        final AllDataViewHolder alldataViewHolder=new AllDataViewHolder(view);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=alldataViewHolder.getAdapterPosition();
                Toast.makeText(mCtx,"Item at position "+position+" deleted", Toast.LENGTH_SHORT).show();
                //  all_list.add((JSONObject) all_list);
                notifyDataSetChanged();
                //if(personModifier!=null){personModifier.onPersonDeleted(position);}
                return true;
            }
        });




        return new AllDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AllDataViewHolder holder, final int position) {
        try {
            final JSONObject list_jsonobject = all_list.get(position);
            holder.post_data.setText(list_jsonobject.getString("post_title"));
            post_id = list_jsonobject.getString("id");

            if (!list_jsonobject.isNull("post_liked")){
                if (list_jsonobject.getString("post_liked").equals("1")){
                    holder.like.setBackgroundResource(R.drawable.ic_heart);
                }else {
                    holder.like.setBackgroundResource(R.drawable.ic_like);
                }
            }else {
                holder.like.setBackgroundResource(R.drawable.ic_like);
            }


            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx,Details.class);
                    try {
                        intent.putExtra("id",list_jsonobject.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mCtx.startActivity(intent);


                }
            });
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("SADasdasd","like");

                    try {
                        like(list_jsonobject.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            Log.e("post_title", list_jsonobject.getString(("post_title")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        holder.details_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Log.e("hooooo", all_list.get(holder.getAdapterPosition()).
//                            getString("name"));
//                    String description = all_list.get(holder.getAdapterPosition()).getString("description");
//                    String charge_daily = all_list.get(holder.getAdapterPosition()).getString("charge_daily");
//                    String charge_weekly = all_list.get(holder.getAdapterPosition()).getString("charge_weekly");
//                    String charge_monthly = all_list.get(holder.getAdapterPosition()).getString("charge_monthly");
//                    String name = all_list.get(holder.getAdapterPosition()).getString("name");
//                    Intent intent = new Intent(mCtx, DetailsActivity.class);
//                    intent.putExtra("details", description);
//                    intent.putExtra("name", name);
//                    intent.putExtra("charge_daily", charge_daily);
//                    intent.putExtra("charge_weekly", charge_weekly);
//                    intent.putExtra("charge_monthly", charge_monthly);
//                    mCtx.startActivity(intent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }




    @Override
    public int getItemCount() {

        try {
            return all_list.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    static class AllDataViewHolder extends RecyclerView.ViewHolder{

        TextView post_data;
        Button details_button;
        LinearLayout details;
        ImageView like,dislike;
        public static String post_id;

        @SuppressLint("CutPasteId")
        public AllDataViewHolder(View itemView) {
            super(itemView);

            post_data=itemView.findViewById(R.id.post_data);
            details=itemView.findViewById(R.id.details);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);

            //fetch_fav();
        }

        private void fetch_fav() {

            Log.e("SADasdasd","like2");
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

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = new JSONObject(jsonObject.getString("data"));
                                JSONArray jsonArray = data.getJSONArray("posts");

                                for (int i=0; i<jsonArray.length(); i++){
                                    Log.e("category_list", jsonArray.get(i).toString());
                                    JSONObject dat = (JSONObject) jsonArray.get(i);

                                    if (dat.getString("status").equals("1")){
                                        like.setBackgroundResource(R.drawable.ic_heart);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("SADasdasd","like2");


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERRROR>>>>>>>", error.toString());

                        }
                    });

            final RequestQueue requestQueue = Volley.newRequestQueue(GsuiteApp.getContext());
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();
        }

    }
    public void like(String id) {
        Log.e("SADasdasd","like2");
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=insert_fav&user_id="+ LocalData.getId()+"&post_id="+id,
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

        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    public void dislike(String id) {
        Log.e("SADasdasd","like2");
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=dislike_fav&user_id="+ LocalData.getId()+"&post_id="+id,
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

        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
}


