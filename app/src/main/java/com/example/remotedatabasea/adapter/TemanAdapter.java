package com.example.remotedatabasea.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.remotedatabasea.EditTeman;
import com.example.remotedatabasea.MainActivity;
import com.example.remotedatabasea.R;
import com.example.remotedatabasea.app.AppController;
import com.example.remotedatabasea.database.Teman;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String id, nm, tlp;

        id = listData.get(position).getId();
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

                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Bundle bundle = new Bundle();
                                bundle.putString("key1", id);
                                bundle.putString("key2", nm);
                                bundle.putString("key3", tlp);

                                Intent intent = new Intent(v.getContext(), EditTeman.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.delete:
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                                alertDialog.setTitle("Ingin menghapus " +nm+ " ?");
                                alertDialog.setMessage("Klik 'Ya' untuk menghapus");
                                alertDialog.setCancelable(false);
                                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Hapus(id);
                                                Toast.makeText(v.getContext(),"Data " +id+ " telah dihapus", Toast.LENGTH_LONG).show();
                                                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent1);
                                            }
                                        });
                                alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog1 = alertDialog.create();
                                alertDialog1.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

    }

    private void Hapus(final String idx) {
        String url_delete = "http://10.0.2.2:8024/umyTI/deletetm.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCCESS = "success";
        final int[] success = new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Response : " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success [0] = jsonObject.getInt(TAG_SUCCESS);
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.getMessage());
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("id", idx);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
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
