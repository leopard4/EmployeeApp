package com.leopard4.employeeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leopard4.employeeapp.model.Employee;
import com.leopard4.networkapp2.adapter.EmployeeAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    ArrayList<Employee> employeeList = new ArrayList<>();
    final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL+ "/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("EMPLOYEE_APP", response.toString());
                        // 객체로 받아온 JSONObject 를 data key로 접근해서 arrayList로 만든다
                        JSONArray data = response.optJSONArray("data");

                        Log.i("EMPLOYEE_APP", data.toString());

                        try {
                            for (int i = 0; i < data.length(); i++) {
                                Employee employee = new Employee(
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("employee_name"),
                                        data.getJSONObject(i).getInt("employee_salary"),
                                        data.getJSONObject(i).getInt("employee_age"),
                                        data.getJSONObject(i).getString("profile_image")
                                );
                                employeeList.add(employee);
                            }
                            adapter = new EmployeeAdapter(MainActivity.this, employeeList);
                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "파싱에러!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


    }

}