package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//Welcome page after successful login
public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        String cid=intent.getStringExtra("cid");
        TextView nameView = findViewById(R.id.textView2);
        TextView pointsView = findViewById(R.id.textView3);
        ImageView imageView = findViewById(R.id.imageView);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid=" + cid;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String name = s.split("\n")[10].split(" ")[1].split("</p>")[0];
                String points = s.split("\n")[12].split(" ")[2].split("</p>")[0];
                nameView.setText(name);
                pointsView.setText(points);
            }
        },null);
        queue.add(request1);
        String url2 = "http://10.0.2.2:8080/loyaltyfirst/images/"+cid+".jpeg";
        ImageRequest request2 = new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        },160,160,null,null);
        queue.add(request2);
        // button 5 ：Exit
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //button 1 ：transactions
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });
        //button 2 ：transaction details
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity4.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });
        //button 2 ：Prize details
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity5.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });
        //button 2 ：Redemption details
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity6.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });
    }

}