package char_stream;

import java.io.FileReader;
import java.io.FileWriter;

public class FileReaderWriterMain {

	public static void main(String[] args) throws Exception {
		FileWriter fw=new FileWriter("fileWriter.txt");
		/*
		 public void write(int c) throws IOException;
		  - Writes a single character. 
		  - The character to be written is contained 
		     in the 16 low-order bits of the given integer value; 
		     the 16 high-order bits are ignored.
		  - Subclasses that intend to support efficient single-character output 
		      should override this method.
		 */
		
		fw.write(44456);
		fw.write('A');
		fw.write('김');
		for (int i = 0; i < 65536; i++) {
			fw.write(i);
			if(i%100==0) {
				fw.write('\n');
			}
		}
		fw.write('\n');
		fw.write("문자열을 맘대로써요");
		fw.write("\n");
		
		fw.flush();
		fw.close();
		System.out.println("-------------FileWriter.write()-->fileWriter.txt-----------");
		FileReader fr=new FileReader("fileWriter.txt");
		/*
		public int read() throws IOException
		
		- Reads a single character. 
		- This method will block until a character is available, an I/O error occurs, or the end of the stream is reached.
		- Subclasses that intend to support efficient single-character input should override this method.
		Returns:
			The character read, as an integer in the range 0 to 65535 (0x00-0xffff),
			 or -1 if the end of the stream has been reached
		 */
		
		int readChar=fr.read();
		
		System.out.println("1.readChar:"+(char)readChar);
		readChar=fr.read();
		System.out.println("2.readChar:"+(char)readChar);
		int lineCount=0;
		while(true) {
			readChar=fr.read();
			if(readChar==-1)break;
			System.out.print((char)readChar);
			if(readChar=='\n') {
				lineCount++;
			}
		}
		fr.close();
		System.out.println();
		System.out.println("FileReader.read()-->"+lineCount+ "라인읽음");;
	}

}