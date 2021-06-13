package com.itwill03.포함;

public class Book {
	/*
	 * - 캡슐화하세요 - 생성자를 정의하세요
	 */
	private int no;
	private String name;
	private String classification;
	private String explanation;

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(int no, String name, String classification, String explanation) {
		this.no = no;
		this.name = name;
		this.classification = classification;
		this.explanation = explanation;
	}
	
	public void print() {
		System.out.println("  "+this.no+"            "+this.name+"           "+this.classification+"          "+this.explanation);
	}
	public static void headerprint() {
		System.out.println("-------------------책 정보 출력--------------------");
		System.out.println("책번호        책제목      분류된위치         책설명");
	}

	
	
	
//	 * <<속성>>
//	 *  책번호
//	 *  책제목
//	 *  책분류
//	 *  책설명

//	 * <<기능>>
//	 *   책정보출력

}
