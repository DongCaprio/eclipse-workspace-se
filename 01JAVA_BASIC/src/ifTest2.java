
public class ifTest2 {

	public static void main(String[] args) {

		int x = 21;
		int y = 30;

		if (x > y) {
			System.out.println("true -> 21 > 30");
		} else {
			System.out.println("false -> 21 > 30");
		}
		if (x < y) {
			System.out.println("true -> 21 < 30");
		} else {
			System.out.println("false -> 21 < 30");
		}
		if (x == y) {
			System.out.println("true -> 21 ==30");
		} else {
			System.out.println("true -> 21 !=30");
		}
		System.out.println("-------지역변수의 범위(scope)--------");
		int kor = 90;
		if (kor >= 90) {
			char grade = 'A';
			String msg = "참 잘했어요 !";
			System.out.println("kor = " + kor);
			System.out.println("grade = " + grade);
			System.out.println("msg = " + msg);
		}
		System.out.println("kor = " + kor);
//		System.out.println("grade = " + grade);
//		System.out.println("msg = "+msg);
		return;// 실행흐름되돌려줌
	}

}
