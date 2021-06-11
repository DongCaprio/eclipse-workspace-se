
public class WhileUse {

	public static void main(String[] args) {

		System.out.println("------10회 반복-------");

		int i = 0;
		while (i < 10) {
			System.out.println("stmt: " + i);
			i++;
		}
		System.out.println(">> 1~10사이의 정수 출력");
		i = 0;
		while (i < 10) {
			int no = i + 1;
			System.out.print(i + ",");
			i++;
		}
		System.out.println(">> 1 ~ 10사이의 정수중 홀수출력");
		i = 1;
		while (i <= 10) {
			if (i % 2 == 1) {
				System.out.print(i + ", ");
			}
			i++;
		}
		System.out.println();

		System.out.println(">> 1~10사이의 정수중 4의 배수 출력");
		i = 1;
		while (i <= 10) {
			if (i % 4 == 0) {
				System.out.print(i + ",");
			}
			i++;
		}
		System.out.println();

		System.out.println();
		System.out.println(">> 1~100사이의 정수출력");
		i = 1;
		while (i <= 100) {
			if (i <= 10) {
				System.out.print(i + " ,");
			} else
				System.out.print(i + ",");
			if (i % 10 == 0)
				System.out.println();
			i++;
		}
		System.out.println(">>1~100 사이의 정수중 3의 배수이면서 4의 배수인수 출력");

		i = 1;
		while (i <= 100) {
			if (i % 3 == 0 && i % 4 == 0) {
				System.out.println(i + " ");
			}
			i++;
		}
		/*
		 * 1~100사이의 정수합 1~100사이의 홀수합 1~100사이의 짝수합
		 */
		i = 1;
		int tot = 0;
		int jjack = 0;
		int hol = 0;

		while (i <= 100) {
			if (i % 2 == 0) {
				jjack += i;
			} else {
				hol += i;
			}
			tot += i;
			i++;
		}
		System.out.println(jjack);
		System.out.println(hol);
		System.out.println(tot);

		/*
		 * 문자출력
		 */

		// char는 부호가 없음 무조건 플러스!
//		char c1 = 0;
//		while(c1 < 65535) {
//			if (c1 % 60 ==0) {
//				System.out.println();
//			}
//			System.out.print(c1);
//			c1++;
//		}
		/*
		 * 영문소문자출력
		 */
		char c2 = 'z';
		while (c2 < 'a') {
			System.out.println(c2);
			c2++;
		}
	}

}
