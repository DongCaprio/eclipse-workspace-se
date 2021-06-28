import java.util.Date;

public class ControlSleepThread extends Thread{
	
	@Override
	public void run()  {
		
		while(true) {
			try {
				System.out.println(
						"ControlSleepThread-->1초마다 1번씩출력");
				Date now=new Date();
				System.out.println(now.toLocaleString());
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}