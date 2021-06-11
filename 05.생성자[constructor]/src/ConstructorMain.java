public class ConstructorMain {

	public static void main(String[] args) {
		
		Constructor c1 = new Constructor();
		/*
		 << new Constructor(); >>
		   1.Constructor클래스(틀)로 객체생성
		   2.생성자호출
		   3.객체의주소반환
		 */
		System.out.println("c1객체주소:-->"+c1);
		c1.print();
		
		Constructor c2=new Constructor();
		System.out.println("c2객체주소:-->"+c2);
		c2.print();
		
		Constructor c3=new Constructor(800, 900);
		c3.print();
		c3.setConstructorData(888, 999);
		c3.print();
		System.out.println("-------기본생성자---------");
		DefaultConstructor dc=new DefaultConstructor();
		System.out.println(dc);
	}

}
