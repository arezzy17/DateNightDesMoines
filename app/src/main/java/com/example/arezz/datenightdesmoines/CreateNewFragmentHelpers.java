package com.example.arezz.datenightdesmoines;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CreateNewFragmentHelpers {

    public static RecyclerViewClickListener GetFragmentListener(final ArrayList<YelpItem> items, final RecyclerView list, final Fragment thisFrag){
        return new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                try{
                    clearColoredElements(items, list);
                    list.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor(Color.parseColor("#b3b3b3"));
                    ((Button)thisFrag.getActivity().findViewById(R.id.create_new_add_button)).setVisibility(View.VISIBLE);
                    ((IYelpId)thisFrag.getActivity()).setYelpId(items.get(position).getYelpId());
                    ((IYelpId)thisFrag.getActivity()).setYelpName(items.get(position).getName());
                }
                catch (Exception ex){
                    String exc = ex.getMessage();

                }

            }


        };
    }
    public static void clearColoredElements(ArrayList<YelpItem> items, RecyclerView list) {
        for(int i = 0; i <items.size(); i++){
            if(list.findViewHolderForAdapterPosition(i) != null)
                list.findViewHolderForAdapterPosition(i).itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
