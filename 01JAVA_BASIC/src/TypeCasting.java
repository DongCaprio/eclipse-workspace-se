
public class TypeCasting {
	
	public static void main(String[] args) {
	
		int i1 = 56;
		long l1 = i1;
		System.out.println(l1);
		double d1 = i1;
		System.out.println(d1);
		
		char c1 = '김';
		int i2 = c1;
		System.out.println(c1);
		System.out.println(i2);
		
		double aavgd = 88.567;
		int avgi = (int)aavgd;
		System.out.println(aavgd);
		System.out.println(avgi);
		
		//연산시의 형변환
		// 연산항들중 가장 큰 타입으로 모든항이 형변환된다.(promotion)
		int ii = 100;
		char cc = 'A';
		long ll = 45L;
		double dd = 45.26;
		
		 //ir = ii+cc+ll;
		
		
		System.out.println(ii+cc+dd);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// double avgd = 86.665;
		// quiz 1. 소수점 이하 첫째자리를 남겨두고 casting -> 85.6
		// quiz 2. 소수점이하 둘째자리에서 반올림 -> 85.7
		// 하는 방법은?
		
		
	}
	
}
