package dao.guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.common.DataSource;

/*이름             널?       유형             
-------------- -------- -------------- 
GUEST_NO       NOT NULL NUMBER(10)     
GUEST_NAME     NOT NULL VARCHAR2(100)  
GUEST_DATE     NOT NULL DATE           
GUEST_EMAIL             VARCHAR2(100)  
GUEST_HOMEPAGE          VARCHAR2(100)  
GUEST_TITLE    NOT NULL VARCHAR2(255)  
GUEST_CONTENT  NOT NULL VARCHAR2(4000) */
public class GuestDao {
	private DataSource dataSource;

	public GuestDao() throws Exception {
		dataSource = new DataSource();
	}

	public int insertGuest(Guest guest) throws Exception {
		String insertSql = "insert into GUEST values(guest_no_seq.nextval ,?,sysdate,?,?,?,?)";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(insertSql);
		pstmt.setString(1, guest.getGuest_name());
		pstmt.setString(2, guest.getGuest_email());
		pstmt.setString(3, guest.getGuest_homepage());
		pstmt.setString(4, guest.getGuest_title());
		pstmt.setString(5, guest.getGuest_content());
		int insertCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return insertCount;
	}

	public int deleteGuest(int no) throws Exception {
		String deleteSql = "delete from guest where guest_no = ?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(deleteSql);
		pstmt.setInt(1, no);
		int deleteCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return deleteCount;
	}

	public int updateGuest(Guest guest) throws Exception {
		String updateSql = "update guest set guest_name=?,guest_email=?,guest_homepage=?, guest_title=?, guest_content=? where guest_no = ?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(updateSql);
		pstmt.setString(1, guest.getGuest_name());
		pstmt.setString(2, guest.getGuest_email());
		pstmt.setString(3, guest.getGuest_homepage());
		pstmt.setString(4, guest.getGuest_title());
		pstmt.setString(5, guest.getGuest_content());
		pstmt.setInt(6, guest.getGuest_no());
		int updateCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return updateCount;
	}

	public Guest selectByNo(int no) throws Exception {
		Guest guest = null;
		String selectSql = "select * from guest where guest_no = ?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		pstmt.setInt(1,no);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no1 = rs.getInt("guest_no");
			String name = rs.getString("guest_name");
			String date = rs.getString("guest_date");
			String email = rs.getString("guest_email");
			String homepage = rs.getString("guest_homepage");
			String title = rs.getString("guest_title");
			String content = rs.getString("guest_content");
			guest = new Guest(no1, name,date, email,  homepage, title, content);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return guest;
	}

	public ArrayList<Guest> selectAll() throws Exception {
		ArrayList<Guest> guestList = new ArrayList<Guest>();
		String selectSqlAll = "select * from guest";
		
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSqlAll);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no1 = rs.getInt("guest_no");
			String name = rs.getString("guest_name");
			String date = rs.getString("guest_date");
			String email = rs.getString("guest_email");
			String homepage = rs.getString("guest_homepage");
			String title = rs.getString("guest_title");
			String content = rs.getString("guest_content");
			Guest gg = new Guest(no1, name,date, email,  homepage, title, content);
			guestList.add(gg);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return guestList;
	}

}