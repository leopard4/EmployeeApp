package com.leopard4.employeeapp.model;

public class Employee {
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
    public Employee(int id, String name, int salary, int age, String image) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.image = image;

    }

    public Employee(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}

