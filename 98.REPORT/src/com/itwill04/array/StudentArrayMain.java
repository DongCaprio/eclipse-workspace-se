package com.itwill04.array;

public class StudentArrayMain {

	public static void main(String[] args) {
		/*
		 * 0.학생배열객체초기화
		 */
		Student[] students = { new Student(1, "KIM", 89, 93, 94), new Student(2, "HIM", 88, 77, 98),
				new Student(3, "AIM", 65, 87, 99), new Student(4, "BIM", 75, 97, 60), new Student(5, "XIM", 85, 98, 90),
				new Student(6, "TIM", 95, 88, 77), new Student(7, "ZIM", 99, 93, 95), new Student(8, "LIM", 83, 80, 99),
				new Student(9, "QIM", 73, 90, 80), };

		/*
		 * 1. 전체학생총점,평균,평점계산
		 */
		System.out.println("1. 전체학생총점,평균,평점계산");
		for (int i = 0; i < students.length; i++) {
			students[i].calculateTotal();
			students[i].calculateAvg();
			students[i].calculateGrade();
		}
		/*
		 * 2. 전체학생 총점으로 석차계산
		 */
		System.out.println("2. 전체학생 총점으로 석차계산");

		// 그다음 get set을 이용해서 총점순서대로 랭크의 값을 매긴다
		for (int i = 0; i < students.length; i++) {
			students[i].setRank(1);// 기본값이 0이므로 모든 석차에 기본값을 1로 해준다.(1등이 0등이 아닌 1등이 되게하기위해서)
			for (int j = 0; j < students.length; j++) {
				if (students[i].getTot() < students[j].getTot()) {
					students[i].setRank(students[i].getRank() + 1);
				}

			}
		}
		System.out.println();
		/*
		 * 3. 전체학생출력
		 */
		System.out.println("3. 전체학생출력 ");

		// static인 헤더를 먼저 프린트해준다.
		Student.headerPrint();
		// 그다음 객체별로 print메소드를 사용해서 학생의 정보를 출력해준다.
		for (int i = 0; i < students.length; i++) {
			students[i].print();
		}

		System.out.println();
		/*
		 * 4. 번호3번 학생한명 출력
		 */
		System.out.println("4.번호3번 학생한명 출력  ");
		Student.headerPrint();// 헤더출력
		for (int i = 0; i < students.length; i++) { // No이 3인 학생의 정보출력
			if (students[i].getNo() == 3) {
				students[i].print();
			}
		}
		System.out.println();

		/*
		 * 5. 학점A인 학생들 출력
		 */

		System.out.println("5. 학점A인 학생들 출력");
		Student.headerPrint();// 헤더출력
		for (int i = 0; i < students.length; i++) { // Grade가 A인 학생의 정보출력
			if (students[i].getGrade() == 'A') {
				students[i].print();
			}
		}
		System.out.println();

		/*
		 * 6. 학생총점으로 오름차순정렬
		 */
		System.out.println("6. 학생총점으로 내림차순정렬");

		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - 1 - i; j++) {
				if (students[j].getTot() < students[j + 1].getTot()) {
					Student tmp = students[j + 1];
					students[j + 1] = students[j];
					students[j] = tmp;
				}
			}
		}
		Student.headerPrint();
		for (int i = 0; i < students.length; i++) {
			students[i].print();
		}
		System.out.println();
		/*
		 * 7. 학생학점순으로 오름차순정렬
		 */
		System.out.println("6. 학생학점순으로 오름차순정렬");
		for (int i = 0; i < students.length - 1; i++) { 
			for (int j = 0; j < students.length - 1 - i; j++) {
				if (students[j].getGrade() < students[j + 1].getGrade()) {
					Student tmp = students[j + 1];
					students[j + 1] = students[j];
					students[j] = tmp;
				}
			}
		}
		Student.headerPrint();
		for (int i = 0; i < students.length; i++) {
			students[i].print();
		}
	}

}