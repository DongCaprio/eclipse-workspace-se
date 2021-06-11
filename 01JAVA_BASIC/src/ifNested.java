
public class ifNested {

	public static void main(String[] args) {

		/*
		 * 평점 산출 A, B, C, D, F
		 */
		int kor = 50;
		char grade = ' ';
		if (kor >= 90) {
			grade = 'A';
		} else {
			if (kor >= 80) {
				grade = 'B';
			} else {
				if (kor >= 70) {
					grade = 'C';
				} else {
					grade = 'D';
				}
			}

		}

		System.out.printf("1. 학점은 %c입니다", grade);

		grade = ' ';
		if (kor >= 98) {
			grade = 'A';
		} else if (kor >= 90) {
			System.out.println('A');
		} else if (kor >= 80) {
			System.out.println('B');
		} else if (kor >= 70) {
			System.out.println('C');
		} else if (kor >= 60) {
			System.out.println('D');
		} else {
			System.out.println('F');
		}

	}

}
