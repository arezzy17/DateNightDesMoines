package com.example.arezz.datenightdesmoines;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEntertainmentListFragment extends Fragment implements IYelpList {

    private RecyclerView entertainmentList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;
    private ArrayList<YelpItem> entertainmentItems;
    public AddEntertainmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_entertainment_list, container, false);

        entertainmentItems = new ArrayList<YelpItem>();
        entertainmentList = (RecyclerView) view.findViewById(R.id.entertainment_list);
        ((Button)this.getActivity().findViewById(R.id.create_new_add_button)).setVisibility(View.INVISIBLE);
        CreateNewFragmentHelpers.clearColoredElements(entertainmentItems, entertainmentList);


        RequestQueue queue = MySingleton.getInstance(getContext().getApplicationContext()).getRequestQueue();
        String[] params = {"categories", "sort_by"};
        String[] paramVals = {"arts", "rating"};
        JsonObjectRequest myReq = MySingleton.getInstance(getContext()).GetJsonRequestFromUrl("https://api.yelp.com/v3/businesses/search","Bearer baYflpcDgbIhpcxDzfCTVY-8-MNrTaQKs-Xi7TkguApK9CW1ezFdxhlNAS754U7dQEou-gJzbZkP54dNIrFO_70lrO1cIcNS0ziaZBqslfvysRtzBZ04M-LFYt23W3Yx","Des Moines, IA", params, paramVals, this );
        // Add a request (in this example, called stringRequest) to your RequestQueue.

        MySingleton.getInstance(getContext()).addToRequestQueue(myReq);

        layoutManager = new LinearLayoutManager(getContext());
        entertainmentList.setLayoutManager(layoutManager);

        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(entertainmentItems, entertainmentList, this);

        yelpItemAdapter = new YelpItemAdapter(getContext(), entertainmentItems,new Dialog(this.getContext()), listener);
        entertainmentList.setAdapter(yelpItemAdapter);

        return view;
    }
    public void setList(JSONObject jsonObject){
        JSONArray businesses = new JSONArray();
        try{
            businesses=jsonObject.getJSONArray("businesses");
        }
        catch (Exception ex){

        }
        entertainmentItems = new ArrayList<YelpItem>();
        for(int i = 0; i<businesses.length();i++){
            try{
                entertainmentItems.add(YelpItem.GetYelpItemFromBusinessJSON(businesses.getJSONObject(i)));
            }
            catch (Exception ex){

            }
        }
        RecyclerViewClickListener listener = CreateNewFragmentHelpers.GetFragmentListener(entertainmentItems, entertainmentList, this);

        yelpItemAdapter = new YelpItemAdapter(getContext(), entertainmentItems,new Dialog(this.getContext()), listener);
        entertainmentList.setAdapter(yelpItemAdapter);
    }
}
