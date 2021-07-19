package dao.address.third;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
Dao(Data Access Object)
 - Address들의 데이터를 저장하고있는 파일(테이블)에
   CRUD(Create, Read, Update, Delete) 작업을 할수있는
   단위(???)메쏘드를 가지고있는 클래스
 - AddressService객체 의 요청(메쏘드호출)을 받아서 
   Data Access(File, DB)에 관련된 단위기능(CRUD)을
   수행하는 객체
 */

public class AddressDao3 {
	public void insert(Address address) throws Exception {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";

		String insertSql = "insert into address values (address_no_seq.nextval,?,?,?,?)";

		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmtInsert = con.prepareStatement(insertSql);
		pstmtInsert.setString(1, address.getId());
		pstmtInsert.setString(2, address.getName());
		pstmtInsert.setString(3, address.getPhone());
		pstmtInsert.setString(4, address.getAddress());

		int insertRowcount = pstmtInsert.executeUpdate();
		System.out.println(">> " + insertRowcount + "행 insert");

		pstmtInsert.close();
		con.close();
	}

//	public void insert(String id, String name, String phone, String address) throws Exception{
//		String driverClass="oracle.jdbc.OracleDriver";
//		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
//		String user = "javadeveloper4";
//		String password = "javadeveloper4";
//		String insertSql = "insert into address values"
//		+ "(address_no_seq.nextval,'"+id+"','"+name+"','"+phone+"','"+address+"')";
//		Class.forName(driverClass);
//		Connection con = DriverManager.getConnection(url, user, password);
//		Statement stmt = con.createStatement();
//		stmt.executeUpdate(insertSql);
//		int insertRowcount = stmt.executeUpdate(insertSql);
//		System.out.println(">> "+insertRowcount+"행 insert");
//		stmt.close();
//		con.close();
//	}
	public void deleteByNo(int no) throws Exception {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";

		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		String deleteSql = "delete from address where no=?";

		PreparedStatement pstmtDelete = con.prepareStatement(deleteSql);
		pstmtDelete.setInt(1, no);
		pstmtDelete.executeUpdate();
		pstmtDelete.close();
		con.close();
	}

	public void updateByNo(Address updateAdress) throws Exception {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";

		String updateSql = "update address set id=? ,name=? ,phone=? ,address=? where no =?";

		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmtUpdate = con.prepareStatement(updateSql);
		pstmtUpdate.setString(1, updateAdress.getId());
		pstmtUpdate.setString(2, updateAdress.getName());
		pstmtUpdate.setString(3, updateAdress.getPhone());
		pstmtUpdate.setString(4, updateAdress.getAddress());
		pstmtUpdate.setInt(5, updateAdress.getNo());

		pstmtUpdate.executeUpdate();
		pstmtUpdate.close();
		con.close();
	}

//	public void updateByNo(int no,String id, String name, String phone, String address) throws Exception{
//		String driverClass="oracle.jdbc.OracleDriver";
//		String url="jdbc:oracle:thin:@182.237.126.19:1521:xe";
//		String user = "javadeveloper4";
//		String password = "javadeveloper4";
//		String updateSql = 
//				"update address set id='"+id+"',name='"+name+"',phone='"+phone+"',address='"+address+"' where no ="+no;
//		Class.forName(driverClass);
//		Connection con = DriverManager.getConnection(url, user, password);
//		Statement stmt = con.createStatement();
//		int updateRowCount = stmt.executeUpdate(updateSql);
//		System.out.println(">> "+updateRowCount + "행 update");
//		stmt.close();
//		con.close();
//	}
	public Address selectByNo(int no) throws Exception {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";

		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		Address findAddres = null;
		String selectSql = "select no,id,name,phone,address from address where no=?";

//		Statement stmt = con.createStatement();
		PreparedStatement pstmtSelect = con.prepareStatement(selectSql);
		pstmtSelect.setInt(1, no);
		ResultSet rs = pstmtSelect.executeQuery();

		while (rs.next()) {
			int n = rs.getInt("no");
			String id = rs.getString("id");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			String address = rs.getString("address");
			findAddres = new Address(n, id, name, phone, address);
//			System.out.println(no + "\t" + id + "\t" + name + "\t" + phone + "\t" + address);
		}
		rs.close();
		pstmtSelect.close();
		con.close();
		return findAddres;
	}

	public ArrayList<Address> selectAll() throws Exception {
		/*********************************************************************/
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@182.237.126.19:1521:xe";
		String user = "javadeveloper4";
		String password = "javadeveloper4";
		/******************************************************************************/
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		ArrayList<Address> addressList = new ArrayList<Address>();
		String selectSql = "select no,id,name,phone,address from address ";
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int no = rs.getInt("no");
			String id = rs.getString("id");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			String address = rs.getString("address");
			// System.out.println(no + "\t" +id+ "\t" + name + "\t" + phone + "\t" +
			// address);
			Address tempAddress = new Address(no, id, name, phone, address);
			addressList.add(tempAddress);
		}

		rs.close();
		pstmt.close();
		con.close();
		return addressList;
	}
}
