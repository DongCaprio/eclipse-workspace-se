import java.util.Iterator;

public class ReferenceTypeArrayAccountMain {

	public static void main(String[] args) {
		System.out.println("----------참조형객체의초기화--------");
		Account acc1 = new Account(1111, "KIM", 56000, 0.9);

		Account[] accounts = { acc1, new Account(2222, "PAR", 56000, 0.8), new Account(3333, "CHO", 12000, 1.8),
				new Account(4444, "SIM", 89000, 4.9), new Account(5555, "KIM", 34000, 0.6),
				new Account(6666, "KIM", 77000, 0.1), new Account(7777, "GIM", 11000, 0.3),
				new Account(8888, "QIM", 21000, 2.4), new Account(9999, "AIM", 67000, 3.3), };

		System.out.println("1.은행총계좌수:" + accounts.length);
		System.out.println("2.은행계좌전체출력");
		Account.headerPrint();
		for (int i = 0; i < accounts.length; i++) {
			accounts[i].print();
		}
		System.out.println("3.은행 계좌들 총잔고");
		int totBalance = 0;
		for (int i = 0; i < accounts.length; i++) {
			totBalance += accounts[i].getBalance();
		}
		System.out.println(">> " + totBalance);
		/*
		 * 계좌번호중복안됨
		 */
		System.out.println("4.은행 계좌들중에서 계좌번호 3333번계좌 한개 찾아서 출력");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == 3333) {
				accounts[i].print();
				break;
			}
		}
		System.out.println("5.은행 계좌들중에서 계좌잔고 50000원이상인 VIP계좌 여러개 찾아서 출력");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getBalance() >= 50000) {
				accounts[i].print();
			}
		}
		System.out.println("6.은행 계좌들중에서 계좌이율 2.0 이상인 계좌 여러개 찾아서 출력[Quiz]");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getIyul() > 2.0)
				accounts[i].print();
		}
		System.out.println("7.6666번계좌 3000원입금");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == 6666) {
				accounts[i].print();
				accounts[i].deposit(3000);
				accounts[i].print();

			}
		}
		System.out.println("8.9999번계좌 3000원출금[Quiz]");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == 9999) {
				accounts[i].print();
				accounts[i].withDraw(3000);
				accounts[i].print();
			}
		}
		System.out.println("9.계좌잔고순으로 오름(내림)차순정렬");
		for (int i = 0; i < accounts.length - 1; i++) {
			// 1회
			for (int j = 0; j < accounts.length - 1 - i; j++) {
				if (accounts[j].getBalance() > accounts[j + 1].getBalance()) {
					Account temAccount = accounts[j + 1];
					accounts[j + 1] = accounts[j];
					accounts[j] = temAccount;
				}
			}
		}
		for (int i = 0; i < accounts.length; i++) {
			accounts[i].print();
		}
		System.out.println("99.계좌번호순으로 내림차순정렬[Quiz]");

		for (int j = 0; j < accounts.length - 1; j++) {
			for (int i = 0; i < accounts.length - 1 - j; i++) {
				if (accounts[i].getNo() < accounts[i + 1].getNo()) {
					Account tmp = accounts[i + 1];
					accounts[i + 1] = accounts[i];
					accounts[i] = tmp;
				}
			}
		}
		for (int i = 0; i < accounts.length; i++) {
			accounts[i].print();
		}
		System.out.println("88. 계좌이름순으로 오름(내림)차순 정렬");
		for(int i=0;i<accounts.length-1;i++) {
			boolean isSwap=false;
			for(int j=0;j<accounts.length-1-i;j++) {
				if(accounts[j].getOwner().compareTo(accounts[j+1].getOwner())>0) {
					Account tempAccount=accounts[j+1];
					accounts[j+1]=accounts[j];
					accounts[j]=tempAccount;
					isSwap=true;
				}
			}
			if(!isSwap) {
				break;
			}
		}
		Account.headerPrint();
		for (int i = 0; i < accounts.length; i++) {
			accounts[i].print();
		}
		

		System.out.println("10.5555계좌한개 이율을 3.6으로변경");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == 5555) {
				accounts[i].print();
				accounts[i].setIyul(3.6);
				accounts[i].print();
				break;
			}
		}

		System.out.println("11.VIP계좌(잔고50000원이상) 잔고 50원 증가");
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getBalance() >= 50000) {
				accounts[i].deposit(50);
				accounts[i].print();
			}
		}
	
		
		

	}
}
