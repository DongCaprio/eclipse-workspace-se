public class InterfaceABImpl extends Object implements InterfaceA,InterfaceB {

	@Override
	public void method3() {
		System.out.println("InterfaceB.method3()의 구현(재정의)");
	}

	@Override
	public void method4() {
		System.out.println("InterfaceB.method4()의 구현(재정의)");
		
	}

	@Override
	public void method1() {
		System.out.println("InterfaceB.method1()의 구현(재정의)");
		
	}

	@Override
	public void method2() {
		System.out.println("InterfaceB.method2()의 구현(재정의)");
		
	}

}