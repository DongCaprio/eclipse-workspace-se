package com.itwill05.service.student;




public class StudentServiceMain {

	public static void main(String[] args) {
		StudentService studentService=new StudentService();
		/*
		 * 1. 전체학생총점,평균,평점계산
		 */
		System.out.println("1. 전체학생총점,평균,평점계산");
		studentService.calculate();
		studentService.print();
		//studentService.addStudent(new Student(17, "KYE", 11, 11, 11));
		/*
		 * 2. 전체학생 총점으로 석차계산
		 */
		System.out.println("2. 전체학생 총점으로 석차계산");
		studentService.calculateRank();
		studentService.print();
		/*
		 * 3-1. 전체학생반환
		 */
		System.out.println("3. 전체학생반환 ");
		studentService.findAll();
		/*
		 * 3-2. 전체학생출력
		 */
		System.out.println("3. 전체학생출력 ");
		studentService.print();
		/*
		 * 4. 번호3번 학생한명 반환
		 */
		System.out.println("4.번호3번 학생한명 반환  ");
		Student a = studentService.findByNo(3);
		a.print();
		/*
		 * 5. 학점A인 학생들 반환
		 */
		System.out.println("5. 학점A인 학생들 반환");
	//	Student[] aPeople = 
		studentService.returnGradeA('A');
//		for (int i = 0; i < aPeople.length; i++) {
//			aPeople[i].print();
//		}
		//오류가 뜨는데..어디인지 잘 모르겠네요
		
		/*
		 * 5. 이름KIM 인 학생들 반환
		 */
		System.out.println("5.  이름KIM 인 학생들 반환");
//		 Student[] aaa =
		studentService.returnName("KIM");
//		 for (int i = 0; i < aaa.length; i++) {
//			aaa[i].print();
//		} 
//		이것도 위와 마찬가지로...오류가 뜨는데
//		혹시 출력하는 법이 틀린건가요?
		
		/*
		 * 6. 학생석차로 오름차순정렬
		 */
		System.out.println("6. 학생석차로 오름차순정렬");
		studentService.ola();
		studentService.print();
	}

}