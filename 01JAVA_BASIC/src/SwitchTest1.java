
public class SwitchTest1 {

	public static void main(String[] args) {

		int level = 5; // 게임레벨 : 1 ~ 5 사이의 정수
		String msg = "";

		switch (level) {
		case 1:
			msg = "초보";
			break;
		case 2:
			msg = "중수";
			break;
		case 3:
			msg = "고수";
			break;
		case 4:
			msg = "초고수";
			break;
		case 5:
			msg = "신";
			break;
		default : 
			msg = "레벨값의 1~5사이의 정수입니다.";
			break;
		
		}
		System.out.println(msg);

	}

}
