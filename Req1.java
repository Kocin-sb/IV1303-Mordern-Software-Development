package MSD;


import java.io.IOException;
import java.util.Scanner;
import java.sql.*;


public class Req1 {
	 static String username = "";
	 static String password = "";
	 final static String url = "jdbc:mysql://localhost:3306/msd_db";
	 final static String user = "root";
	 final static String pass = "KocinMSD1!";
	 
	public static void main(String[] args) throws IOException {
		 Scanner scan = new Scanner(System.in);
		 while(true) {
		  System.out.print("Press '1' to login, press '2' to signup or '3' to exit:");
		  int choice = scan.nextInt();
		  System.out.println();
		  
		  switch (choice) {
		  case 1: 
			  verifyLogin();
			  break;
		  case 2:
			  signUp();
			  break;
		  case 3:
			  System.out.println("Thank you for using our system! Have a nice day :) ");
			  return;
		  default :
			  System.out.println("You Entered An Incorrect Input, Try Again.");
			  break;
		  }
		  System.out.println("****************************************************");
		 }
	}
	
	private static void signUp() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("|> Signup");
		System.out.print("|--> Enter Username : ");
		username = sc.nextLine();
		
		try {
			Connection mycon = DriverManager.getConnection(url,user,pass);
			Statement mystam = mycon.createStatement();
			String sql = "insert into user_details " +" (Username) " + " values('"+username+"')"; 
			mystam.executeUpdate(sql);
			System.out.print("|--> Enter Password : ");
			password = sc.nextLine();
		
			sql = "update user_details " +" set Password='"+ password+"' "+" where Username = '"+username+"';";
			mystam.executeUpdate(sql);
			System.out.println("|** ID created! **");
		} 
		catch (Exception e) {
			if ( e instanceof java.sql.SQLIntegrityConstraintViolationException  ) {
				System.out.println("|** Username already exists **");
			}else {System.out.println(e);}
		}
	}
	
	public static void verifyLogin() {
		System.out.println("|> Veryfying login");
		Scanner sc = new Scanner(System.in);
		System.out.print("|--> Enter Username : ");
		username = sc.nextLine();
		System.out.print("|--> Enter Password : ");
		password = sc.nextLine();	
		 try {
			 Connection mycon = DriverManager.getConnection(url,user,pass);
			 Statement mystam = mycon.createStatement();
			 String mysql = "SELECT * FROM user_details WHERE Username='"+username+"'";
			 ResultSet myrs = mystam.executeQuery(mysql);
			 myrs.next();
			 if(myrs.getString(1) != null) {
				 if(myrs.getString(3).equals(password)) {
					 System.out.println("|** Login success! **");
				 }else {System.out.println("|** Incorrect password :( **");}
			 }else {System.out.println("|** Username does not exist **");}

		 }
		 catch (Exception e) {
			 if( e.toString().equals("java.sql.SQLException: Illegal operation on empty result set.")) {
				 System.out.println("|** Incorrect Username **");
			 }else {System.out.println(e);}
		 }
	}
}
