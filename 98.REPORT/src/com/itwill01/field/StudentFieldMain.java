
package com.itwill01.field;

public class StudentFieldMain {

	public static void main(String[] args) {
		/*
		 *학생객체 2명 생성
		 */
		Student student1 = new Student();
		Student student2 = new Student();
		////////////////////////////////////
		/*
		 * 학생객체에기본데이타(번호,이름,국어,영어,수학)대입(2명)
		 */
		student1.no=1;
		student1.name="김경수";
		student1.kor=78;
		student1.eng=34;
		student1.math=30;
		
		student2.no=2;
		student2.name="김경우";
		student2.kor=45;
		student2.eng=89;
		student2.math=88;
		
		/*
		 * 학생총점,평균,평점계산후 대입(2명)
		 */
		student1.tot = student1.kor+student1.eng+student1.math;
		student1.avg = student1.tot/3.0;
		if(student1.avg>=90) {
			student1.grade='A';
		}else if(student1.avg>=80) {
			student1.grade='B';
		}else if(student1.avg>=70) {
			student1.grade='C';
		}else if(student1.avg>=60) {
			student1.grade='D';
		}else {
			student1.grade='F';
		}
		
		student2.tot = student2.kor+student2.eng+student2.math;
		student2.avg = student2.tot/3.0;
		if(student2.avg>=90) {
			student2.grade='A';
		}else if(student2.avg>=80) {
			student2.grade='B';
		}else if(student2.avg>=70) {
			student2.grade='C';
		}else if(student2.avg>=60) {
			student2.grade='D';
		}else {
			student2.grade='F';
		}
		/*
		 * 학생데이타출력2명
		 */
		System.out.printf("---------------학생 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n",
						"학번","이름","국어","영어", "수학","총점","평균", "평점","석차");
		System.out.printf("-----------------------------------------------%n");
		System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
				student1.no,student1.name,
				student1.kor,student1.eng,
				student1.math,student1.tot,
				student1.avg,student1.grade,
				student1.rank);
		
		System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
				student2.no,student2.name,
				student2.kor,student2.eng,
				student2.math,student2.tot,
				student2.avg,student2.grade,
				student2.rank);
		
	}

}