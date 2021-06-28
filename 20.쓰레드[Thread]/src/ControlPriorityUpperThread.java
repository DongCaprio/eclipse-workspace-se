public class ControlPriorityUpperThread extends Thread {
	@Override
	public void run() {
		while(true)
		System.out.print("A");
	}
}