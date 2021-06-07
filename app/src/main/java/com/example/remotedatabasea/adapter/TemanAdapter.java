package com.example.remotedatabasea.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.remotedatabasea.R;
import com.example.remotedatabasea.database.Teman;

import java.util.ArrayList;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm, tlp;

        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();

        holder.tvnama.setTextColor(Color.YELLOW);
        holder.tvnama.setTextSize(20);
        holder.tvnama.setText(nm);
        holder.tvtelpon.setTextColor(Color.WHITE);
        holder.tvtelpon.setTextSize(15);
        holder.tvtelpon.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder{
        private CardView cardku;
        private TextView tvnama, tvtelpon;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            tvnama = (TextView) view.findViewById(R.id.textNama);
            tvtelpon = (TextView) view.findViewById(R.id.textTelpon);

        }
    }

}
