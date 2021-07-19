package dao.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.address.fourth.DataSource;

/*
 Dao(Data Access Object)
 - member 들의 데이터를 저장하고있는 member 테이블에
   CRUD(Create, Read, Update, Delete) 작업을 할수있는
   단위(???)메쏘드를 가지고있는 클래스
 - MemberService객체의 요청(메쏘드호출)을 받아서 
   Data Access(DB)에 관련된 단위기능(CRUD)을
   수행하는 객체
 */
/*
이름         널?       유형            
---------- -------- ------------- 
M_ID       NOT NULL VARCHAR2(20)  
M_PASSWORD          VARCHAR2(10)  
M_NAME              VARCHAR2(50)  
M_ADDRESS           VARCHAR2(100) 
M_AGE               NUMBER(3)     
M_MARRIED           CHAR(1)       
M_REGDATE           DATE          
 */
public class MemberDao {
	private DataSource dataSource;
	
	public MemberDao() {
		dataSource = new DataSource();
	}
	public int insert(Member member) throws Exception{
		String insertSql = "insert into member values(?,?,?,?,?,?,?)";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(insertSql);
		pstmt.setString(1, member.getM_id());
		pstmt.setString(2, member.getM_passord());
		pstmt.setString(3, member.getM_name());
		pstmt.setString(4, member.getM_address());
		pstmt.setInt(5, member.getM_age());
		pstmt.setString(6,member.getM_married());
//		pstmt.setDate(7, member.getM_regdate());
		pstmt.setDate(7, null); //시간을 바꾸는걸 잘 모르겠어서 다 null로 했습니다
		int insertRowCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return insertRowCount;
	}
	public int deleteById(String m_id) throws Exception{
		String deleteSql = "delete from member where m_id = ?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(deleteSql);
		pstmt.setString(1, m_id);
		int deleteCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return deleteCount;
	}
	public int updateById(Member member) throws Exception{
		String updateSql = "update member set m_password=?,m_name=?, m_address=?, m_age=?, m_married=? where m_id=? ";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(updateSql);
		pstmt.setString(1, member.getM_passord());
		pstmt.setString(2, member.getM_name());
		pstmt.setString(3, member.getM_address());
		pstmt.setInt(4, member.getM_age());
		pstmt.setString(5,member.getM_married());
		pstmt.setString(6, member.getM_id());
		int updateCount = pstmt.executeUpdate();
		pstmt.close();
		dataSource.releaseConnection(con);
		return updateCount;
	}
	public Member selectById(String m_id) throws Exception{
		Member findMember = null;
		String selectSql = "select * from member where m_id=?";
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		pstmt.setString(1, m_id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String id = rs.getString("m_id");
			String password = rs.getString("m_password");
			String name = rs.getString("m_name");
			String address = rs.getString("m_address");
			int age = rs.getInt("m_age");
			String married = rs.getString("m_married");
			String regdate = rs.getString("m_regdate");
			findMember = new Member(id, password, name, address, age, married, null);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return findMember;
	}
	public ArrayList<Member> selectAll() throws Exception {
		ArrayList<Member> memberList = new ArrayList<Member>();
		String selectSql = "select * from member";
		
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(selectSql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String id = rs.getString("m_id");
			String password = rs.getString("m_password");
			String name = rs.getString("m_name");
			String address = rs.getString("m_address");
			int age = rs.getInt("m_age");
			String married = rs.getString("m_married");
			Member tempList = new Member(id, password, name, address, age, married, null);
			memberList.add(tempList);
		}
		rs.close();
		pstmt.close();
		dataSource.releaseConnection(con);
		return memberList;
	}
}
