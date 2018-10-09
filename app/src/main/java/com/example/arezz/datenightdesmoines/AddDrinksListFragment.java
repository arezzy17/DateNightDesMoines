package com.example.arezz.datenightdesmoines;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDrinksListFragment extends Fragment implements IYelpList {

    private RecyclerView drinksList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;
    private ArrayList<YelpItem> drinksItems;
    public AddDrinksListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drinks_list, container, false);

        drinksItems = new ArrayList<YelpItem>();
        drinksList = (RecyclerView) view.findViewById(R.id.drinks_list);

        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();
        String[] params = {"categories", "sort_by"};
        String[] paramVals = {"bars", "rating"};
        JsonObjectRequest myReq = MySingleton.getInstance(getContext()).GetJsonRequestFromUrl("https://api.yelp.com/v3/businesses/search","Bearer baYflpcDgbIhpcxDzfCTVY-8-MNrTaQKs-Xi7TkguApK9CW1ezFdxhlNAS754U7dQEou-gJzbZkP54dNIrFO_70lrO1cIcNS0ziaZBqslfvysRtzBZ04M-LFYt23W3Yx","Des Moines, IA", params, paramVals, this );
        // Add a request (in this example, called stringRequest) to your RequestQueue.

        MySingleton.getInstance(getContext()).addToRequestQueue(myReq);


        layoutManager = new LinearLayoutManager(getContext());
        drinksList.setLayoutManager(layoutManager);

        yelpItemAdapter = new YelpItemAdapter(getContext(), drinksItems);
        drinksList.setAdapter(yelpItemAdapter);

        return view;
    }
    public void setList(JSONObject jsonObject){
        JSONArray businesses = new JSONArray();
        try{
            businesses=jsonObject.getJSONArray("businesses");
        }
        catch (Exception ex){

        }
        drinksItems = new ArrayList<YelpItem>();
        for(int i = 0; i<businesses.length();i++){
            try{
                drinksItems.add(new YelpItem(businesses.getJSONObject(i).getString("name"),businesses.getJSONObject(i).getDouble("rating")+""));
            }
            catch (Exception ex){

            }
        }

        yelpItemAdapter = new YelpItemAdapter(getContext(), drinksItems);
        drinksList.setAdapter(yelpItemAdapter);
    }
}
