package com.example.arezz.datenightdesmoines;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ConfirmNightAdapter extends RecyclerView.Adapter<ConfirmNightAdapter>NightViewHolder{
    private Context context;
    private ArrayList<Night> nights;

    public ConfirmNightAdapter(Context context, ArrayList<Night> dataSet) {
        this.context = context;
        this.nights = dataSet;
    }

    public static class NightViewHolder extends RecyclerView.ViewHolder {
        //public
    }


@Override
public int getItemCount() {
    return nights.size();
}

@Override
public ConfirmNightAdapter.NightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = (View) LayoutInflater.from(parent.getContext())
            .inflate(R.layout.night_cell, parent, false);
    NightViewHolder vh = new NightViewHolder(v);
    return vh;
}

@Override
public void onBindViewHolder(NightViewHolder holder, int position) {
   // holder.????.setText(nights.get(position).getName());
    //holder.ageView.setText(bulldogs.get(postion).getAge());
}
}