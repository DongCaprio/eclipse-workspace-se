class MemberMethod {
	public int member1;
	public int member2;
	
	public void method1(){
		System.out.println("-------void method1()실행---------");
		System.out.println("void method1()실행1");
		/*
		 * - 모든 메쏘드의 마지막에는 항상 실행흐름을
		 *   호출한곳(클래스,객체)으로 반환하여야한다.
		 * - return문을 만나면 호출한곳으로 반환된다.
		 * - return type이 void인경우에는 생략이 가능하다.
		 * - return문이 생략된경우에는 자동return된다.
		 */
		return;
	}
	public void method2() {
		System.out.println("--------void method2() 실행--------");
		System.out.println("void method2() 실행1");
		System.out.println("void method2() 실행2");
		return;
	}
	public void method3(int count) {
		System.out.println("------void method3(int count) 실행--------");
		int localVar = 1111;
		System.out.println("method3 블록안에서 선언된변수-->"+localVar);
		for(int i=0;i<count;i++) {
			System.out.println("void method3(int count)실행 파라메타(매개변수) count 값-->"+count);
		}
		return;
	}
	public void method4(String msg,int count) {
		System.out.println("-------void method4(String msg,int count)------");
		System.out.println("msg:"+msg);
		System.out.println("count:"+count);
		for (int i = 0; i < count; i++) {
			System.out.println(msg);
		}
		return;
	}
	public void method5(String name,String msg,int count) {
		System.out.println("-----------void method5(String name,String msg,int count)-------");
		for (int i = 0; i < count; i++) {
			System.out.printf("%s님 %s%n",name,msg);
		}
		return;
	}
	
	
	/*
	 * 멤버변수출력하는기능
	 */
	public void print() {
		/*
		 * this
		 *   - self참조변수
		 *   - print메쏘드를 가지고있는 객체의 주소값
		 */
		System.out.println(this.member1);
		System.out.println(this.member2);
	}
	
	
}

public class MemberMethodMain {
	public static void main(String[] args) {
		MemberMethod mm1=new MemberMethod();
		mm1.member1=88;
		mm1.member2=99;
		System.out.println(mm1.member1);
		System.out.println(mm1.member2);
		mm1.member1=2147483647;
		mm1.member2=-2147483648;
		System.out.println("main stmt1");
		/*
		 * 멤버메쏘드실행(호출)
		 *   - 형식:참조변수.메쏘드이름();
		 *   - mm객체의 method1 메쏘드블록을 실행하기위해
		 *     main실행흐름을 method1블록으로보낸다.
		 */
		mm1.method1();
		System.out.println("main stmt2");
		mm1.method2();
		System.out.println("main stmt3");
		/*
		 
		 MemberMethod 클래스
		 	public void method3(int count){}
		 */
		System.out.println("------------매개변수(파라메타)를 사용한호출-----------");
		mm1.method3(1);
		mm1.method3(2);
		/*
	   	MemberMethod 클래스
		 	public void method4(String msg,int count)
		 */
		mm1.method4("메세지1", 3);
		mm1.method4("메세지2", 2);
		/*
	   	MemberMethod 클래스
		 	void method5(String name,String msg,int count)
		 */
		mm1.method5("김경호", "안녕하세요", 3);
		mm1.method5("이소라", "뭐하세요", 2);
		mm1.method5("김태희", "잘가세요!!", 1);
		
		
		return;
	}
}