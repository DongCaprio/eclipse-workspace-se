
public class FinalClassChild extends FinalClass {
	
	@Override
	public void method1() {
		System.out.println("FinalClassChild에서"
				+ " FinalClass.method1()재정의ㅣ");
	}
//	public void method2()	{
//		method2는 final이므로 재정의 불가
//	}
	
	
}
