package com.itwill07.dao.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PhoneBookDao {
	
	public void insert(PhoneBook phone) throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		// insert
		String insertSql = "insert into phonebook values(phonebook_no_seq.nextval,'"
		+phone.getName()+"','"+phone.getPhone()+"')";
		
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(insertSql);
		stmt.close();
		con.close();
	}
	
	public void updateByNo(PhoneBook phonea) throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		//update
		String updateSql = "update phonebook set name='"+phonea.getName()+"',phone='"+phonea.getPhone()+"' where no = "+phonea.getNo();
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		int updateRowCount = stmt.executeUpdate(updateSql);
		System.out.println(">> "+updateRowCount + "행 update");
		stmt.executeUpdate(updateSql);
		stmt.close();
		con.close();
	}
	//delete
	public void deleteByNo(int no) throws Exception{ 
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		//delete
		String deleteSql = "delete from phonebook where no="+no;
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(deleteSql);
		stmt.close();
		con.close();
	}

	//select (pk select)
	public void selectByNo(int no) throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		//select
		String selectSql = "select no,name,phone from phonebook where no = "+no;
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(selectSql);
		while (rs.next()) {
			int n = rs.getInt("no");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			System.out.println(no + "\t" + name + "\t" + phone);
		}
		rs.close();
		stmt.close();
		con.close();
	}
	
	//select(all select)
	public void selectAll() throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		//selectAll
		String selectAllSql = 
				"select no,name,phone from phonebook order by no";
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(selectAllSql);
		while (rs.next()) {
			int no = rs.getInt("no");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			System.out.println(no + "\t" + name + "\t" + phone);
		}
		rs.close();
		stmt.close();
		con.close();
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		
		
		
		
		
		
		
		
		
	
}