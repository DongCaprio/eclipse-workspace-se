
public class FianlFieldMain {

	public static void main(String[] args) {
		FinalField ff1 = new FinalField();
//		ff1.INCETIVE_RATE = 0.5;
//		ff1.PORT_NUMBER = 8080;
		System.out.println(ff1.INCETIVE_RATE);
		
		ff1.move(FinalField.NORTH);
		ff1.move(FinalField.EAST);
		ff1.move(FinalField.WEST);
		ff1.move(FinalField.SOUTH);
		
		
	}

}
