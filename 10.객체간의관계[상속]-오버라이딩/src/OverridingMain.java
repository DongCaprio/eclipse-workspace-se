/*
 * 오버라이딩(재정의)
 *   1. 상속관계에서 발생하는 메쏘드의 재정의 기법이다
 *   2. 자바의 다형성 기법중의하나이다.
 *   
 *   -형태 : 부모클래스에정의 된 메쏘드의 이름,리턴타입,인자가
 *          동일한 메쏘드를 자식클래스에 정의(재정의)하는것
 * 
 *   - ex> public class Super{
 *   			public void print(){
 *   			}		
 *   		}
 *        
 *        public class Sub extends Super{
 *        		public void print(){
 *        		}
 *        }
 */
class OverrideingParant {
	public OverrideingParant() {
		System.out.println("기본생성자를 꼭만들어봐요");
	}
	public void method1() {
		System.out.println("OverridingParent.method1()");
	}

	public void method2() {
		System.out.println("OverridingParent.method2()");
	}
}

class OverridengChild extends OverrideingParant {
	/*
	 * public void method1() { System.out.println("OverridingParent.method1()"); }
	 * public void method2() { System.out.println("OverridingParent.method2()"); }
	 */
	public void method2() {
		System.out.println("---------OverridingChild에서 재정의된 method2() start------------------");

		System.out.println("---------OverridingChild에서 재정의된 method2() end--------------------");
		/*
		 * 재정의하면 -상속받은 method2()는 은폐(호출불가) -자식에서 재정의한 method2() 만 호출된다.
		 */
	}

	public void method3() {
		System.out.println("OverridingChild.method3()");
	}
}

public class OverridingMain {

	public static void main(String[] args) {
		OverridengChild oc = new OverridengChild();
		oc.method1();
		oc.method2();
	}

}
