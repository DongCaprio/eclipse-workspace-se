package wrapper;

public class WrapperClassCompareMain {

	public static void main(String[] args) {

		Integer wi1 = new Integer(89);
		Integer wi2 = new Integer(89);
		Integer wi3 = wi1;
		/*
		 * Wrapper, String객체
		 *  - 비교연산자( == )를 사용해서 주소비교금지!!!!!!!!!
		 *  - equals메소드 사용(멤버필드기본형값비교)[재정의]
		 */
		
//		절대사용금지!!!!!!!!!!!!!
//		if(wi1 == wi2) {
//			System.out.println("wi1과 wi2의 주소가 같다");
//		} else {
//			System.out.println("wi1과 wi2의 주소가 다르다");
//		}
//		if(wi1 == wi3) {
//			System.out.println("wi1과 wi3의 주소가 같다");
//		} else {
//			System.out.println("wi1과 wi3의 주소가 다르다");
//		}
		
		if(wi1.equals(wi2)) {
			System.out.println("wi1,wi2의 값이 동일하다.");
		}
		if(wi1.equals(wi3)) {
			System.out.println("wi1,wi3의 값이 동일하다");
		}
		if(wi2.equals(wi3)) {
			System.out.println("wi2,wi3의 값이 동일하다");
		}
		
	}

}
