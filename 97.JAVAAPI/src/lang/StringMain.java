package lang;

public class StringMain {

	public static void main(String[] args) {
		/*
		 * String 객체의 명시적생성
		 * -생성자 호출
		 */
		String name1 = new String("KIM");
		String name2 = new String("KIM");
		if(name1==name2) {
			System.out.println("name1과name2의 주소가같다");
		} else {
			System.out.println("name1과 name2의 주소가 다르다");
		} if(name1.equals(name2)) {
			System.out.println("name1의 문자열의 값과 name2 문자열의 값이 동일하다");
		} else {
			System.out.println("name1의 문자열의 값과 name2 문자열의 값이 동일하지 않다");
		}
		
		/*
		 * String 객체의 암시적생성(생성자호출안함)
		 *  ""으로 생성
		 */
//		Stirng st1 = "자바";
//		Stirng st2 = "자바";
//		String str3 = new Stirng("자바");
	}

}
