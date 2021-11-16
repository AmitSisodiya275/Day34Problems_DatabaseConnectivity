package com.bridgelab.dbconnectivity;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bridgelab.dbconnectivity.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest {

	@Test
	public void given3EmployeeDetailsWhenWrittenToTheFileShouldMatchTheEntries() {
		EmployeePayroll[] arrayOfEmpData = { new EmployeePayroll(1, "amit", 5000),
				new EmployeePayroll(2, "Sunanda", 30000),
				new EmployeePayroll(3, "Gayatri", 40000) };

		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmpData));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		assertEquals(3, entries);
	}

	@Test
	public void givenFileWhenReadingShouldMatchTheEmployeeCount() {
		EmployeePayrollService payrollService = new EmployeePayrollService();
		long entries = payrollService.readData(IOService.FILE_IO);
		assertEquals(3, entries);
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchTheCount() {
		EmployeePayrollService service = new EmployeePayrollService();
		List<EmployeePayroll> payrollList = service.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3, payrollList.size());
	}
}
