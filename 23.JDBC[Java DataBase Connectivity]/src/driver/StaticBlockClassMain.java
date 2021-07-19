package driver;

public class StaticBlockClassMain {

	public static void main(String[] args) throws Exception{
		/*
		 * Class loading..
		 * 		-생성자호출
		 * 		-Class.forName("class name");
		 * 
		 */
//		new StaticBlockClass();
		String classname = "basic.StaticBlockClass";
		Class.forName("basic.StaticBlockClass");
		
	}

}
