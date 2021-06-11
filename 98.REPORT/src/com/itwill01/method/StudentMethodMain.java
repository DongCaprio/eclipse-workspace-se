package com.itwill01.method;

public class StudentMethodMain {

	public static void main(String[] args) {

		// 학생 3명의 인스턴스 생성
		Student s1 = new Student();
		Student s2 = new Student();
		Student s3 = new Student();

		/*
		 * 학생객체기본데이타(번호, 이름, 국어, 영어, 수학) 입력메쏘드호출(3명)
		 */
		s1.basicData(1, "신짱구", 80, 90, 45);
		s2.basicData(2, "김철수", 33, 27, 99);
		s3.basicData(3, "이훈이", 99, 88, 77);

		/*
		 * 학생총점계산 메쏘드 호출(3명)
		 */
		s1.makeTot();
		s2.makeTot();
		s3.makeTot();
		/*
		 * 학생평균계산 메쏘드 호출(3명)
		 */
		s1.makeAvg();
		s2.makeAvg();
		s3.makeAvg();

		/*
		 * 학생평점계산 메쏘드 호출(3명)
		 */
		s1.makeGrade();
		s2.makeGrade();
		s3.makeGrade();

		// 클래스 영역에서 구하지못한 석차 값 대입
		s1.rank = 2;
		s2.rank = 3;
		s3.rank = 1;
		/*
		 * 학생데이타 출력메쏘드 호출(3명)
		 */
		System.out.println("**********************************************");
		System.out.println("번호 이름 국어 영어 수학 총점 평균 평점 석차");
		System.out.println("**********************************************");

		s1.printt();
		s2.printt();
		s3.printt();
	}

}