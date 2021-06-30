package byte_stream.filter_stream;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class PrintStreamMain {

	public static void main(String[] args) throws Exception {
//		PrintStream
//		자바의 모든데이터를 문자로 출력하는 기능(필터)을 하는 스트림
		PrintStream ps = new PrintStream(new FileOutputStream("print.txt"));
		
		ps.write(65);
		ps.write('A');
		
		ps.print(65);
		ps.write('6');
		ps.write('5');
		
		ps.print(123456789); //==ps.write('1') ~ ps.write('9');
		
		ps.print(true);
		//ps.write('t'); ~ ps.write('e');
		
		ps.print(3.144592);
		//ps.write('3')  ps.write('.') ~ ps.write('2');
		
		ps.print(12345);
		ps.write(49);
		ps.write(50);
		ps.write(51);
		ps.write(52);
		ps.write(53); //12345 print의 실제모습
		System.out.println("------------PrintStream.print() --> print.txt-----------");
		
		
		
		System.out.println();
	}

}
