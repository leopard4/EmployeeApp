package com.leopard4.employeeapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leopard4.employeeapp.adapter.EmployeeAdapter;
import com.leopard4.employeeapp.model.Employee;




import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    ArrayList<Employee> employeeList = new ArrayList<>();
    ProgressBar progressBar;
    final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com";

    // 내가 실행한 액티비티로부터, 데이터를 다시 받아오는 경우에 작성하는 코드
    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // 액티비티를 실행한후, 이 액티비티로
                    // 돌아왔을때 할 일을 여기에 작성

                    // AddActivity가 넘겨준
                    // Employee 객체를 받아서 원상복구 시키고
                    // 리스트에 넣어주고
                    // 화면 갱신 해준다.
                    if (result.getResultCode() == AddActivity.SAVE) {
                        Employee employee = (Employee) result.getData().getSerializableExtra("employee");
                        employeeList.add(employee);
                        adapter.notifyDataSetChanged();
                    } else if (result.getResultCode() == EditActivity.EDIT) {
                        Employee employee = (Employee) result.getData().getSerializableExtra("employee");
                        // 몇번째 데이터를 수정한건지 확인을위한 코드작성
                        int index = result.getData().getIntExtra("index", -1);
                        employeeList.set(index, employee);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 액션바의 타이틀 변경법
        getSupportActionBar().setTitle("직원리스트");
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
        fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 액티비티 띄운다.
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 액티비티 띄운다.
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);
            }
        });

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

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray data = response.optJSONArray("data"); // 또는
    //                      JSONArray data = response.getJSONArray("data");

                            Log.i("EMPLOYEE_APP", data.toString());

                            for (int i = 0; i < data.length(); i++) {
                                // JSONArray 에 들어있는 직원 정보를 가져와서,
                                // Employee 클래스로 만든다.
                                Employee employee = new Employee(
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("employee_name"),
                                        data.getJSONObject(i).getInt("employee_salary"),
                                        data.getJSONObject(i).getInt("employee_age"),
                                        data.getJSONObject(i).getString("profile_image")
                                );
                                employeeList.add(employee);
                                adapter = new EmployeeAdapter(MainActivity.this, employeeList);
                                // 리사이클러뷰에 셋팅
                                recyclerView.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
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

        progressBar.setVisibility(View.VISIBLE);
        queue.add(request);


    }
    // 액션바의 메뉴는, 전용 함수가 있다.
    // 이 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // 액션바에 메뉴가 나오도록 설정한다.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    // 액션바의 메뉴를 탭했을때, 실행되는 함수가 있다.
    // 이 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 메뉴의 아이디를 확인해서, 각각의 메뉴를 구분한다.
        int itemId = item.getItemId();

        if(itemId == R.id.menuAdd){
            // AddActivity 실행하는 코드
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            launcher.launch(intent);
        } else if(itemId == R.id.menuAbout){
            // AboutAcitivity 실행하는 코드
        }

        return MainActivity.super.onOptionsItemSelected(item);
    }
}