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
public class AddEntertainmentListFragment extends Fragment {

    private RecyclerView entertainmentList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter yelpItemAdapter;


    public AddEntertainmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_entertainment_list, container, false);

        ArrayList<YelpItem> entertainmentItems = new ArrayList<YelpItem>();
        entertainmentList = (RecyclerView) view.findViewById(R.id.entertainment_list);

        entertainmentItems.add((new YelpItem("EnterTest","5")));
        entertainmentItems.add((new YelpItem("EnterTest2","1")));


        layoutManager = new LinearLayoutManager(getContext());
        entertainmentList.setLayoutManager(layoutManager);

        yelpItemAdapter = new YelpItemAdapter(getContext(), entertainmentItems);
        entertainmentList.setAdapter(yelpItemAdapter);

        return view;
    }

}
