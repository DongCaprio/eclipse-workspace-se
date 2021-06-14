package lang;

public class MathMain {

	public static void main(String[] args) {
		//Maht m = new Math();
		/*
		Math 클래스
		   - 수학함수를 가지고있는클래스
		   - 모든 메쏘드 static 이다.
		   - 객체생성이 불가능
		      (생성자 접근제한자 private)
		The constructor Math() is not visible
		Math math=new Math();
		 */
		double result = Math.abs(-8989.111);
		System.out.println(result);
		System.out.println(Math.max(23, 45));
		System.out.println(Math.min(23, 45));
		System.out.println(Math.round(33.55645));
		System.out.println(Math.round(33.55645*10)/10.0);
		System.out.println(Math.sqrt(2));
		System.out.println(Math.sin(Math.PI/2));
		System.out.println();
		
		
		// public static double random()
		System.out.println(Math.random());
		System.out.println("1~10사이의 정수 랜덤 생성");
		System.out.println((int)(Math.random()*10)+1);
		System.out.println((int)(Math.random()*10)+1);
		System.out.println((int)(Math.random()*10)+1);
		System.out.println((int)(Math.random()*10)+1);
		System.out.println((int)(Math.random()*10)+1);
		System.out.println("1~45");
		System.out.println((int)(Math.random()*45)+1);
		System.out.println((int)(Math.random()*45)+1);
		System.out.println((int)(Math.random()*45)+1);
		System.out.println((int)(Math.random()*45)+1);
		System.out.println((int)(Math.random()*45)+1);
	}

}
