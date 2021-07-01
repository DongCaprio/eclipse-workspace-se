package byte_stream.filter_stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FilterFileCopyMain {

	public static void main(String[] args) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("file_exe.exe"));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("file_exe_copy.exe"));
		int count = 0;    //file_exe랑 file_exe_copy가 없어서 실행은안됌
						// 버퍼를 추가하면 하기 전이랑 실행속도가 100배 이상 차이가남
					//****결론은 꼭 보조스트림을 사용해야 될 꺼 같다.***
		int starCount = 0;
		long startTime = System.currentTimeMillis();
		while (true) {
			int readByte = bis.read();
			System.out.println(readByte);
			if (readByte == -1)
				break;
			bos.write(readByte);
			count++;
			if (count % 1024 == 0) {
				System.out.print("★");
				starCount++;
				if (starCount % 10 == 0) {
					System.out.println();
				}
			}
		} // end while
		bis.close();
		bos.close();

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println();
		System.out.println("FilterFileCopy:" + count + " bytes copy");
		System.out.println("took " + duration + " ms");

	}// end main

}// end class
