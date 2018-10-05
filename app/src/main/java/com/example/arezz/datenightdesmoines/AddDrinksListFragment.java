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
public class AddDrinksListFragment extends Fragment {

    private RecyclerView drinksList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;

    public AddDrinksListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drinks_list, container, false);

        ArrayList<YelpItem> drinksItems = new ArrayList<YelpItem>();
        drinksList = (RecyclerView) view.findViewById(R.id.drinks_list);

        drinksItems.add((new YelpItem("DrinkTest","7")));
        drinksItems.add((new YelpItem("DrinkTest2","2")));


        layoutManager = new LinearLayoutManager(getContext());
        drinksList.setLayoutManager(layoutManager);

        yelpItemAdapter = new YelpItemAdapter(getContext(), drinksItems);
        drinksList.setAdapter(yelpItemAdapter);

        return view;
    }

}
