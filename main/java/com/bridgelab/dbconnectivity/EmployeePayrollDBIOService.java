package com.bridgelab.dbconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBIOService {

	private PreparedStatement EmployeePayrollPreparedStatement;

	private static EmployeePayrollDBIOService employeePayrollDBIOService;

	private EmployeePayrollDBIOService() {

	}

	public static EmployeePayrollDBIOService getInstance() {
		if (employeePayrollDBIOService == null) {
			employeePayrollDBIOService = new EmployeePayrollDBIOService();
		}
		return employeePayrollDBIOService;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String username = "root";
		String password = "Amit@#$987";

		Connection connection = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);

		return connection;
	}

	public List<EmployeePayroll> readData() throws ConnectivityIssueException {
		String sql = "select * from employee_payroll;";
		List<EmployeePayroll> payrollList = new ArrayList<>();

		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			payrollList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectivityIssueException("There is an Error with the SQL");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return payrollList;
	}

	public int updateEmployeeSalary(String name, double salary) {
		return this.updateEmployeeSalaryUsingStatement(name, salary);
	}

	private int updateEmployeeSalaryUsingStatement(String name, double salary) {
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmployeePayroll> getEmployeePayrollData(String name) {
		List<EmployeePayroll> employeePayrollData = null;
		if (this.EmployeePayrollPreparedStatement == null) {
			this.preparedStatementForEmployeePayroll();
		}
		try {
			EmployeePayrollPreparedStatement.setString(1, name);
			ResultSet resultSet = EmployeePayrollPreparedStatement.executeQuery();
			employeePayrollData = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollData;
	}

	private List<EmployeePayroll> getEmployeePayrollData(ResultSet resultSet) throws SQLException {
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			double salary = resultSet.getDouble("salary");
			LocalDate date = resultSet.getDate("start_date").toLocalDate();
			employeePayrollList.add(new EmployeePayroll(id, name, salary, date));
		}
		return employeePayrollList;
	}

	private void preparedStatementForEmployeePayroll() {
		String sql = "select * from employee_payroll where name = ?";
		try {
			Connection connection = this.getConnection();
			EmployeePayrollPreparedStatement = connection.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
