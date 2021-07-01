package byte_stream.object_stream;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import byte_stream.filter_stream.Account;

public class ObjectInputStreamMain {

	public static void main(String[] args)throws Exception {
		ObjectInputStream ois=
				new ObjectInputStream(
						new FileInputStream("objects.ser"));
		System.out.println("-------objects.ser-->String---");
		String readString=(String)ois.readObject();
		System.out.println(readString);
		System.out.println("-------objects.ser-->Integer---");
		Integer readInteger=(Integer)ois.readObject();
		System.out.println(readInteger);
		System.out.println("-------objects.ser-->Account---");
		Account readAccount1=(Account)ois.readObject();
		readAccount1.print();
		System.out.println("-------objects.ser-->Account---");
		Account readAccount2=(Account)ois.readObject();
		readAccount2.print();
		
		ois.close();
	}

}