package com.itwill07.dao.phonebook;
/*
NO		NUMBER(4,0)			No		1	
NAME	VARCHAR2(50 BYTE)	Yes		2	
PHONE	VARCHAR2(50 BYTE)	Yes		3	
*/
public class PhoneBook {
	private int no;
	private String name;
	private String phone;

	public PhoneBook() {
		// TODO Auto-generated constructor stub
	}

	public PhoneBook(int no, String name, String phone) {
		super();
		this.no = no;
		this.name = name;
		this.phone = phone;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
