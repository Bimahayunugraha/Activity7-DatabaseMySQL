package com.example.remotedatabasea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditTeman extends AppCompatActivity {

    TextView tvId;
    EditText edNama, edTelpon;
    Button btnEdit;
    String id, nm, tlp, namaEd, telponEd;
    int success;

    private static String url_update = "http://10.0.2.2:8024/umyTI/updatetm.php";
    private static final String TAG = EditTeman.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        tvId = findViewById(R.id.tvId);
        edNama = findViewById(R.id.edtNama);
        edTelpon = findViewById(R.id.edtTelpon);
        btnEdit = findViewById(R.id.btnEdit);

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("key1");
        nm = bundle.getString("key2");
        tlp = bundle.getString("key3");

        tvId.setText("ID : " + id);
        edNama.setText(nm);
        edTelpon.setText(tlp);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });
    }

    public void EditData() {
        namaEd = edNama.getText().toString();
        telponEd = edTelpon.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Response : " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);success = jsonObject.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(EditTeman.this, "Sukses mengedit data", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(EditTeman.this, "Gagal", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }}
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.getMessage());
                Toast.makeText(EditTeman.this, "Gagal mengedit data", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("id", id);
                params.put("nama", namaEd);
                params.put("telpon", telponEd);

                return params;
            }
        };
        requestQueue.add(stringRequest);
        CallHomeActivity();
    }

    public void CallHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}