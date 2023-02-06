package com.leopard4.employeeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leopard4.employeeapp.data.DatabaseHandler;
import com.leopard4.employeeapp.model.Employee;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editAge;
    EditText editSalary;
    Button btnSave;

    public static final int SAVE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setTitle("직원 추가");
        // 아래 코드는 돌아갈수 있는 화살표만 화면에 보여준다. (동작은안된다는것)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editSalary = findViewById(R.id.editSalary);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String name = editName.getText().toString().trim();
                    int age = Integer.parseInt(editAge.getText().toString().trim());
                    int salary = Integer.parseInt(editSalary.getText().toString().trim());

                    // 이름, 나이, 급여가 모두 있어야 한다!
                    if (name.isEmpty() || age == 0 || salary == 0) {
                        Toast.makeText(AddActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 묶어서 처리할 Employee 클래스를 하나 만든다.
                    Employee employee = new Employee(name, salary, age);

                    // 메인 액티비티로 돌아가야 한다.
                    // employee 객체를 메인엑티비티에 보내준다.

                    Intent intent = new Intent();
                    intent.putExtra("employee", employee);
                    setResult(SAVE, intent);

                    // 이 액티비티는 할일 다 했으니, 종료하면 된다.
                    finish();

            }
        });
    }
    // 액션바의 돌아가는 화살표를 눌렀을때의 이벤트를 처리하는
    // 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onSupportNavigateUp() {
        // 이 액티비티는 할일 다 했으니, 종료하면 된다.
        finish();
        return super.onSupportNavigateUp();
    }
}