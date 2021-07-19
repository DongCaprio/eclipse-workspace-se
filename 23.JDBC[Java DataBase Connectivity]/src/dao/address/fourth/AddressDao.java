package dao.address.fourth;
/*
Dao(Data Access Object)
 - Address들의 데이터를 저장하고있는 Address 테이블에
   CRUD(Create, Read, Update, Delete) 작업을 할수있는
   단위(???)메쏘드를 가지고있는 클래스
 - AddressService객체의 요청(메쏘드호출)을 받아서 
   Data Access(DB)에 관련된 단위기능(CRUD)을
   수행하는 객체
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AddressDao {
	private DataSource dataSource;

	public AddressDao() {
		this.dataSource = new DataSource();
	}

	public int insert(Address address) throws Exception {
		String insertSql = "insert into address values(address_no_seq.nextval,?,?,?,?)";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(insertSql);
		pstmt.setString(1, address.getId());
		pstmt.setString(2, address.getName());
		pstmt.setString(3, address.getPhone());
		pstmt.setString(4, address.getAddress());
		int insertRowCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return insertRowCount;
	}

	public int deleteByNo(int no) throws Exception {
		String deleteSql = "delete from address where no=?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(deleteSql);
		pstmt.setInt(1, no);
		int deleteRowCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return deleteRowCount;
	}

	public int updateByNo(Address updateAdress) throws Exception {
		String updateSql = "update address set id=?,name=?,phone=?,address=? where no = ?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(updateSql);
		pstmt.setString(1, updateAdress.getId());
		pstmt.setString(2, updateAdress.getName());
		pstmt.setString(3, updateAdress.getPhone());
		pstmt.setString(4, updateAdress.getAddress());
		pstmt.setInt(5, updateAdress.getNo());
		int updateRowCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return updateRowCount;
	}

	public Address selectByNo(int no) throws Exception {
		Address findAddres = null;
		String selectSql = "select no,id,name,phone,address from address where no=?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		pstmt.setInt(1, no);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int n = rs.getInt("no");
			String id = rs.getString("id");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			String address = rs.getString("address");
			findAddres = new Address(n, id, name, phone, address);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return findAddres;
	}

	public ArrayList<Address> selectAll() throws Exception {
		ArrayList<Address> addressList = new ArrayList<Address>();
		String selectSql = "select no,id,name,phone,address from address";

		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no = rs.getInt("no");
			String id = rs.getString("id");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			String address = rs.getString("address");
			Address tempAddress = new Address(no, id, name, phone, address);
			addressList.add(tempAddress);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return addressList;
	}

}