package statement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;

/*
<<PreparedStatement>>
	1. sql문작성
	    - ?(파라메타) 를 사용해서 나중에 외부에서 데이타를 받을수있게한다.
	      ex> insert into emp(empno,ename,job,manager,hiredate,sal,comm,deptno) values(?,?,?,?,?,?,?,?)	
	    - 테이블이름,컬럼이름등은 ? 를 사용해서 외부에서 입력받을수없다.
	      ex> select * from ? 
	          select ?,? from emp    
	2. PreparedStatement 객체생성 후 인자로 sql문을 넣어서생성한다
	   ex> PreparedStatement pstmt = con.prepareStatement(sql);
	3. 생성된 PreparedStatement 객체에 파라메타를 setting(binding) 한다
	    - sql 좌측 ? 부터 1,2,3...
	   ex> 	pstmt.setInt(1,1234);              
	 	   	pstmt.setString(2,"KIM");              
	   		pstmt.setString(3,"MANAGER");              
	  		pstmt.setInt(4,7839);              
	   		pstmt.setDate(5,new Date());              
	   		pstmt.setDouble(6,1000.12);              
	   		pstmt.setInt(7,0);              
	   		pstmt.setInt(8,10);              
	 4.  실행
	    - 실행시 sql문을 인자로 넣지안는다.
	    ex> pstmt.executeUpdate();         
	        pstmt.executeQuery();         
*/
public class PreparedStatementMain{

	public static void main(String[] args)  throws Exception{
		String driverClass="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("---------insert(PreparedStatement)--------------");
		String insertSql1 = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) values(?,?,?,?,?,?,?,?)";
//		PreparedStatement pstmt1 = con.prepareStatement(insertSql1);
//		pstmt1.setInt(1, 7777);
//		pstmt1.setString(2, "TOM");
//		pstmt1.setString(3, "SALESMAN");
//		pstmt1.setInt(4, 7369);
//		pstmt1.setDate(5, new Date(System.currentTimeMillis()));
//		
//		/*
//		//java.util.Date --> java.sql.Date
//		java.util.Date utilDate = new SimpleDateFormat("yyyy/MM/dd").parse("2003/09/06");
//		long milliSec = utilDate.getTime();
//		pstmt1.setDate(5, new java.sql.Date(milliSec));
//		이렇게 util로도 넣을 수 있다.
//		*/
//		
//		pstmt1.setDouble(6, 8000.34);
//		pstmt1.setInt(7, 0);
//		pstmt1.setInt(8, 40);
//		int insertRowcount1 = pstmt1.executeUpdate();
//		System.out.println(insertRowcount1+"행 insert");
		
		String insertSql2 = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) values(?,?,?,?,sysdate,?,?,?)";
//		PreparedStatement pstmt2 = con.prepareStatement(insertSql2);
//		pstmt2.setInt(1, 7001);
//		pstmt2.setString(2, "ALICE");
//		pstmt2.setString(3, "CLERK");
//		pstmt2.setInt(4, 7369);
//		pstmt2.setDouble(5, 5000.34);
//		pstmt2.setInt(6, 400);
//		pstmt2.setInt(7, 30);
//		int insertRowcount2 = pstmt2.executeUpdate();
//		System.out.println(insertRowcount2+"행 insert");
		
		String insertSql3 = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) values(?,?,?,?,to_date(?,?),?,?,?)";
//		PreparedStatement pstmt3 = con.prepareStatement(insertSql3);
//		pstmt3.setInt(1, 7002);
//		pstmt3.setString(2, "SMOKY");
//		pstmt3.setString(3, "MANAGER");
//		pstmt3.setInt(4, 7369);
//		pstmt3.setString(5, "1998/03/07");
//		pstmt3.setString(6, "YYYY/MM/DD");
//		pstmt3.setDouble(7, 9998.99);
//		pstmt3.setInt(8, 800);
//		pstmt3.setInt(9, 10);
//		int insertRowcount3 = pstmt3.executeUpdate();
//		System.out.println(insertRowcount3+"행 insert");
		
		
		
		String insertSql4 = "insert into emp values(?,?,?,?,?,?,?,?)";
//		PreparedStatement pstmt4 = con.prepareStatement(insertSql1);
//		pstmt4.setInt(1, 7003);
//		pstmt4.setString(2, null); //String만 setnull없이 그냥 null값을 넣을 수 있다.
//		pstmt4.setNull(3, Types.VARCHAR);
//		pstmt4.setNull(4, Types.INTEGER);
//		pstmt4.setNull(5, Types.DATE);
//		pstmt4.setNull(6, Types.DOUBLE);
//		pstmt4.setNull(7, Types.INTEGER);
//		pstmt4.setNull(8, Types.INTEGER);
//		int insertRowcount4 = pstmt4.executeUpdate();
//		System.out.println(insertRowcount4+"행 insert");
		
		int startSal = 1000;
		int endSal =7000;
		String job = "CLERK";

		System.out.println("-----------------------select(Statement)--------------------");
		String selectSql1 = "select * from emp where sal>=" + startSal + " and sal<=" + endSal + " and job='" + job
				+ "'";
		Statement stmt = con.createStatement();
		ResultSet rs1 = stmt.executeQuery(selectSql1);
		while (rs1.next()) {
			int empno = rs1.getInt("empno");
			String ename = rs1.getString("ename");
			String jobStr = rs1.getString("job");
			double sal = rs1.getDouble("sal");
			Date hireDate = rs1.getDate("hiredate");
			String hireDateStr = new SimpleDateFormat("yyyy/MM/dd").format(hireDate);
			System.out.println(empno + "\t" + ename + "\t" + jobStr + "\t" + sal + "\t" + hireDateStr);

		}

		System.out.println("-----------------------select(PreparedStatement)--------------------");
		String selectSql2 = "select * from emp where sal>=? and sal<=?  and job=?";
		PreparedStatement pstmt = con.prepareStatement(selectSql2);
		pstmt.setInt(1, startSal);
		pstmt.setInt(2, endSal);
		pstmt.setString(3, job);
		ResultSet rs2 = pstmt.executeQuery();
		while (rs2.next()) {
			int empno = rs2.getInt("empno");
			String ename = rs2.getString("ename");
			String jobStr = rs2.getString("job");
			double sal = rs2.getDouble("sal");
			Date hireDate = rs2.getDate("hiredate");
			String hireDateStr = new SimpleDateFormat("yyyy/MM/dd").format(hireDate);
			System.out.println(empno + "\t" + ename + "\t" + jobStr + "\t" + sal + "\t" + hireDateStr);

		}
	}

}
