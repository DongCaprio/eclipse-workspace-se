public class StudentFieldMain {

	public static void main(String[] args) {
		/*
		 * 학생객체 2명 생성
		 */
		Student student1 = new Student();
		Student student2 = new Student();

		/*
		 * 학생객체에기본데이타(번호,이름,국어,영어,수학)대입(2명)
		 */
		// student1의 데이터 대입
		student1.num = 1;
		student1.name = "김군";
		student1.kor = 90;
		student1.eng = 88;
		student1.math = 74;

		// student2의 데이터 대입
		student2.num = 2;
		student2.name = "박군";
		student2.kor = 60;
		student2.eng = 44;
		student2.math = 47;

		/*
		 * 학생총점,평균,평점계산후 대입(2명)
		 */

		student1.sum = student1.kor + student1.eng + student1.math;
		student1.avg = student1.sum / 3.0;

		if (student1.avg <= 100 && student1.avg >= 60) {
			student1.grade = 'A';
		} else {
			student1.grade = 'B';
		}

		student2.sum = student2.kor + student2.eng + student2.math;
		student2.avg = student2.sum / 3.0;

		if (student2.avg <= 100 && student2.avg >= 60) {
			student2.grade = 'A';
		} else {
			student2.grade = 'B';
		}

		/*
		 * 학생데이타출력2명
		 */
		System.out.println("--------------학생 성적출력-------------------");
		System.out.println("학번  이름   국어 영어 수학 총점 평균 평점");
		System.out.println("----------------------------------------------");
		System.out.printf("%d  %s  %d  %d  %d  %d  %.2f  %c", student1.num, student1.name, student1.kor, student1.eng,
				student1.math, student1.sum, student1.avg, student1.grade);
		System.out.println();
		System.out.println();
		System.out.printf("%d  %s  %d  %d  %d  %d  %.2f  %c", student2.num, student2.name, student2.kor, student2.eng,
				student2.math, student2.sum, student2.avg, student2.grade);

	}

}