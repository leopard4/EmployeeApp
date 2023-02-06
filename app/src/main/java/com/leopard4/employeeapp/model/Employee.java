package com.leopard4.employeeapp.model;

import java.io.Serializable;

public class Employee implements Serializable {
    //        "id": 3,
//        "employee_name": "Ashton Cox",
//        "employee_salary": 86000,
//        "employee_age": 66,
//        "profile_image": "",
    public int id;
    public String name;
    public int salary;
    public int age;
    public String image;

    public Employee() {
    }

    public Employee(String name, int salary, int age) {
        id = -1;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }
    public Employee(int id, String name, int salary, int age, String image) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.image = image;

    }
}

