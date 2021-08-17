package com.bridgelabz.emppayrolljdbc;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import employeepayrolljdbc.EmployeePayrollService.IOService;
import junit.framework.Assert;

public class EmployeePayrollServiceTest
{
	
	@Test
	public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries()
	{
		EmployeePayrollData[] arrayOfEmp = 
			{
				new EmployeePayrollData(1,"Sharan",100000.0),
				new EmployeePayrollData(2, "Shreya",200000.0),
				new EmployeePayrollData(3, "Shivani",300000.0)
		};
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmp));
		employeePayrollService.writeEmployeeData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		List<EmployeePayrollData> employeeList = employeePayrollService.readData(IOService.FILE_IO);
		System.out.println(employeeList);
		assertEquals(3, entries);
	}
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readData(IOService.DB_IO);
		assertEquals(5, employeePayrollData.size());
	}
}
