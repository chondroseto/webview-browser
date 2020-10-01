package com.xela.browser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xela.browser.R;
import com.xela.browser.model.HistoryData;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private ArrayList<HistoryData> dataRecycler;

    public HistoryAdapter(ArrayList<HistoryData> dataRecycler) {
        this.dataRecycler = dataRecycler;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_history_recview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.no.setText(dataRecycler.get(position).getNo());
        holder.link_btn.setText(dataRecycler.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return dataRecycler.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView no;
        Button link_btn;

        public MyViewHolder(View itemView){
            super(itemView);
            no = itemView.findViewById(R.id.number);
            link_btn = itemView.findViewById(R.id.link);
        }
    }
}
