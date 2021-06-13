package com.itwill03.포함;


public class Dvd {
	//번호
	//타이틀
	//쟝르
	private int num;
	private String title;
	private String genre;
	
	public Dvd() {
		// TODO Auto-generated constructor stub
	}
	
	public void ppp() {
		System.out.println("대출하신 자료의 정보는 다음과 같습니다");
		System.out.printf("  %d  %s  %s  \n",this.num,this.title,this.genre);
		
	}
	
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	  
}