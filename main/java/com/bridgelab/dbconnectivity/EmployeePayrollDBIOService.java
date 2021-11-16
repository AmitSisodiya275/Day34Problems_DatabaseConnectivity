package com.bridgelab.dbconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBIOService {

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

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate date = resultSet.getDate("start_date").toLocalDate();
				payrollList.add(new EmployeePayroll(id, name, salary, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectivityIssueException("There is an Error with the SQL");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return payrollList;
	}

}
