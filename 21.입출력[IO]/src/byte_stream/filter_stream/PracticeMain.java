package byte_stream.filter_stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PracticeMain {

	public static void main(String[] args) throws Exception{
		BufferedInputStream bb = 
				new BufferedInputStream(new FileInputStream("file_exe.exe"));
		
		BufferedOutputStream bo =
				new BufferedOutputStream(new FileOutputStream("file_exe_copy"));
		
		int count = 0;
		long sT = System.currentTimeMillis();
		while(true) {
			int readByte = bb.read();
			if(readByte == -1) {
				break;
			}
			bo.write(null);
		}
		
	}

}
