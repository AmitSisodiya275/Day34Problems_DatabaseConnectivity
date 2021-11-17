package com.bridgelab.dbconnectivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOError;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bridgelab.dbconnectivity.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest {

	@Test
	public void given3EmployeeDetailsWhenWrittenToTheFileShouldMatchTheEntries() {
		EmployeePayroll[] arrayOfEmpData = { new EmployeePayroll(1, "amit", 5000),
				new EmployeePayroll(2, "Sunanda", 30000), new EmployeePayroll(3, "Gayatri", 40000) };

		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmpData));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		assertEquals(3, entries);
	}

	@Test
	public void givenFileWhenReadingShouldMatchTheEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		long entries = employeePayrollService.readData(IOService.FILE_IO);
		assertEquals(3, entries);
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchTheCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayroll> payrollList = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3, payrollList.size());
	}

	@Test
	public void givenNewSalary_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayroll> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Terissa", 3000000.0);
		boolean result = employeePayrollService.checkPayrollObjectDataIsSyncWithDB("Terissa");
		assertTrue(result);
	}

	@Test
	public void givenDateRange_whenFetched_shouldReturnEmployeeWithinThatRange() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		LocalDate startDate = LocalDate.of(2018, 1, 1);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayroll> employeePayrollData = employeePayrollService
				.getEmployeePayrollDataFromDateRange(IOService.DB_IO, startDate, endDate);
		assertEquals(3, employeePayrollData.size());
	}
}
