
public class ParentChildMain {

	public static void main(String[] args) {
		Child c = new Child();
		c.method1();
		c.method2();
		c.method3();
		c.method4();
		c.method5();
		
		Parent p = c;
		p.method1();
		p.method2();
		p.method3();
		p.method4();
		//p.method5();
	}

}
