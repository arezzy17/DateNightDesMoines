package com.example.arezz.datenightdesmoines;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFoodListFragment extends Fragment implements IYelpList{

    private RecyclerView foodList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;
    private ArrayList<YelpItem> foodItems;
    private EditText searchBar;
    private ImageButton searchButton;

    public AddFoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food_list, container, false);

        foodItems = new ArrayList<YelpItem>();
        foodList = (RecyclerView) view.findViewById(R.id.food_list);

        searchBar = (EditText) view.findViewById(R.id.food_search_bar);
        searchButton = (ImageButton) view.findViewById(R.id.food_button_drinks);

        ((Button)this.getActivity().findViewById(R.id.create_new_add_button)).setVisibility(View.INVISIBLE);

        CreateNewFragmentHelpers.clearColoredElements(foodItems, foodList);

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();
        String[] params = {"categories", "sort_by"};
        String[] paramVals = {"restaurants","rating"};
        SubmitRequest(params, paramVals);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] params = {"categories", "sort_by", "term"};
                String[] paramVals = {"restaurants", "rating", searchBar.getText().toString().trim()};
                SubmitRequest(params, paramVals);
            }
        });

        searchBar.setOnEditorActionListener(new DoneOnEditorActionListener());

        layoutManager = new LinearLayoutManager(getContext());
        foodList.setLayoutManager(layoutManager);


        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(foodItems, foodList, this);

        yelpItemAdapter = new YelpItemAdapter(getContext(), foodItems,new Dialog(this.getContext()), listener);
        foodList.setAdapter(yelpItemAdapter);

        return view;
    }
    public void setList(JSONObject jsonObject){
        JSONArray businesses = new JSONArray();
        try{
            businesses=jsonObject.getJSONArray("businesses");
        }
        catch (Exception ex){

        }
        foodItems = new ArrayList<YelpItem>();
        for(int i = 0; i<businesses.length();i++){
            try{
                foodItems.add(YelpItem.GetYelpItemFromBusinessJSON(businesses.getJSONObject(i)));
            }
            catch (Exception ex){

            }
        }

        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(foodItems, foodList, this);

        yelpItemAdapter = new YelpItemAdapter(getContext(), foodItems,new Dialog(this.getContext()),listener);
        foodList.setAdapter(yelpItemAdapter);
    }

    public void SubmitRequest(String[] params, String[] paramVals){
        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();

        JsonObjectRequest myReq = MySingleton.getInstance(getContext()).GetJsonRequestFromUrl("https://api.yelp.com/v3/businesses/search",getString(R.string.bearer_key),"Des Moines, IA", params, paramVals, this );
        MySingleton.getInstance(getContext()).addToRequestQueue(myReq);
    }

}
