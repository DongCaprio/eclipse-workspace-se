
public class ControlPriorityLowerUpperMain {

	public static void main(String[] args) {
		System.out.println("1. main");
		ControlPriorityUpperThread ut = new ControlPriorityUpperThread();
		ControlPriorityLowerThread lt = new ControlPriorityLowerThread();
		/*
		 * priority
		 *  -CPU의 점유권을 얻을수있는 우선순위
		 *  -1~10까지의 우선순위를 가짐
		 */
		System.out.println("ControlPriorityUpperThread--> "+ut.getPriority());
		System.out.println("ControlPriorityLowerThread--> "+lt.getPriority());
		
		ut.setPriority(10);
		lt.setPriority(1);
		
		ut.start();
		lt.start();
		
		System.out.println("99.main return");
	}

}