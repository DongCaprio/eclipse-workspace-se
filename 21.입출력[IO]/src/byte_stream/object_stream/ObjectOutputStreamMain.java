package byte_stream.object_stream;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import byte_stream.filter_stream.Account;

public class ObjectOutputStreamMain {

	public static void main(String[] args)throws Exception {
		/*
		An ObjectOutputStream writes primitive data types 
		  and graphs of Java objects to an OutputStream
		 */
		ObjectOutputStream oos=
				new ObjectOutputStream(
						new FileOutputStream("objects.ser"));
		System.out.println("----------String Object write-------------");
		String str1=new String("객체스트림");
		oos.writeObject(str1);
		System.out.println("----------Integer Object write-------------");
		Integer wi2=new Integer(2147483647);
		oos.writeObject(wi2);
		System.out.println("----------Account1 Object write-------------");
		Account account3=new Account(3434, "김경수",63000, 0.01);
		/*
		 * 직렬화객체조건
		 *   1.객체가 반드시 java.io.Serializable interface를 구현해야한다.
		 *   2.java.io.Serializable interface는 추상메쏘드가 존재하지않는 인터페이스(falg interface)이다.
		 * 
		 */
		oos.writeObject(account3);
		System.out.println("----------Account2 Object write-------------");
		Account account4=new Account(6776, "김성호", 99000, 0.3);
		oos.writeObject(account4);
		oos.flush();
		oos.close();
		System.out.println("---------ObjectOutputStream.writObject --> object.ser-------");
		
		
	}

}