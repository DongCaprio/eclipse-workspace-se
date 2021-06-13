package com.itwill03.포함;

public class BookMember {
	/*
	- 캡슐화
	*/
//	<<속성>>
//	 회원번호
//	 회원이름
//	 전화번호
//	 빌린책
	private int no;
	private String name;
	private String PhoneNo;
	private Book borrow;
	
	public BookMember() {
		// TODO Auto-generated constructor stub
	}
	
	
public BookMember(int no, String name, String phoneNo, Book borrow) {
		this.no = no;
		this.name = name;
		this.PhoneNo = phoneNo;
		this.borrow = borrow;
	}


//	<<기능>>
//	  회원정보출력 설명
	


public void setBorrow(Book borrow) {
	this.borrow = borrow;
}


	public BookMember(int no, String name, String phoneNo) {
	super();
	this.no = no;
	this.name = name;
	PhoneNo = phoneNo;
}


	public void printBook() {
		System.out.println("             "+this.no+"        "+this.name+"    "+this.PhoneNo+"  ");
		System.out.println();
		Book.headerprint();
		this.borrow.print();
	}
	public static void head() {
		System.out.println("---------------------회원정보----------------------");
		System.out.println("           회원번호    이름     핸드폰번호     ");
	}
	
	
}

