
public class SwitchUse {

	public static void main(String[] args) {

		/*
		 * 짝수 홀수 판별
		 */
		int number = 23;

		switch (number % 2) {
		case 0:
			System.out.println("짝수");
			break;
		case 1:
			System.out.println("홀수");
			break;
		}
		/*
		 * 4의 배수 판별
		 */
		int number2 = 400;
		switch (number2 % 4) {
		case 0:
			System.out.println(number2 + " 는 4의 배수");
			break;
		default:
			System.out.println(number2 + "는 4의 배수 아닙니다");
		}
		/*
		 * 문자비교 A : left D
		 */

		char direction = 'A';

		switch (direction) {
		case 'A':
			System.out.println("left");
		case 'D':
			System.out.println("UP");
		}

	}

}
