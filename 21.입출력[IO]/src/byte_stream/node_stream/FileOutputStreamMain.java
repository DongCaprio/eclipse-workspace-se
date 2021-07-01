package byte_stream.node_stream;

import java.io.FileOutputStream;

/*
 * 
 * <<출력스트림(OutputStream)사용방법>>
 * 1. 데이타 목적지(target,destination) 선정(파일)
 * 2. 출력스트림(OutputStream)객체생성(FileOutputStream)
 * 3. 출력스트림(OutputStream)에 한바이트씩 쓴다(write)
 * 4. 출력스트림(OutputStream)닫는다.(close) 
 */
 

public class FileOutputStreamMain {

	public static void main(String[] args) throws Exception{
		FileOutputStream fos=new FileOutputStream("fileOut.dat");
		/*
		public abstract void write(int b) throws IOException;
		 	- Writes the specified byte to this output stream.
		 	- The general contract for write is that one byte is written to the output stream.
		 	- The byte to be written is the eight low-order bits of the argument b.
		 	- The 24 high-order bits of b are ignored.
			- Subclasses of OutputStream must provide an implementation for this method. 
		 */
		/*
		 00000000|00000000|00000000|01000001
		 */
		fos.write(65);
		/*
		 00000000|00000000|00000000|01000010
		 */
		fos.write(66);
		 /*
		 01111111|11111111|11111111|11111111
		 */
		fos.write(2147483647);
		/*
		 * 00000000|00000000|00000000|00000000 -->0
		 *  ~
		 * 00000000|00000000|00000000|11111111 -->255
		 */
		for(int i =0;i<256;i++) {
			fos.write(i);
		}
		fos.close();
		System.out.println("FileOutputStream.write --> fileOut.dat");
	}

}