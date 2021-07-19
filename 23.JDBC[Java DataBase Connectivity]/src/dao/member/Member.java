package dao.member;

import java.sql.Date;
/*
VO(Value Object),DTO(Data Transfer Object)
	- 한개의 주소객체  데이타를 가지고있다.(VO)
	- 한개의 주소객체  데이타이동(파라메타,리턴데이타)시키기위한객체(DTO)
	- member 테이블과 동일한 속성을 멤버변수로가지는 객체
 */
/*
이름         널?       유형            
---------- -------- ------------- 
M_ID       NOT NULL VARCHAR2(10)  
M_PASSWORD          VARCHAR2(10)  
M_NAME              VARCHAR2(126) 
M_ADDRESS           VARCHAR2(255) 
M_AGE               NUMBER(3)     
M_MARRIED           CHAR(1)       
M_REGDATE           DATE    
*/
public class Member {
	private String m_id;
	private String m_passord;
	private String m_name;
	private String m_address;
	private int m_age;
	private String m_married;
	private Date m_regdate;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}
	
	public Member(String m_id, String m_passord, String m_name, String m_address, int age, String m_married,
			Date m_regdate) {
		super();
		this.m_id = m_id;
		this.m_passord = m_passord;
		this.m_name = m_name;
		this.m_address = m_address;
		this.m_age = age;
		this.m_married = m_married;
		this.m_regdate = m_regdate;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_passord() {
		return m_passord;
	}

	public void setM_passord(String m_passord) {
		this.m_passord = m_passord;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_address() {
		return m_address;
	}

	public void setM_address(String m_address) {
		this.m_address = m_address;
	}

	public int getM_age() {
		return m_age;
	}

	public void setM_age(int m_age) {
		this.m_age = m_age;
	}

	public String getM_married() {
		return m_married;
	}

	public void setM_married(String m_married) {
		this.m_married = m_married;
	}

	public Date getM_regdate() {
		return m_regdate;
	}

	public void setM_regdate(Date m_regdate) {
		this.m_regdate = m_regdate;
	}

	@Override
	public String toString() {
		return "Member [m_id=" + m_id + ", m_passord=" + m_passord + ", m_name=" + m_name + ", m_address=" + m_address
				+ ", m_age=" + m_age + ", m_married=" + m_married + ", m_regdate=" + m_regdate + "]";
	}
	
	
	
}
