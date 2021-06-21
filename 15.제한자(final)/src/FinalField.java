/*
제한자(final)
	1. 클래스앞에 붙일경우
	   상속금지 
       ex> public final class Test{}
    2. 멤버메쏘드앞에 붙일경우 
       오버라이딩 금지
       ex> public final void print(){}
    3. 멤버변수 앞에붙일경우--> 상수
       ex> public final int PORT_NUMBER=80;
       상수화된다.    
       변경금지
*/

public class FinalField {
	public final int PORT_NUMBER = 80;
	public final double INCETIVE_RATE;
	
	public final static int NORTH =0;
	public final static int SOUTH =1;
	public final static int EAST =22;
	public final static int WEST =3;
	
	public FinalField() {
		INCETIVE_RATE=0.1;
		System.out.println(PORT_NUMBER);
		System.out.println(INCETIVE_RATE);
		
//		FinalField.NORTH = 1;
//		FinalField.SOUTH = 1;
		System.out.println(FinalField.SOUTH);
		
	}
	public void move(int direction) {
		switch (direction) {
		case FinalField.NORTH :
			System.out.println("move north");
			break;
		case FinalField.SOUTH:
			System.out.println("move south");
			break;
		case FinalField.WEST:
			System.out.println("move west");
			break;
		case FinalField.EAST:
			System.out.println("move east");
			break;
		}
	}
}
