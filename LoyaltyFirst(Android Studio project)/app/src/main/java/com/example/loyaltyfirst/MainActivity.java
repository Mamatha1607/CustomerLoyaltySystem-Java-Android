package com.example.loyaltyfirst;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText textUser = findViewById(R.id.editTextUname);
        EditText textPwd = findViewById(R.id.editTextPassword);
        Button button = findViewById(R.id.buttonLogin);
        RequestQueue queue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUser.getText().toString();
                String pw = textPwd.getText().toString();
                String url =  "http://10.0.2.2:8080/loyaltyfirst/login?user="+username+"&pass="+pw;
                StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] s2 = s.split(":");

                        if (s2[0].equals("Yes")){
                            String cid = s2[1];
                            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                            intent.putExtra("cid",cid);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invalid password or username", Toast.LENGTH_SHORT).show();
                        }



                    }
                },null);
                queue.add(request1);
            }
        });

    }
}