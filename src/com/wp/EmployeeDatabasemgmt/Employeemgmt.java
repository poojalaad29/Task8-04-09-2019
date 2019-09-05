package com.wp.EmployeeDatabasemgmt;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Employeemgmt{
	static Scanner sc=new Scanner(System.in);
	static int eno; static String ename;static int salary;static String desig;static String dept;
	
	public static void main(String[] args) {
		try {
			while (true) {
			System.out.println("Select opeation from menu");		
			System.out.println("1,Insert Employee");
			System.out.println("2:View All Employee");
			System.out.println("3:Delete Employee");
			System.out.println("4:Clear Data");
			System.out.println("5:Search Employee");
			System.out.println("6:Change Salary");
			System.out.println("7:Show Employee dept wise");
			System.out.println("8:View Employees in sorted form");
			System.out.println("9:Exit");
			
			int choice = sc.nextInt();

			switch(choice)
			{
				case 1:InsertEmp();
				       break;
				case 2:ViewAll();
				        break;
				case 3:Delete();
				       break;
				case 4:ClearData();
				       break;
				case 5:Search();
				       break;
				case 6:ChangeSalary();
				        break;
				case 7: DeptwiseEmp();
				        break;
				case 8: viewSortedEmployee();
				         break;
				case 9: System.exit(1);
			  }
			}
			} catch (Exception e) {
			e.printStackTrace();
			} finally {
			sc.close();
			 }
	}
		
	
	public static  void InsertEmp() throws Exception  {
		System.out.println("Please Enter employee details: ");
	    Scanner sc=new Scanner(System.in);
	    System.out.println("Enter Employee Name");
	    String ename=sc.next();
	    System.out.println("Enter Employee salary");
	    int sal=sc.nextInt();
	    System.out.println("Enter Employee Department");
	    String dept=sc.next();
	    System.out.println("Enter Employee Designation");
	    String desig=sc.next();
	    Connection con;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Pooja", "abcd1234");
		System.out.println("driver load");
		 //String qr="insert into employee(Ename,Salary,Department,Designation) Values('"+ename+"','"+sal+"','"+dept+"','"+desig+"')";
		 CallableStatement cs =con.prepareCall("{Call Insert_Into_Empl(?,?,?,?,?)}");
		 cs.setInt(1, eno);
		 cs.setString(2, ename);
		 cs.setString(3, dept);
		 cs.setInt(4,sal);
		 cs.setString(5, desig);
		 cs.executeUpdate();
		 System.out.println("Inserted");
		 System.out.println("-------------------------------------------------------------");
		 } catch (Exception e) {
			e.printStackTrace();
		}
	}
   
   public static  void ViewAll() {		
		try {
			 Class.forName("com.mysql.jdbc.Driver");
		      Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
			String qr="Select Eno,Ename,Salary,Department,Designation from employee";
			PreparedStatement ps = con.prepareStatement(qr);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.print(" Employee number is "+rs.getInt(1));
				System.out.print(" Employee name is "+rs.getString(2));
				System.out.print(" Employee salary is "+rs.getInt(3));
				System.out.print(" Employee Department is "+rs.getString(4));
				System.out.println(" Employee Designation is "+rs.getString(5));
                
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
   public static  void Delete() throws SQLException {
  try {
	     Class.forName("com.mysql.jdbc.Driver");
          Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
	   System.out.println("Enter employee number you want to delete");
	   int del=sc.nextInt();
	   String sql="Delete from employee where Eno="+del;
	   PreparedStatement ps = con.prepareStatement(sql);
	   ps.executeUpdate();
	   System.out.println("Deleted successfully");
	   System.out.println("-------------------------------------------------------------------------");
	   
	}catch(Exception ex)
  {
		ex.printStackTrace();
  }
   }
   public static  void ClearData() throws SQLException {
	  try {
		  String sql="Delete from employee";
      Class.forName("com.mysql.jdbc.Driver");
      Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
	   PreparedStatement ps = con.prepareStatement(sql);
	   ps.executeUpdate();
	   System.out.println("Deleted data successfully");  
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	}
   public static  void Search() {
	   System.out.println("Enter employee number you want to view");
	   int srch=sc.nextInt();
	   Connection con=null;
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Pooja", "abcd1234");
		System.out.println("driver load");
		CallableStatement stmt=con.prepareCall("{?=call SearchEmp(?)}");
		stmt.registerOutParameter (1, Types.VARCHAR);
	    stmt.setInt(2,srch);       
	    stmt.execute(); 
		int eno=stmt.getInt(1);
		String name=stmt.getString(2);
		System.out.println(eno+name);
				System.out.print(" Employee number is "+stmt.getInt(1));
				System.out.print(" Employee name is "+stmt.getString(2));
				System.out.print(" Employee salary is "+stmt.getInt(3));
				System.out.print(" Employee Department is "+stmt.getString(4));
				System.out.println(" Employee Designation is "+stmt.getString(5));
				System.out.println("-------------------------------------------------------------------------");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
 
   public static  void ChangeSalary() throws SQLException, ClassNotFoundException {
	   Class.forName("com.mysql.jdbc.Driver");
       Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
	   System.out.println("Enter employee number you want to change salary");
	   int Eno=sc.nextInt();
	   System.out.println("Enter new salary");
	   int newsal=sc.nextInt();
	    String sql="Update employee set Salary='"+newsal+"'"+"where Eno='"+Eno+"'";	
	    PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		System.out.println("Salary changed....");
		System.out.println("-------------------------------------------------------------------------");
	}
   public static  void DeptwiseEmp() throws SQLException, ClassNotFoundException {
	   Class.forName("com.mysql.jdbc.Driver");
      Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
	   System.out.println("Enter department name of employees you want to view");
	   String dept=sc.next();
	   String sql="Select * from employee where Department='"+dept+"'";
	   try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.print(" Employee number is "+rs.getInt(1));
				System.out.print(" Employee name is "+rs.getString(2));
				System.out.print(" Employee salary is "+rs.getInt(3));
				System.out.print(" Employee Department is "+rs.getString(4));
				System.out.println(" Employee Designation is "+rs.getString(5));
				System.out.println("-------------------------------------------------------------------------");
			}
		
	  }catch(InputMismatchException ex)
	   {
		System.out.println("Please give correct depatment");		
	   }
	   catch(Exception ex)
	   {
		ex.printStackTrace();		
	   }
   }
   public static  void viewSortedEmployee() throws ClassNotFoundException, SQLException {
	   Class.forName("com.mysql.jdbc.Driver");
       Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
	   System.out.println("Enter on which basis you want to sort ........1 for Eno,2 for salary");
	   int ch=sc.nextInt();
	   if(ch==1) 
	   {
		   String sql="Select * from employee order by Eno";	  
		   try {
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					System.out.print(" Employee number is "+rs.getInt(1));
					System.out.print(" Employee name is "+rs.getString(2));
					System.out.print(" Employee salary is "+rs.getInt(3));
					System.out.print(" Employee Department is "+rs.getString(4));
					System.out.println(" Employee Designation is "+rs.getString(5));
					System.out.println("-------------------------------------------------------------------------");
				}
		   }
		   catch(Exception ex)
		   {
			ex.printStackTrace();}
	   }
	   else if(ch==2) {
		   {
			   String sql="Select * from employee order by Salary";	  
			   try {
					PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						System.out.print(" Employee number is "+rs.getInt(1));
						System.out.print(" Employee name is "+rs.getString(2));
						System.out.print(" Employee salary is "+rs.getInt(3));
						System.out.print(" Employee Department is "+rs.getString(4));
						System.out.println(" Employee Designation is "+rs.getString(5));
						System.out.println("-------------------------------------------------------------------------");
					}
			   }
			   catch(Exception ex)
			   {
				ex.printStackTrace();}
		   }
	   }
	   else {
		System.out.println("Please enter correct number");   
	   }
   }
}
