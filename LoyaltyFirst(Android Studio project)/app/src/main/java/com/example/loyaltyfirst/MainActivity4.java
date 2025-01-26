package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
// Retrieving Transaction details
public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent=getIntent();
        String cid=intent.getStringExtra("cid");
        TextView dateView = findViewById(R.id.textView7);
        TextView pointsView = findViewById(R.id.textView8);
        TextView outView = findViewById(R.id.textView9);
        Typeface monospace = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
        outView.setTypeface(monospace);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Splitting the data from the response
                String response = s.split("\n")[8];
                String[] ids = response.split("#");
                for(int i=0; i<ids.length;i++) {
                    list.add(ids[i].split(",")[0]);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tref = parent.getSelectedItem().toString();
                        //Passing tref to the URL to fetch transaction details
                        String url = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref=" + tref;
                        StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            //Transaction Details Response
                            public void onResponse(String s) {
                                String header = "Prod.name      Quantity     Points\n";
                                String content = s.split("\n")[7];
                                String[] rows = content.split("#");
                                dateView.setText(rows[0].split(",")[0]);
                                pointsView.setText(rows[0].split(",")[1] + " Points");
                                StringBuilder output = new StringBuilder(header);
                                //Itreating through the transation details to display
                                for (int i = 0; i < rows.length && i < 20; i++) {
                                    String[] col = rows[i].split(",");
                                    String formattedRow = String.format("%-15s %7s %10s\n", col[2], col[4], col[3]);
                                    output.append(formattedRow);
                                }
                                outView.setText(output.toString());

                            }
                        },null);
                        queue.add(request2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        },null);

        queue.add(request1);

    }
}