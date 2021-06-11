public class StudentScorePrint {
	public static void main(String[] args) {

		// 변수 선언
		String name1, name2;
		int num1, num2, kor1, kor2, eng1, eng2, math1, math2, sum1, sum2, num11, num22;
		char grade1, grade2;
		double avg1, avg2;
		// 리터럴 대입
		num1 = 1;
		name1 = "김경호";
		kor1 = 42;
		eng1 = 56;
		math1 = 78;
		sum1 = kor1 + eng1 + math1;
		avg1 = sum1 / 3.0;
		num11 = 0;
		// 리터럴 대입
		num2 = 2;
		name2 = "김경수";
		kor2 = 45;
		eng2 = 53;
		math2 = 76;
		sum2 = kor2 + eng2 + math2;
		avg2 = sum2 / 3.0;
		num22 = 0;
		// 조건문을 통해 성적입력 오류시의 예외처리
		if ((kor1 <= 100 && kor1 >= 0) && (kor2 <= 100 && kor2 >= 0) && (eng1 <= 100 && eng1 >= 0)
				&& (eng2 <= 100 && eng2 >= 0) && (math1 <= 100 && math1 >= 0) && (math2 <= 100 && math2 >= 0)) {
		} else {
			System.out.println("입력이 잘못되었습니다.");
			return;
		}

		// 김경호의 평균 소수점자리 처리
		avg1 = avg1 * 100;
		avg1 = (int) avg1;
		avg1 = avg1 + 5;
		avg1 = (int) avg1 / 10;
		avg1 = avg1 / 10;

		// 김경수의 평균 소수점자리 처리
		avg2 = avg2 * 100;
		avg2 = (int) avg2;
		avg2 = avg2 + 5;
		avg2 = (int) avg2 / 10;
		avg2 = avg2 / 10;

		// 평점 조건문으로 처리하기
		if (avg1 > 90) {
			grade1 = 'A';
		} else if (avg1 > 80) {
			grade1 = 'B';
		} else if (avg1 > 70) {
			grade1 = 'C';
		} else if (avg1 > 60) {
			grade1 = 'D';
		} else
			grade1 = 'F';
		// 김경수의 평점 조건문으로 범위를 나눠 처리하기
		if (avg2 > 90) {
			grade2 = 'A';
		} else if (avg1 > 80) {
			grade2 = 'B';
		} else if (avg2 > 70) {
			grade2 = 'C';
		} else if (avg2 > 60) {
			grade2 = 'D';
		} else
			grade2 = 'F';

		// 결과값 출력
		System.out.println("--------------학생 성적출력-------------------");
		System.out.println("학번  이름  국어  영어  수학  총점  평균  평점  석차");
		System.out.println("-------------------------------------------");
		System.out.println(" " + num1 + "  " + name1 + "   " + kor1 + "  " + eng1 + "   " + math1 + "  " + sum1 + "  "
				+ avg1 + "   " + grade1 + "    " + num11);
		System.out.println(" " + num2 + "  " + name2 + "   " + kor2 + "  " + eng2 + "   " + math2 + "  " + sum2 + "  "
				+ avg2 + "   " + grade2 + "    " + num22);
		System.out.println("-------------------------------------------");
	}
}