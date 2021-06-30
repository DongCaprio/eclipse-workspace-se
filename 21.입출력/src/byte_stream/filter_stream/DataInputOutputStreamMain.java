package byte_stream.filter_stream;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataInputOutputStreamMain {

	public static void main(String[] args) throws Exception{
		/*
		 * 자바의 기본데이타를 출력 스트림에 쉽게쓸수있도록하는 필터클래스
		 */
		FileOutputStream fos= new FileOutputStream("dataOut.dat");
		DataOutputStream dos = new DataOutputStream(fos);
		int intData = 2147483647;
		/*
		 *      01111111|11111111|11111111|11111111
		 *         (1)     (2)       (3)      (4) 
		 * >>24 00000000|00000000|00000000|01111111
		 *                                    (1)
		 * >>16 00000000|00000000|01111111|11111111
		 *                           (1)      (2)
		 * >>8  00000000|01111111|11111111|11111111
		 *                  (1)      (2)      (3)
		 *      01111111|11111111|11111111|11111111
		 *         (1)      (2)      (3)      (4)  
		 */
//		fos.write(intData>>24);
//		fos.write(intData>>16);
//		fos.write(intData>>8);
//		fos.write(intData);
		
		dos.writeInt(intData);
		dos.writeByte(127);
		dos.writeBoolean(true);
		dos.writeDouble(3.141592);
		dos.writeChar('가');
		dos.writeUTF("오늘은 자바 입출력을 공부합니다");
		dos.close();
		System.out.println("DataOutputStream.writeXXX()-->dataOut.dat");
		
		System.out.println("-----------------------------------------");
		System.out.println("DataInputStream.readXXX() <-- dataOut.dat");
		DataInputStream dis =
				new DataInputStream(new FileInputStream("dataOut.dat"));
		int readInt = dis.readInt();
		System.out.println("int :"+readInt);
		System.out.println("byte : "+dis.readByte());
		System.out.println("boolean : "+dis.readBoolean());
		System.out.println("double : "+dis.readDouble());
		System.out.println("char : "+dis.readChar());
		System.out.println("String : "+dis.readUTF());
		dis.close(); //쓴 순서대로 읽어야 한다. ***중요***
//		중간에 순서가 바뀌거나 빠진것이 존재하면 정상적으로 읽히지않는다.
		
	}

}