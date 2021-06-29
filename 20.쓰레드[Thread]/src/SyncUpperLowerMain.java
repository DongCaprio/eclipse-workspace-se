
public class SyncUpperLowerMain {

	public static void main(String[] args) {
		Object monitorObject = new Object();
		
		SyncUpperThread ut = new SyncUpperThread(monitorObject);
		SyncLowerThread lt = new SyncLowerThread(monitorObject);
		ut.start();
		lt.start();
	}

}
