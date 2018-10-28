package com.example.arezz.datenightdesmoines;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText searchBar;
    private ImageButton searchButton;

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
        searchBar = (EditText) view.findViewById(R.id.drinks_search_bar);
        searchButton = (ImageButton) view.findViewById(R.id.search_button_drinks);
        ((Button)this.getActivity().findViewById(R.id.create_new_add_button)).setVisibility(View.INVISIBLE);

        CreateNewFragmentHelpers.clearColoredElements(drinksItems, drinksList);

        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();
        String[] params = {"categories", "sort_by"};
        String[] paramVals = {"bars", "rating"};
        SubmitRequest(params, paramVals);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] params = {"categories", "sort_by", "term"};
                String[] paramVals = {"bars", "rating", searchBar.getText().toString().trim()};
                SubmitRequest(params, paramVals);
            }
        });

        searchBar.setOnEditorActionListener(new DoneOnEditorActionListener());

        layoutManager = new LinearLayoutManager(getContext());
        drinksList.setLayoutManager(layoutManager);

        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(drinksItems, drinksList, (android.support.v4.app.Fragment)this);


        yelpItemAdapter = new YelpItemAdapter(getContext(), drinksItems, new Dialog(this.getContext()), listener);
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
                drinksItems.add(YelpItem.GetYelpItemFromBusinessJSON(businesses.getJSONObject(i)));
            }
            catch (Exception ex){

            }
        }
        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(drinksItems, drinksList, (android.support.v4.app.Fragment)this);

        yelpItemAdapter = new YelpItemAdapter(getContext(), drinksItems, new Dialog(this.getContext()), listener);
        drinksList.setAdapter(yelpItemAdapter);
    }

    public void SubmitRequest(String[] params, String[] paramVals){
        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();

        JsonObjectRequest myReq = MySingleton.getInstance(getContext()).GetJsonRequestFromUrl("https://api.yelp.com/v3/businesses/search",getString(R.string.bearer_key),"Des Moines, IA", params, paramVals, this );
        MySingleton.getInstance(getContext()).addToRequestQueue(myReq);
    }
}

