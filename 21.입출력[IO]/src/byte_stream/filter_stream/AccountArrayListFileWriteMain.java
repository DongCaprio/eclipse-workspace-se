package byte_stream.filter_stream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class AccountArrayListFileWriteMain {
	public static void main(String[] args) throws Exception {
		/*
		 * List<Account>  --> 파일로저장(accountList.dat)[Quiz]
		 */
		List<Account> accountList=Arrays.asList(
				new Account(1111, "KIM", 56800, 0.3),
				new Account(2222, "KIM", 56000, 0.9),
				new Account(3333, "PAR", 12000, 1.9),
				new Account(4444, "CHO", 89000, 4.9),
				new Account(5555, "SIM", 34000, 0.6),
				new Account(6666, "KIM", 77000, 0.1),
				new Account(7777, "KIM", 11000, 0.3),
				new Account(8888, "GIM", 21000, 2.4),
				new Account(9999, "QIM", 99000, 3.3));
		
		DataOutputStream dos =  
				new DataOutputStream(
						new FileOutputStream("accountList.ser"));
		
		for (Account account : accountList) {
			dos.writeInt(account.getNo());
			dos.writeUTF(account.getOwner());
			dos.writeInt(account.getBalance());
			dos.writeDouble(account.getIyul());
		}
		dos.close();
		System.out.println(">>>>List의 Account객체들 --> accountList.dat");
		
		

	}

}