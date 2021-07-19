public class ControlPriorityUpperThread extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 300; i++) {
			System.out.print("A");
			if (i % 30 == 0) {
				System.out.println();
			}
		}

	}
}