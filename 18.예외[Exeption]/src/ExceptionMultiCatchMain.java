import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ExceptionMultiCatchMain {
	/*
	public static void main(String[] args){
		try {
			System.out.println("stmt1");
			Class.forName("First");
			System.out.println("stmt2");
			Socket socket=new Socket("www.naver.com",80);
			String str=null;
			str.length();
			
			System.out.println("stmt3");
			First first=new First();
			first.method1();
			System.out.println("stmt4 return");
			
		}catch (ClassNotFoundException e) {
			System.out.println("catch ClassNotFoundException start");
			System.out.println("catch msg:"+e.getMessage());
			System.out.println("catch ClassNotFoundException end");
			JOptionPane.showMessageDialog(null, e.getMessage());
		}catch (IOException e) {
			System.out.println("catch IOException start");
			System.out.println("catch msg:"+e.getMessage());
			System.out.println("catch IOException end");
			JOptionPane.showMessageDialog(null, e.getMessage());
		}catch (Exception e) {
			System.out.println("catch Exception start");
			System.out.println("catch msg:"+e.getMessage());
			System.out.println("catch Exception end");
			JOptionPane.showMessageDialog(null, "잘모르는애로사항발생!!!");
			
		}
	
	}
	*/
	public static void main(String[] args){
		try {
			System.out.println("stmt1");
			Class.forName("First");
			System.out.println("stmt2");
			Socket socket=new Socket("www.naver.com",80);
			String str="";
			str.length();
			System.out.println("stmt3");
			First first=new First();
			first.method1();
			System.out.println("stmt4 return");
			
		}catch (Exception e) {
			System.out.println("catch Exception start");
			System.out.println("catch msg:"+e.getMessage());
			System.out.println("catch Exception end");
			JOptionPane.showMessageDialog(null, "애로사항발생!!!");
			
		}
	
	}
}