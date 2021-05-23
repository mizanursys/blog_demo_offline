package com.example.blog_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.blog_demo.MyListAdapter.AllDataViewHolder.post_id;
import static com.example.blog_demo.MyListAdapter.AllDataViewHolder.postdes;
import static com.example.blog_demo.MyListAdapter.AllDataViewHolder.posttitle;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.AllDataViewHolder> {

    private Context mCtx;
    private List<JSONObject> all_list =new ArrayList<>();


    public MyListAdapter(Context mCtx, List<JSONObject> all_list) {
        this.mCtx = mCtx;
        this.all_list = all_list;
//        int size=all_list.size();
        //      String total_data= String.valueOf(size);
    }

    @Override
    public AllDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //ViewHolder For RecyclerView
        View view = inflater.inflate(R.layout.my_all_post, parent, false);
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
            posttitle = list_jsonobject.getString("post_title");
            postdes = list_jsonobject.getString("post_description");


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
            holder.delete.setOnClickListener(new View.OnClickListener() {
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
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showComment((Activity) mCtx);
                }
            });
            Log.e("post_title", list_jsonobject.getString(("post_title")));
            Log.e("post_title", list_jsonobject.getString(("id")));

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
        ImageView  delete,edit;
        public static String post_id,posttitle,postdes;

        @SuppressLint("CutPasteId")
        public AllDataViewHolder(View itemView) {
            super(itemView);

            post_data=itemView.findViewById(R.id.post_data);
            details=itemView.findViewById(R.id.details);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=delete&id="+id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SADasdasd","like2");
                        notifyDataSetChanged();


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
    public void showComment(final Activity activity) {


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final LinearLayout locarea,locareachoose;
        Button saveloc;
        RecyclerView areas;

        final TextInputEditText post_title,post_description;

        dialog.setContentView(R.layout.activity_createpost);
        post_title = dialog.findViewById(R.id.post_title);
        post_description = dialog.findViewById(R.id.post_description);
        saveloc = dialog.findViewById(R.id.post);
        post_title.setText(posttitle);
        post_description.setText(postdes);

        saveloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(post_id,post_title.getText().toString(),post_description.getText().toString());
                dialog.dismiss();
            }
        });



        dialog.show();

    }

    private void update(String id,String post_title,String post_description) {
        Log.e("SADasdasd","like2");
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsInterface.BaseUrl+"?action=update&id="+id+"&post_title="+post_title+"&post_description="+post_description,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SADasdasd","like2");
                        notifyDataSetChanged();


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


