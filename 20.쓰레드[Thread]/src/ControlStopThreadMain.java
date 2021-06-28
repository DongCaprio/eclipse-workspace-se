import javax.swing.JOptionPane;

public class ControlStopThreadMain {

	public static void main(String[] args) {
		System.out.println("1. main");
		ControlStopThread1 cst1 = new ControlStopThread1();
		cst1.start();

		System.out.println("2. main");
		ControlStopThread2 cst2 = new ControlStopThread2();
		cst2.start();
		String inputNo = JOptionPane.showInputDialog("쓰레드를 종료할 방법을 선택하세요[1.stop 2.isPlay변수변경]");
		// 메인쓰레드가 클라이언트 입력받기 위해 쓰레드 대기
		int stopNo = Integer.parseInt(inputNo);
		if (stopNo == 1) {
			cst1.stop(); //호출한 순간 종료 이렇게 하지 말것!
		} else if (stopNo == 2) {
			cst2.setPlay(false); // 조건변경으로 종료 (isPlay->false)
		}						 // 현재진행중인 작업을 "마무리"하고 종료!
		System.out.println("99.main end");

	}

}
