package com.itwill03.포함;


public class DvdShopMain {

	public static void main(String[] args) {
		/*
		 * 회원객체생성
		 */
		DvdMember p1 = new DvdMember(100,"김민수","010_3333_3333");
		
		/*
		 * Dvd객체생성
		 */
		Dvd d1 = new Dvd();
		d1.setNum(4444);
		d1.setTitle("나는공포영화");
		d1.setGenre("스릴러");
		
		/*
		 * 회원이Dvd을 대여
		 */
		 // Dvd객체참조변수를 회원의 멤버변수에대입
		 p1.setDvdTitle(d1);
		
		/*
		 * 회원정보출력(회원이빌린Dvd정보출력)
		 */
		 p1.printH();
		 
		 
	}

}