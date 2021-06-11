
public class ReturnMain {

	public static void main(String[] args) {
		
		int eng = -45;
//유효성 체크
		if (eng < 0 || eng > 100) {
			System.out.println("점수는 0~100사이의 정수여야합니다.");
			return;
		}
		System.out.println("당신의 학점은 A+입니다.");
		
		

		System.out.println("dd");
	}

}
