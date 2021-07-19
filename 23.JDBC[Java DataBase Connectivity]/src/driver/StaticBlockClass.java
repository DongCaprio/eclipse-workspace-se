package driver;

public class StaticBlockClass {
	/* 
	 * << 클래스에 들어갈 수 있는 것 >>
	 * 멤버변수(static, instance)
	 * 멤버 메소드 (static, instance)
	 * 생성자
	 *  // 지금부터 배울것(클래스에 들어갈수있는것) : 정적블록
	 */ 
	
	static {
		System.out.println("클래스로딩시 최초로 실행되는 블록(초기화)");
		System.out.println("클래스로딩시 실행되야되는 코드기술");
		StaticBlockClass sbc = new StaticBlockClass();
		System.out.println("static block에서객체생성"+sbc);
		
	}
}
