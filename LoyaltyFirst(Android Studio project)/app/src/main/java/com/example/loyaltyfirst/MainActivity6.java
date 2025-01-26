package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Intent intent=getIntent();
        String cid=intent.getStringExtra("cid");
        Spinner spinner = findViewById(R.id.spinner3);
        TextView fView = findViewById(R.id.textView14);
        ArrayList<String> list = new ArrayList<String>();
        Button button = findViewById(R.id.button);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response = s.split("\n")[8];
                String[] ids = response.split("#");
                for(int i=0; i<ids.length;i++) {
                    list.add(ids[i].split(",")[0]);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tref = parent.getSelectedItem().toString();

                        String url = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid=" +cid + "&tref=" + tref;
                        StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String content = s.split("\n")[7];
                                String fid = content.split("#")[0].split(",")[0];
                                String percent = content.split("#")[0].split(",")[1];
                                String total = content.split("#")[0].split(",")[2];
                                int points =Integer.parseInt(percent)*Integer.parseInt(total)/100;
                                fView.setText("TXN Points: " + total +"\n\nFamily ID: " + fid +"\n\nFamily Percent: " + percent +"  ");
                                String url = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid=" + fid +"&cid=" + cid + "&npoints=" + points;
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StringRequest request3 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                if (s.contains("successfully")){
                                                    Toast.makeText(MainActivity6.this, points+" points added to the members of family ID of "+fid, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }, null);
                                        queue.add(request3);
                                    }
                                });
                            }
                        },null);
                        queue.add(request2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }, null);
        queue.add(request1);

    }
}