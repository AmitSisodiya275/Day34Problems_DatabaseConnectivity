package com.bridgelab.dbconnectivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	Scanner scanner = new Scanner(System.in);
	List<EmployeePayroll> employeePayrollList = new ArrayList<>();

	public EmployeePayrollService() {
	}

	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	public static void main(String[] args) {

		EmployeePayrollService service = new EmployeePayrollService();
		service.readEmployeePayrollData();
		service.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}

	public void readEmployeePayrollData() {
		System.out.println("Enter Employee ID : ");
		int id = scanner.nextInt();
		System.out.println("Enter Employee Name : ");
		String name = scanner.next();
		System.out.println("Enter Employee Salary : ");
		double salary = scanner.nextDouble();
		LocalDate date = LocalDate.of(2011, 5, 4);
		System.out.println("Details Added!");
		employeePayrollList.add(new EmployeePayroll(id, name, salary, date));
	}

	public void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO)) {
			System.out.println("Writing Employee Payroll Data to the console : " + employeePayrollList);
		} else if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().writeEmployeePayrollData(employeePayrollList);
		}
	}

	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().printData();
		}
	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayrollFileIOService().countEntries();
		}
		return 0;
	}

	public long readData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayrollFileIOService().readData().size();
		}
		return 0;
	}

	public List<EmployeePayroll> readEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			try {
				this.employeePayrollList = new EmployeePayrollDBIOService().readData();
			} catch (ConnectivityIssueException e) {
				e.printStackTrace();
			}
		}
		return this.employeePayrollList;
	}

}
