package com.leopard4.networkapp2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leopard4.employeeapp.model.Employee;
import com.leopard4.employeeapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    Context context;
    ArrayList<Employee> EmployeeList;

    DecimalFormat dc = new DecimalFormat("$" +"###,###,###");

    public EmployeeAdapter(Context context, ArrayList<Employee> EmployeeList) {
        this.context = context;
        this.EmployeeList = EmployeeList;
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
        Employee employee = EmployeeList.get(position);

        holder.txtName.setText(employee.name);
        holder.txtAge.setText("나이 : " + employee.age + "");
        holder.txtSalary.setText("연봉 : " +employee.salary + "");

    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtAge;
        TextView txtSalary;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSalary = itemView.findViewById(R.id.txtSalary);

        }
    }
}
