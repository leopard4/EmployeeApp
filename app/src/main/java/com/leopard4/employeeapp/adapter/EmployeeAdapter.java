package com.leopard4.employeeapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.leopard4.employeeapp.EditActivity;
import com.leopard4.employeeapp.MainActivity;
import com.leopard4.employeeapp.model.Employee;
import com.leopard4.employeeapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    Context context;
    ArrayList<Employee> employeeList;
    DecimalFormat dc = new DecimalFormat("###,###,###");

    int deleteIndex;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_row, parent, false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
        // 뷰에 데이터를 저장한다.
        Employee employee = employeeList.get(position);

        // todo 데이터 가공해서 셋팅
        holder.txtName.setText(employee.name);
        holder.txtAge.setText("나이 : " + employee.age + "세");
        // txtsalary에 $와 , 를 넣어주기 위해 DecimalFormat을 사용한다
        holder.txtSalary.setText("연봉 : $" + dc.format(employee.salary));

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtAge;
        TextView txtSalary;
        ImageView imgDelete;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            // 클릭 이벤트를 만들어 준다.
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1) 인텐트에 유저가 어떤 행을 눌렀는지 파악하여
                    int index = getAdapterPosition(); // 어떤 행을 눌렀는지 인덱스로 알려준다.
                    Employee employee = employeeList.get(index); // 해당 행의 데이터를 가져온다.

                    // 2) 수정 액티비티를 띄운다.
                    //    어떤 액티비티가 어떤 액티비티를 띄운다!! => 인텐트에 있어야 한다.
                    // (새로운 액티비티에 위의 정보를 넘겨서 실행한다.)
                    Intent intent = new Intent(context, EditActivity.class);

                    // 직렬화해서 한번에 넘겨준다
                    intent.putExtra("employee", employee);
                    // 몇번째 데이터를 수정한건지 확인을위한 인덱스도 넘겨준다.
                    intent.putExtra("index", index);

                    ((MainActivity)context).launcher.launch(intent); // 매인액티비티로 캐스팅
                    // 매인액티비티의 퍼블릭멤버변수만 사용가능
                }
            });

            // 삭제 이벤트를 만들어 준다.
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 0) 어느 주소록을 삭제할것인지
                    //    삭제할 주소록을 가져온다 (어떤 행을 눌렀는지 파악한다.)
                    deleteIndex = getAdapterPosition();

                    // 1) 알러트 다이얼로그 나온다.
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제");
                    builder.setMessage("정말 삭제하시겠습니까?");
                    // 3) 알러트 다이얼로그에서 No 눌렀을때
                    //    아무것도 안한다.
                    builder.setNegativeButton("No", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 2) 알러트 다이얼로그에서 Yes 눌렀을때

                            // 알러트 다이얼로그는 액티비티가 아니므로
                            // 메인액티비티의 onResume 함수가 실행안된다.
                            // 따라서 화면 갱신이 안된다.

                            // 메모리에 저장된 데이터도 삭제한다.
                            employeeList.remove(deleteIndex);
                            // 데이터가 변경되었으니, 화면 갱신하라고 어댑터의 함수호출 (상속받은기능)
                            notifyDataSetChanged();
                        }
                    });


                    builder.show();
                }


            });

        }
    }
}
