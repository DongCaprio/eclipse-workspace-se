package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AddressInsertMain {

	public static void main(String[] args) throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		String insertSql = "insert into address values(address_no_seq.nextval,'guard','김경호','123-4568','경기도 성남시')";
	
		/*
		 1.Driver Class loading
		 2.Connection객체생성(DBServer에연결)
		 3.Statement객체생성(SQL 전송객체)
		 4.Statement객체를 사용해 SQL문전송(DML)
		 5.실행결과(Resultset,영향받은 행의수)
		 */

		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Statement stmt = con.createStatement();
		/*
		 * 4. Statement객체를 사용해 SQL문 전송(DML)
		 */
		stmt.executeUpdate(insertSql);
		int insertRowcount = stmt.executeUpdate(insertSql);
		System.out.println(">> "+insertRowcount+"행 insert");
		stmt.close();
		con.close();
		
	
	
	
	
	
	
	
	}

}
