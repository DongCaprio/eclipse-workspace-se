public class ControlPriorityLowerThread extends Thread{
	@Override
	public void run() {
		while(true)
		System.out.print("a");
	}

}
