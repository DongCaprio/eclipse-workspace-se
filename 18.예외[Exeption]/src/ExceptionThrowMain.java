import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExceptionThrowMain {
	/*
	public static void main(String[] args) throws ClassNotFoundException, UnknownHostException, IOException {
		System.out.println("stmt1");
		Class.forName("First");
		System.out.println("stmt2");
		Socket socket=new Socket("www.naver.com",80);
		System.out.println("stmt3");
		First first=new First();
		first.method1();
		System.out.println("stmt4 return");
		
	}
	*/
	public static void main(String[] args) throws Exception {
		System.out.println("stmt1");
		Class.forName("First");
		System.out.println("stmt2");
		Socket socket=new Socket("www.naver.com",80);
		System.out.println("stmt3");
		First first=new First();
		first.method1();
		System.out.println("stmt4 return");
		
	}
}