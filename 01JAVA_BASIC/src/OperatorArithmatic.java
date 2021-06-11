
public class OperatorArithmatic {

	public static void main(String[] args) {

		int a = 1;
		int b = 2;
		int result = 0;
		double dresult;
		
		result = a + b;
		System.out.println("a + b -> "+result);
		result = a - b;
		System.out.println("a - b -> "+result);
		result = a * b;
		System.out.println("a * b -> "+result);
		dresult = a / b;
		System.out.println("a / b -> "+dresult);
		result = a % b;
		System.out.println("a % b -> "+result);
		
		int no = 16;
		result = no % 4;
		System.out.println("result의 값이 0이면 4의 배수 : "+result);
		char ca = 'A';
		char cz = 'Z';
		result = ca;
		System.out.println(ca);
		result = ca +35;
		char abb = (char)result;
		System.out.println("A의 더하기는뭘까? "+result);
		System.out.println("A의 더하기는뭘까? "+abb);
		
		int numOfAlphabet = cz-ca+1;
		System.out.println(numOfAlphabet);
		
		int numOfHangul = '힣'-'가'+1;
		System.out.println("한글의 개수는 몇개? "+numOfHangul);
		
		char ch1 = '가'+100;
		System.out.println(ch1);
		
		char c123= 'A';
		System.out.println(c123+1);
		
		/*
		 * 비트연산
		 * int d = 1;
		 */
		int d = 1;
		int result5 = d << 4;
		System.out.println(result5);
		
		
		
		
		
		
	}

}
