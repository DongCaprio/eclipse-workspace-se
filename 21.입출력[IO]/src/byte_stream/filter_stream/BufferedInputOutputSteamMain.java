package byte_stream.filter_stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BufferedInputOutputSteamMain {

	public static void main(String[] args) throws Exception {

		FileOutputStream fos = new FileOutputStream("bufferedOut.dat");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(0b0000001110010);
		bos.write(89);
		bos.write(23);
		for (int i = 0; i < 280; i++) {
			bos.write(i);
		}
		bos.flush();
		bos.close();
		System.out.println("BufferedOutputStream.write --> bufferedOut.dat ");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("bufferedOut.dat"));
		while (true) {
			int readByte = bis.read();
			System.out.print(readByte+"*");
			if (readByte == -1)
				break;
//			System.out.print((char)readByte);
		}
		bis.close();
		
		System.out.println("bufferedInputStream.read <-- bufferedOut.dat");
	}
}