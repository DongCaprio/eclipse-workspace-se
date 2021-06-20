package lang;

public class StringMain {

	public static void main(String[] args) {
		/*
		 * String 객체의 명시적생성 -생성자 호출
		 */
		String name1 = new String("KIM");
		String name2 = new String("KIM");
		if (name1 == name2) {
			System.out.println("name1과name2의 주소가같다");
		} else {
			System.out.println("name1과 name2의 주소가 다르다");
		}
		if (name1.equals(name2)) {
			System.out.println("name1의 문자열의 값과 name2 문자열의 값이 동일하다");
		} else {
			System.out.println("name1의 문자열의 값과 name2 문자열의 값이 동일하지 않다");
		}

		/*
		 * String 객체의 암시적생성(생성자호출안함) ""으로 생성
		 */
		String name3 = "KIM";
		String name4 = "KIM";
		if (name3 == "KIM") {
			System.out.println("name3과 \"KIM\"의 주소가 같다 (하지만 스트링에서는 절대 ==비교는 금지!!!)");
		} else {
			System.out.println("name3과 KIM의 주소가 다르다");
		}

		if (name4.equals(name3)) {
			System.out.println("스트링에서는 이렇게 비교!!");
		}
		if (name1.equals("KIM")) {
			System.out.println("name1과 name4 문자열이 동일하다\n" + "결론 : String비교는 반드시 .equals()로 비교한다 !");
		}

		/*
		 * 
		 */
		String irum1 = "5";
		String irum2 = "1";
		int unicodeGap1 = irum1.compareTo(irum2);
		System.out.println(unicodeGap1);

		int unicodeGap2 = irum2.compareTo(irum1);
		System.out.println(unicodeGap2);

		if (irum1.compareTo(irum2) > 0) {
			System.out.println("irum1,irum2 교환[오름차순]");
		}
	}
}
