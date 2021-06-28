
public class MainThreadCalled {
	public void main_thread_called_method() {
		while(true) {
		System.out.println("\t A."+Thread.currentThread().getName()+" 쓰레드 실행");
		System.out.println("\t B."+Thread.currentThread().getName()+" 쓰레드 반환");
		return;
	}}
}
