package com.bridgelabz.emppayrolljdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {

	
	 public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}

	    private List<EmployeePayrollData> employeepayrollList;
	    private EmployeePayrollDBService employeePayrollDBService;

	    public EmployeePayrollService() {
	        employeePayrollDBService = employeePayrollDBService.getInstance();
	    }

	    public EmployeePayrollService(List<EmployeePayrollData> employeepayrollList) {
	        this();
	        this.employeepayrollList = employeepayrollList;
	    }

	    public static void main(String[] args) {
	        ArrayList<EmployeePayrollData> employeepayrollList = new ArrayList<>();
	        EmployeePayrollService employeepayrollservice = new EmployeePayrollService(employeepayrollList);
	        Scanner consoleInputReader = new Scanner(System.in);
	        employeepayrollservice.readEmployeePayrollData(consoleInputReader);
	        employeepayrollservice.writeEmployeePayrollData(IOService.CONSOLE_IO);
	    }

	    private void readEmployeePayrollData(Scanner consoleInputReader) {
	        System.out.println("Enter Employee ID: ");
	        int id = consoleInputReader.nextInt();
	        System.out.println("Enter Employee Name: ");
	        String name = consoleInputReader.next();
	        System.out.println("Enter Employee Salary: ");
	        double salary = consoleInputReader.nextDouble();
	        employeepayrollList.add(new EmployeePayrollData(id, name, salary));
	    }

	    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService){
	        if (ioService.equals(IOService.DB_IO))
	            this.employeepayrollList = employeePayrollDBService.readData();
	        return employeepayrollList;
	    }

	    public List<EmployeePayrollData> readEmployeePayrollForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
	        if (ioService.equals(IOService.DB_IO))
	            return employeePayrollDBService.getEmployeePayrollForDateRange(startDate, endDate);
	        return null;
	    }

	    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
	        if (ioService.equals(IOService.DB_IO))
	            return employeePayrollDBService.getAverageSalaryByGender();
	        return null;
	    }

	    public boolean checkEmployeePayrollInSyncWithDB(String name) {
	        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
	        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	    }

	    public void updateEmployeeSalary(String name, double salary) {
	        int result = employeePayrollDBService.updateEmployeeData(name, salary);
	        if(result == 0) return;
	        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
	        if(employeePayrollData != null) employeePayrollData.salary = salary;
	    }

	    private EmployeePayrollData getEmployeePayrollData(String name) {
	        return this.employeepayrollList.stream()
	                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
	                .findFirst()
	                .orElse(null);
	    }

	    public void addEmployeeTOPayroll(String name, String gender, double salary, LocalDate startDate ) {
	        employeepayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name, gender, salary, startDate));
	    }


	    public void writeEmployeePayrollData(IOService ioService) {
	        if (ioService.equals(IOService.CONSOLE_IO))
	            System.out.println("\n Writting Employee payroll to Console\n" + employeepayrollList);
	        else if (ioService.equals(IOService.FILE_IO))
	            new EmployeePayrollFileIOService().writeData(employeepayrollList);
	    }

	    public void printData(IOService ioService) {
	        if (ioService.equals(IOService.FILE_IO))
	            new EmployeePayrollFileIOService().printData();
	    }

	    public long countEntries(IOService ioService) {
	        if (ioService.equals(IOService.FILE_IO))
	            return new EmployeePayrollFileIOService().countEntries();
	        return 0;
	    }
	}