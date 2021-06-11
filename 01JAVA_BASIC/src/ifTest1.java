
public class ifTest1 {

	public static void main(String[] args) {
		// 중요** 코드 들여쓰기
		// ctrl + shift + f
		System.out.println("stm1"); 
		boolean conditon = false;

		if (conditon) {
			System.out.println("stm2");
			System.out.println("stm3");
			System.out.println("stm4");
		} // end if
		System.out.println("stm5");

		if (conditon) {
			System.out.println("stm6");
			System.out.println("stm7");
		} else {
			System.out.println("stm8");
			System.out.println("stm9");
		}
		System.out.println("-----stm10-----");

		if (conditon)
			System.out.println("stm11");
		System.out.println("-----stm12------");

		if (conditon)
			System.out.println("stm13");
		else
			System.out.println("stm14");
		System.out.println("---stm15---");

	}// end main

}// end class
