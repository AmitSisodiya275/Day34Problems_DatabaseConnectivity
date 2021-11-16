package com.bridgelab.dbconnectivity;

import java.time.LocalDate;

public class EmployeePayroll {

	int id;
	String name;
	double salary;
	LocalDate date;
	
	public EmployeePayroll(int id , String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	
	public EmployeePayroll(int id, String name, double salary, LocalDate date) {
		this(id, name, salary);
		this.date = date;
	}
	
	public String toString() {
		return "Id : "+id+ ", Name : "+name+", Salary : "+salary + ", Joining Date : "+date;
	}
}
