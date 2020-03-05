package com.example.Employee;


import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ManageEmployee {
	 private static SessionFactory factory;
	 
	    public static void main(String[] args) {
	       
	        factory = HibernateUtil.getSessionFactory();
	 
	      
	        ManageEmployee manageEmployee = new ManageEmployee();
	 
	       
	        Integer empID1 = manageEmployee.addEmployee("hoa", "Bishop", 4000);
	        Integer empID2 = manageEmployee.addEmployee("my", "Ali", 5000);
	        Integer empID3 = manageEmployee.addEmployee("John", "Vector", 10000);
	 
	        
	        System.out.println("List down all the employees:");
	        manageEmployee.listEmployees();
	 
	      
	        manageEmployee.updateEmployee(empID1, 3000);
	 
	       
	        manageEmployee.deleteEmployee(empID2);
	 
	        
	        System.out.println("List down new list of the employees:");
	        manageEmployee.listEmployees();
	    }
	 
	  
	    public Integer addEmployee(String fname, String lname, int salary) {
	        Session session = factory.openSession();
	        Transaction tx = null;
	        Integer employeeID = null;
	        try {
	            tx = session.beginTransaction();
	            Employee employee = new Employee(fname, lname, salary);
	            employeeID = (Integer) session.save(employee);
	            tx.commit();
	        } catch (HibernateException e) {
	            if (tx != null)
	                tx.rollback();
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return employeeID;
	    }
	 
	    
	    public void listEmployees() {
	        Session session = factory.openSession();
	        try {
	            List employees = session.createQuery("FROM Employee").list();
	            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
	                Employee employee = (Employee) iterator.next();
	                System.out.print("First Name: " + employee.getFirstName());
	                System.out.print("  Last Name: " + employee.getLastName());
	                System.out.println("  Salary: " + employee.getSalary());
	            }
	        } catch (HibernateException e) {
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	    }
	 
	  
	    public void updateEmployee(Integer EmployeeID, int salary) {
	        Session session = factory.openSession();
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();
	            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
	            employee.setSalary(salary);
	            session.update(employee);
	            tx.commit();
	        } catch (HibernateException e) {
	            if (tx != null)
	                tx.rollback();
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	    }
	 
	    // Method to DELETE an employee from the records
	    public void deleteEmployee(Integer EmployeeID) {
	        Session session = factory.openSession();
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();
	            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
	            session.delete(employee);
	            tx.commit();
	        } catch (HibernateException e) {
	            if (tx != null)
	                tx.rollback();
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	    }

}
