/*
 인터페이스
    1. 클래스가가진 모든 메쏘드가 추상메쏘드임
    2. 다중상속의 효과를 냄
       (클래스는 불가능하나 인터페이스는가능하다)
   -형식
     * class keyword 대신에 interface 라는 keyword를사용
     * 추상메쏘드앞에 abstract 를 붙히지않는다.
     ex>public interface Test{
     		public void method1();
     		public void method2();
        }
   - 사용
       1. interface를 상속(implements)받아서
       2. 추상메쏘드를 재정의(구현)한후 사용한다.
       
       ex> public class TestImpl implements Test{
       			public void method1(){}
     			public void method2(){}
          }
*/
public class interfaceABImplMain {

	public static void main(String[] args) {
		System.out.println("---------------InterfaceABImpl----------------------");
		InterfaceABImpl abImpl = new InterfaceABImpl();
		abImpl.method1();
		abImpl.method2();
		abImpl.method3();
		abImpl.method4();
		System.out.println("---------------Object----------------");
		Object o = abImpl;
//		o.method1();
//		o.method2();
//		o.method3();
//		o.method4();
		System.out.println("---------------InterfaceA----------------");
		InterfaceA ia = abImpl;
		ia.method1();
		ia.method2();
//		ia.method3();
//		ia.method4();
		System.out.println("---------------InterfaceB-----------------");
		InterfaceB ib = abImpl;
//		ib.method1();
//		ib.method2();
		ib.method3();
		ib.method4();
		System.out.println("============= InerfaceA<--->InterfaceB =========");
		InterfaceA ia2 = new InterfaceABImpl();
		ia2.method1();
		ia2.method2();
		InterfaceB ib2 = (InterfaceB)ia2;
		//ib2.method1();
		ib2.method3();
		ib2.method4();

		
	}

}
