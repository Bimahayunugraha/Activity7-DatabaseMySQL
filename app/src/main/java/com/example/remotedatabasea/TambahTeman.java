package com.example.remotedatabasea;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {

    private EditText tNama, tTelpon;
    private Button btnSave;
    String nm, tlp;
    int success;

    private static String url_insert = "http://10.0.2.2:8024/umyTI/tambahtm.php";
    private static final String TAG = TambahTeman.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        tNama = (EditText) findViewById(R.id.tietNama);
        tTelpon = (EditText) findViewById(R.id.tietTelpon);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void simpanData() {
        if (tNama.getText().toString().equals("") || tTelpon.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
        } else {
            nm = tNama.getText().toString();
            tlp = tTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response : " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        success = jsonObject.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(TambahTeman.this, "Sukses menyimpan data", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(TambahTeman.this, "Gagal", Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }}
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error : " + error.getMessage());
                    Toast.makeText(TambahTeman.this, "Gagal menyimpan data", Toast.LENGTH_LONG).show();
                }

            }) {
                @Override
                protected Map<String,String> getParams() {
                    Map<String,String> params = new HashMap<>();

                    params.put("nama", nm);
                    params.put("telpon", tlp);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}