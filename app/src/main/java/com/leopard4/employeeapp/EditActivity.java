package com.leopard4.employeeapp;

import static com.leopard4.employeeapp.AddActivity.SAVE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leopard4.employeeapp.model.Employee;

public class EditActivity extends AppCompatActivity {

    EditText editSalary;
    EditText editAge;
    Button btnSave;

    Employee employee;
    int index;


    // 메인액티비티에서 확인할 용도로 상수를 만든다.
    // 수정을한건지 뭐한건지 구분하기 위해서
    public static final int EDIT = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setTitle("직원 수정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        employee = (Employee) getIntent().getSerializableExtra("employee");
        index = getIntent().getIntExtra("index", -1);
        editSalary.setText(employee.salary+"");
        editAge.setText(employee.age+"");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSalary = editSalary.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                // 이름과 전화번호가 모두 있어야 한다!
                if (strSalary.isEmpty() || strAge.isEmpty()) {
                    Toast.makeText(EditActivity.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 숫자로 변환
                // 위에서 받아온 객체에 셋팅해준다.
                int salary = Integer.valueOf(strSalary).intValue();
                int age = Integer.valueOf(strAge).intValue();

                // 묶어서 처리할 클래스를 하나 만든다.
                employee.salary = salary;
                employee.age = age;

                Intent intent = new Intent();
                intent.putExtra("employee", employee);
                intent.putExtra("index", index);

                setResult(EDIT, intent);


                // 유저한테 잘 저장되었다고, 알려주고
                Toast.makeText(EditActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();

                // 액티비티 종료, 메인 액티비티로 돌아간다.(메인액티비티는 뒤에 숨어있었으므로)
                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}