public class CreateCustomThreadMain {

	public static void main(String[] args) {
		System.out.println("1.main thread");
		/*
		1. Thread 클래스를 상속받는다.
		2. Thread class의 run method를 overriding 한다.
		  메쏘드원형:public void run()
		3. Thread 객체를 생성한다.
		4. Thread 객체를통해서 Thread를 시작시킨다.
		 */
		CreateCustomThread cct=new CreateCustomThread();
		cct.setName("mySuperUltraThread");
		/*
		- Causes this thread to begin execution; 
				the Java Virtual Machine calls the run method of this thread.
		- The result is that two threads are running concurrently: 
		- the current thread (which returns from the call to the start method) 
			and the other thread (which executes its run method).
		 */
		cct.start();
		while(true) {
			System.out.println("2.main thread");
		}
	}

}
