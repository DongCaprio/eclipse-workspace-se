
public class MemoDD {

	public static void main(String[] args) {

		/*
		 * 85. 665;
		 * 1. 소수점 이하 첫째자리 내림 -> 85.6
		 * 2. 소수점 이하 첫째자리 반올림 -> 85.7
		 */
		double ad = 85.665;          
		System.out.println(ad);
		ad = ad * 100;
		System.out.println(ad);
		int ai = (int)ad;
		System.out.println(ai);
		ai = ai +5;
		System.out.println(ad);
		ad = ai / 10.0;
		System.out.println(ad);
		ai = (int)ad;
		System.out.println(ai);
		ad = ai/10.0;
		System.out.println(ad);
		
		//
		
		int number = 16;
		switch (number / 5) {
		case 4 : System.out.println("4다4야");
		break;
		case 3 : System.out.println("3출력된다");
		break;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
