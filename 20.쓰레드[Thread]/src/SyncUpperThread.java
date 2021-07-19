public class SyncUpperThread extends Thread {
	private Object monitorObject;
	public SyncUpperThread(Object monitorObject) {
		this.monitorObject = monitorObject;
	}
	@Override
	public void run() {

		for(int i =0 ;i<100; i++){
//			System.out.println("\n--> Upper Thread 동기화블록 실행전");
			/****************************/
			synchronized (monitorObject) {
				System.out.print("A");
				System.out.print("B");
				System.out.print("C");
				System.out.print("D");
				System.out.print("E");
				System.out.print("F");
				System.out.print("G");
				System.out.print("H");
				System.out.print("I");
				System.out.print("J");
				System.out.print("K");
				System.out.print("L");
				System.out.print("M");
				System.out.print("N");
				System.out.print("O");
				System.out.print("P");
				System.out.print("Q");
				System.out.print("R");
				System.out.print("S");
				System.out.print("T");
				System.out.print("U");
				System.out.print("V");
				System.out.print("W");
				System.out.print("X");
				System.out.print("Y");
				System.out.print("Z");
			}
//			System.out.println("\n-->Upper Thread 동기화블록 실행 후");
		}

	}
}