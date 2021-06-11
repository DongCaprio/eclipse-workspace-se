package com.itwill01.field;


public class StudentConstructorMain {

	public static void main(String[] args) {
		
		/*
		 학생객체생성(3명)
		  - 1명은 기본생성자사용
		      학생객체데이타(번호, 이름, 국어, 영어, 수학)입력메쏘드호출
		  - 2명은 인자5개 생성자사용
		 */
		Student st1=new Student();
		Student st2=new Student(2, "LIM", 22, 44, 66);
		Student st3=new Student(3, "JIM", 77, 88, 99);
		
		
		st1.setStudentData(1, "KIM", 1, 2, 3);
		
		/*
		 * 학생총점계산 메쏘드 호출(3명) 
		 */
		st1.calculateTotal();
		st2.calculateTotal();
		st3.calculateTotal();
		
//		* 학생평균계산 메쏘드 호출(3명) 
		st1.calculateAvg();
		st2.calculateAvg();
		st3.calculateAvg();
		
	//	* 학생평점계산 메쏘드 호출(3명)
		st1.calculateGrade();
		st2.calculateGrade();
		st3.calculateGrade();
		
		/*
		 * 학생데이타3명 출력메쏘드 호출
		 */
		st1.headerPrint();
		st1.print();
		st2.print();
		st3.print();		
		/*
		 * 학생 student1 의 이름변경
		 * 학생 student1 의 총점데이타 반환받은후 메인에서출력
		 * 학생 student2 의 학점데이타 반환받은후 메인에서출력
		 */
		st1.setStudentData("박씨");
		st1.getTot();
		st1.getGrade();
		System.out.println("박씨의 이름 총점 학점");
		System.out.println("\t"+st1.getName()+"  "+st1.getTot()+"   "+st1.getGrade());
		
		

	}

}