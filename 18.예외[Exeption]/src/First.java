import java.io.IOException;

public class First {
	private Second second;
	public First() {
		second=new Second();
	}
	public void method1() throws IOException{
		System.out.println(
				"\t First.method1()실행");
		second.method2();
		System.out.println(
				"\t First.method1()반환");
		return;
	}
}