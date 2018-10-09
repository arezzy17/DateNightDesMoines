package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    public JSONObject jResponse;

    private MySingleton(Context context) {
        mCtx = context;
        jResponse = new JSONObject();
        // Instantiate the cache

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

    }
    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public JsonObjectRequest GetJsonRequestFromUrl(String url, final String Authorization, final String location, final String[] params, final String[] paramVals,  final IYelpList fragment) {

        url=url+"?location="+location+"&";
        for(int i =0; i < params.length; i++){
            url = url+params[i]+"="+paramVals[i];

            if(i!=params.length-1){
                url=url+"&";
            }
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        fragment.setList(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        String mes = error.getMessage();
                    }
                }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", Authorization);
                return params;
            }

        }  ;

        return jsonObjectRequest;

    }

}
