
public class ifUse {

	public static void main(String[] args) {
		/*
		 * 짝수홀수판별후출력
		 */
		int no1 = 56;

		if (no1 % 2 == 0)
			// System.out.println(no1+"는 짝수입니다.");
			System.out.printf("%d는 %s입니다\n", no1, "짝수");
		else
			// System.out.println(no1+"는 홀수입니다.");
			System.out.printf("%d는 %s입니다\n", no1, "홀수");

		// 4의 배수 판별
		int no2 = 5683;
		if (no2 % 4 == 0) {
			System.out.printf("%d는 %s입니다\n", no2, "4의배수");
		} else {
			System.out.printf("%d는 %s입니다\n", no2, "홀수");
		}

		int kor = -90;
		if (kor >= 0 && kor <= 100) {
			System.out.printf("%d는 유효한 점수입니다\n", kor);
		} else {
			System.out.printf("%d는 유효하지 않은 점수입니다\n", kor);
		}

		/*
		 * 윤년여부체크 . 4의 배수이면서 100의 배수가 아니면서 400의 배수
		 */
		int year = 2020;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + "는 윤년입니다!");
		} else
			System.out.println(year + "는 윤년이 아닙니다!");
		/*
		 * 문자판단 1. 한글 2. 알파벳 대문자(소문자) 3. 숫자 형태 문자 ---> '1'
		 */
		char c = '휅';

		if (c >= '가' && c <= '힣') {
			System.out.println(c + "는(은) 한글입니다");
		} else {
			System.out.println(c + "는(은) 한글이 아닙니다");
		}

		char d = 'i';
		if (d >= 'A' && d <= 'Z') {
			System.out.println(d + "는 알파벳 대문자입니다");
		} else {
			System.out.println(d + "는 알파벳 대문자가 아닙니다");
		}
		
		// quiz 아이디 적합 판단
		// 아이디를 생성하려면 아이디의 첫글자는 대문자이고 숫자와 문자가 모두 포함
		
		
		
		
		
		
		
		
		
		
		
		

	}
}
