package com.example.arezz.datenightdesmoines;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFoodListFragment extends Fragment {

    private RecyclerView foodList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;

    public AddFoodListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food_list, container, false);

        ArrayList<YelpItem> foodItems = new ArrayList<YelpItem>();
        foodList = (RecyclerView) view.findViewById(R.id.food_list);

        foodItems.add((new YelpItem("Test","4")));
        foodItems.add((new YelpItem("Test2","3")));


        layoutManager = new LinearLayoutManager(getContext());
        foodList.setLayoutManager(layoutManager);

        yelpItemAdapter = new YelpItemAdapter(getContext(), foodItems);
        foodList.setAdapter(yelpItemAdapter);

        return view;
    }

}
