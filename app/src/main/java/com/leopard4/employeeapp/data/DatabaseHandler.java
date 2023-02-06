package com.leopard4.employeeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.leopard4.employeeapp.model.Employee;
import com.leopard4.employeeapp.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DB_NAME, null, Util.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성
        String CREATE_CONTACT_TABLE = "create table employee ( id integer primary key autoincrement, name text, salary text, age text, image text )";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE); // SQL문 실행

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존에 테이블을 삭제하고, 새 테이블을 다시 만든다.
        String DROP_TABLE = "drop table employee";

        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);
    }
    // 이제부터는 우리가 앱 동작시키는데 필요한
    // CRUD 관련된 SQL문이 들어간,
    // 메소드를 만들면 된다.

    // 1. 연락처 추가하는 메소드(함수) c (create)
    public void addEmployee(Employee employee) {
        // 1. 데이터베이스를 가져온다 (SQLiteDatabase 객체를 만든다.)
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. 저장가능한 형식으로 만들어준다 (ContentValues 객체를 만든다.)
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, employee.name);
        values.put(Util.KEY_SALARY, employee.salary);
        values.put(Util.KEY_AGE, employee.age);
        values.put(Util.KEY_IMAGE, employee.image);

        // 3. insert() 메소드를 사용해서 데이터를 추가한다.
        db.insert(Util.TABLE_NAME, null, values);

        // 4. 사용이 끝나면 db 객체를 닫는다.
        db.close();
    }

    // 2. 저장된 연락처를 모두 가져오는 메소드 r (read)
    public ArrayList<Employee> getAllEmployee() {

        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. 쿼리문 만든다.
        String query = "select * from " + Util.TABLE_NAME;

        // 3. 쿼리문을 실행하여, 커서 객체로 받는다.
        // 원문을 실행할때는 rawQuery() 메소드를 사용한다
        // 두번째 파라미터는 where 같은것
        Cursor cursor = db.rawQuery(query, null);

        // 3-1 여러 데이터를 저장할 어레이리스트를 만든다.
        ArrayList<Employee> employeeList = new ArrayList<>();

        // 4. 커서 에서 데이터를 뽑아낸다.
        if(cursor.moveToFirst()){                // 첫번째 데이터로 이동해서 데이터를 가져온다.(행)
            do{
                int id = cursor.getInt(0);  // 컬럼의 인덱스
                String name = cursor.getString(1);
                int salary = cursor.getInt(2);
                int age = cursor.getInt(3);
                String image = cursor.getString(4);

                Log.i("DB", "id : " + id + ", name : " + name + ", salary : " + salary + ", age : " + age + ", image : " + image);

                // 이 데이터를, 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야하 한다!!!
                // 그래서, 이 데이터를, Contact 객체로 만들어서
                Employee employee = new Employee(name, salary,age);
                // 이 Contact 객체를, ArrayList에 담아둔다.
                employeeList.add(0, employee);


            }while(cursor.moveToNext()); // 다음 데이터로 이동해서 반복
        }

        // 5. db닫기
        db.close();

        // 6. DB에서 읽어온 데이터를 리턴한다.
        return employeeList;
    }

    // 3.업데이트 메소드 u (update)
    public void updateContact(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update employee " +
                "set name = ?, salary = ?, age = ?, image = ?" +
                "where id = ? ";
        db.execSQL(query, new String[]{employee.name, String.valueOf(employee.salary), String.valueOf(employee.age),employee.image, employee.id + ""});     // flask의 record와 비슷하다.

        db.close();
    }

    // 4. 연락처 삭제하는 메소드 D (delete)
    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();    // 쓰기 가능한 객체를 가져온다.

        String query = "delete from employee " +
                "where id = ?";
        String[] args = new String[]{employee.id + ""};  // 파라미터로 넘겨줄 값들을 배열로 만든다.

        db.execSQL(query, args);    // 쿼리문 실행

        db.close(); // db 닫기
    }
}
