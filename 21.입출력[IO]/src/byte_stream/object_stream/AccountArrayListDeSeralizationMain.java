package byte_stream.object_stream;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import byte_stream.filter_stream.Account;

public class AccountArrayListDeSeralizationMain {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ObjectInputStream ois=
				new ObjectInputStream(
						new FileInputStream("accountArrayList.ser"));
		List<Account> accountList=
				(List<Account>)ois.readObject();
		System.out.println("accountArrayList.ser -----> ArrayList<Account");
		Account.headerPrint();
		for (Account account : accountList) {
			account.print();
		}
		ois.close();
	}

}