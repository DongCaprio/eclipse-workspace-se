
public class AMain {

	public static void main(String[] args) {

		A aname = new A();
		A aname2 = new A();
		
		aname.setX(10);
		aname.setY(100);
		
		aname2.setX(222);
		aname2.setY(2222);
		
		System.out.println(aname.getX());
		System.out.println(aname2.getY());
		System.out.println(aname.getX());
		System.out.println(aname2.getY());
		aname.m1();
		aname2.m1();
		aname.m2();
		aname2.m2();
	
	}

}
