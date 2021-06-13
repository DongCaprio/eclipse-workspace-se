package com.itwill03.포함;


public class DvdMember {
	//회원번호
	//회원이름
	//전화번호
	//빌린dvd
	private int no;
	private String name;
	private String phone;
	private Dvd dvdTitle;
	
	public DvdMember() {
		// TODO Auto-generated constructor stub
	}

	public DvdMember(int no, String name, String phone) {
		this.no = no;
		this.name = name;
		this.phone = phone;
	}

	public Dvd getDvdTitle() {
		return dvdTitle;
	}

	public void setDvdTitle(Dvd dvdTitle) {
		this.dvdTitle = dvdTitle;
	}
	
	public void printH() {
		System.out.println("   회원정보는 다음과 같습니다");
		System.out.printf("   %d    %s    %s\n",this.no,this.name,this.phone);
		this.dvdTitle.ppp();
		
	}
	

	
	
	
	
	
}