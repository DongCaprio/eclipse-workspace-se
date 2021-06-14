
public class AMain {

	public static void main(String[] args) {

		A aname = new A();
		A aname2 = new A();
		
		aname.setGaga(10);
		aname.setNana(100);
		
		aname2.setGaga(222);
		aname2.setNana(2222);
		
		aname.printGaGa();
		aname.printNaNa();
		aname2.printGaGa();
		aname2.printNaNa();
	
	}

}
