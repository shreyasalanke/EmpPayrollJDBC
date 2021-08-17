package com.bridgelabz.emppayrolljdbc;

import java.util.List;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollServiceTest {

	 @Test
	    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEnteries(){
	        EmployeePayrollData[] arrayOfEmps = {
	                new EmployeePayrollData(1, "Sharan", 100000.0),
	                new EmployeePayrollData(2, "Shreya", 200000.0),
	                new EmployeePayrollData(3, "Shivani", 300000.0)
	        };
	        EmployeePayrollService employeePayrollService;
	        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
	        employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
	        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
	        long enteries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
	        Assert.assertEquals(3, enteries);
	    }

	    @Test
	    public void givenEmployeePayrollDBWhenRetrivedShouldMatchEmployeeCount() {
	        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
	        Assert.assertEquals(3, employeePayrollData.size());
	    }

	    @Test
	    public void givenNewSalaryForEmployeeWhenUpdatedShouldSyncWithDB() {
	        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
	        employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
	        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
	        Assert.assertTrue(result);
	    }

	}