package com.itwill03.포함;

public class BookLibraryMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * 회원객체생성
		 */
//		BookMember person1 = new BookMember(1, "유재석", "01012345678", "책1");
//		BookMember person2 = new BookMember(1, "박명수", "01044444444", "책2");
//		BookMember person3 = new BookMember(1, "노홍철", "01077777777", "책3");
//		BookMember person4 = new BookMember(1, "정준하", "01098765432", "책4");
//		//책객체생성
//		Book b1 = new Book(11, "책1", "1칸", "많이 어렵다");
//		Book b2 = new Book(22, "책2", "2칸", "어렵다");
//		Book b3 = new Book(33, "책3", "3칸", "쉽다");
//		Book b4 = new Book(44, "책4", "4칸", "많이 쉽다");
		BookMember person1 = new BookMember(1, "유재석", "01012345678");
		/*
		 * 회원이 책들 대여
		 */

		// * 책객체참조변수를 회원의 멤버변수에대입
		/*
		 * 회원정보출력(회원정보와 회원이 빌린책 정보출력)
		 */

		Book 책1 = new Book(11, "책1", "1칸", "많이 어렵다");
		person1.setBorrow(책1);
		BookMember.head();
		person1.printBook();

	}

}
