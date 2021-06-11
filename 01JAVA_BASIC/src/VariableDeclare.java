
public class VariableDeclare {

	public static void main(String[] args) {

		int score1 = 78; // 변수에 값 대입
		System.out.println(score1);// 변수의 값 출력
		score1 = 99;
		System.out.println(score1);
		int score2 = 5569;
		System.out.println(score2);

		int _number = 333;
		System.out.println(_number);

		int kor, eng, math;
		kor = 100;
		eng = 90;
		math = 42;
		System.out.println(kor);
		System.out.println(eng);
		System.out.println(math);

		/*
		 * 변수선언 데이터타입 이름(식별자) ex) String name
		 */
		String name;
		name = "김동진";
		System.out.println(name);

		byte b3 = 127;
		byte b4 = 0b01111111;
		short s4 = 0b0111111111111111;
		System.out.println(s4);
		System.out.println(b3);
		System.out.println(b4);
		int i1 = 0b111;
		System.out.println(i1);

		long i4 = 2147483648L;
		long i5 = 0b0111111111111111111111111111111111111111111111111111111111111111L;
		System.out.println(i4);
		System.out.println(i5);

		double ddd1 = 0.1;
		System.out.println(ddd1);
		float f1 = 0.1f; // 실수형 리터럴은 기본적으로 double에 들어간다
		System.out.println(f1);

		double d1, d2, d3;
		d1 = 1234567.123456;
		d2 = 1234567.123457;
		d3 = 1234567.123458;
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		
		char mun1 = 'A';
		char mun2 = 'a';
		char mun3 = 90;
		char mun4 = 65;
		System.out.println(mun1);
		System.out.println(mun2);
		System.out.println(mun3);
		System.out.println(mun4);

	}

}
