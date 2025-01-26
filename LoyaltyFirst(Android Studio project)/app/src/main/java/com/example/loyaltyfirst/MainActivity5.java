package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
// Retrieving prize and redemption details
public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Intent intent=getIntent();
        String cid=intent.getStringExtra("cid");
        Spinner spinner = findViewById(R.id.spinner2);
        TextView prizeView = findViewById(R.id.textView11);
        TextView locView = findViewById(R.id.textView12);
        ArrayList<String> list = new ArrayList<String>();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid=" + cid;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response = s.split("\n")[7];
                String[] ids = response.split("#");
                for(int i=0; i<ids.length;i++) {
                    list.add(ids[i].split("#")[0]);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String pid = parent.getSelectedItem().toString();
                        String url = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid=" + pid +"&cid="+cid;
                        StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String content = s.split("\n")[7];
                                String description = content.split(",")[0];
                                String points = content.split(",")[1];
                                prizeView.setText("Prize Description:\n" + description + "\nPoints needed:\n" +points);
                                String[] rows = content.split("#");
                                String output = "     Redemption Date                                       Exchange Center\n-----------------------------------------------------------------------------------------------------\n";
                                for (int i = 0; i < rows.length && i < 5; i++){
                                    String[] col = rows[i].split(",");
                                    String date = col[2];
                                    String loc = col[3];
                                    output += "     " + date + "                                     " +loc +"\n";
                                }
                                locView.setText(output);
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