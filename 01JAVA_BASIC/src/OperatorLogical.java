
public class OperatorLogical {

	public static void main(String[] args) {

		boolean b1,b2,b3,b4;
		b1 = true;
		b2 = true;
		b3 = false;
		b4 = false;
		
		boolean result = b1 || b2;
		System.out.println(result);
		result = b1 || b3;
		System.out.println(result);
		result = b1 && b2;
		System.out.println(result);
		result = b1 && b3;
		System.out.println(result);
		
		System.out.println("---------------------------");
		int kor = 90;
		int eng = 25;
		System.out.println("---A대학 국어, 영어 중에서 1과목만 90점 이상이면 합격 ---");
		boolean isPass1 = (kor>=90) ||(eng>=90);
		System.out.println("A대학 합격여부 : "+isPass1);
		System.out.println("---B대학 국어, 영어 중에서 2과목 전부 90점 이상이면 합격 ---");
		boolean isPass2 = (kor >= 90) && (eng >=90);
		System.out.println("B대학 합격여부 : "+ isPass2);
		
		System.out.println("점수의 유효성 체크(0~100)");
		int math = 66;
		boolean isVailed = math <=100 && math >=0;
		System.out.println(isVailed);
		
		System.out.println("----배수판별----");
		int sss = 23;
		boolean samsa = (sss % 3 == 0) && (sss % 4 == 0);
		System.out.println(samsa);
		
		
		/*
		 * 윤년여부판단
		 * 4의 배수이면서 100의 배수가 아니면서 400의 배수인것
		 * 총 3가지의 조건 / 4의배수 / 400의 배수 / 100의 배수는 X
		 */
		int y = 2021;
		boolean isLeapYear=true;
		System.out.println(y+"의 윤년여부 : "+isLeapYear);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
