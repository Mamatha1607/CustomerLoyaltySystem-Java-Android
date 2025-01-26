package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
// Retrieving Transactions
public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent=getIntent();
        String cid=intent.getStringExtra("cid");
        TextView tranView = findViewById(R.id.transactions);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Splitting the data from the response
                String content = s.split("\n")[8];
                String[] rows = content.trim().split("#");
                String output = "TXN Ref                \tDate                  \tPoints                    \tTotal\n-----------------------------------------------------------------------------------------------------\n";
                //Iterating through the SQL data
                for (int i = 0; i < rows.length && i < 20; i++) {
                    String[] col = rows[i].split(",");
                    String ref = col[0];
                    String date = col[1];
                    String points = col[2];
                    int t = 3 - points.length();
                    for (int j = 0; j < t; j++) {
                        points += "  " ;
                    }
                    String total = "$" + col[3];
                    output += ref + "           " + date + "              " + points + "                  " + total +'\n';
                }
                tranView.setText(output);
            }
        },null);
        queue.add(request1);
    }
}