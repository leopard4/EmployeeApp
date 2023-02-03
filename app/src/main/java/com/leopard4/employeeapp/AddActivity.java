package com.leopard4.employeeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leopard4.employeeapp.model.Employee;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editAge;
    EditText editSalary;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editSalary = findViewById(R.id.editSalary);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String name = editName.getText().toString().trim();
                    String age = editAge.getText().toString().trim();
                    // age를 int로 바꿔야 한다.
                    age = String.valueOf(Integer.parseInt(age));
                    String salary = editSalary.getText().toString().trim();
                    salary = String.valueOf(Integer.parseInt(salary));

                    // 이름, 나이, 급여가 모두 있어야 한다!
                    if (name.isEmpty() || age.isEmpty() || salary.isEmpty()) {
                        Toast.makeText(AddActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 묶어서 처리할 Employee 클래스를 하나 만든다.
                    Employee employee = new Employee(name, age, salary);

                    // DB에 저장한다. // 힙에있는건 날라가므로
                    DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                    db.addEmployee(employee);

                    // 유저한테 잘 저장되었다고, 알려주고
                    // 액티비티 종료
                    finish();
            }
        });
    }
}