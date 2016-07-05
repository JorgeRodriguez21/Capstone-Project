package com.app.bedomax.tagadata.handlers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.models.New;
import com.app.bedomax.tagadata.services.VolleyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 04/07/16.
 */
public class NewsHandler {


    private List<New> news;
    private Context context;
    protected RequestQueue requestQueue;
    private VolleyService volley;

    public NewsHandler(Context context) {
        this.context = context;
        news = new ArrayList<>();
        volley = VolleyService.getInstance(this.context);
        requestQueue = volley.getRequestQueue();
    }

    public void searchNews(final HandlerCallback.listener listener){
        String url = context.getString(R.string.url);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                    New newObject;
                    for (int i = 0; i<jsonArray.length();i++){

                        JSONObject o = jsonArray.getJSONObject(i);
                        newObject = gson.fromJson(o.toString(), New.class);
                        news.add(newObject);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                listener.onResponse(HandlerCallback.OK);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onResponse(HandlerCallback.FAIL);
                try {
                    System.out.println(new String(volleyError.networkResponse.data,"UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        addToQueue(request);
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (requestQueue == null)
                requestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }
    }

    public List<New> getNews() {
        return news;
    }
}
