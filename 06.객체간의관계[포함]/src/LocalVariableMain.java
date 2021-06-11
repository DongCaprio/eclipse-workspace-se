public class LocalVariableMain {

	public static void main(String[] args) {
		/*
		 * 지역변수(local variable)
		 *   - 메쏘드블록안에서 선언된변수(매개변수)
		 * 멤버필드(변수)
		 *   - 클래스블록안에 선언된변수 
		 */
		int a=9;
		int b=10;
		
		int t=a;
		a = b;
		b=t;
		System.out.println("a="+a);
		System.out.println("b="+b);
		int tot;
		/*
		1.<< The local variable tot may not have been initialized >>
		System.out.println("local variable tot:"+tot);
		 
		2.<< The local variable tot may not have been initialized >> 
		boolean condition=true;
		if(condition) {
			tot=0;
		}
		System.out.println("local variable tot:"+tot);
		*/
		
		Member m=null;
		
		if(m==null) {
			m=new Member(10000, "일만이");
		}
		m.print();
		//The local variable m may not have been initialized
		m.method(11);
		
		
		
	}

}
