public class AccountConstructorMain {

	public static void main(String[] args) {
		Account acc1=new Account(1111, "KIM", 89000, 0.3);
		Account acc2=new Account(2222, "LIM", 45000, 0.2);
		Account acc3=new Account(3333, "GIM", 23000, 0.1);
		Account acc4=new Account(4444, "FIM", 11000, 1.3);
		Account acc5=new Account(5555, "VIM", 79000, 2.3);
		Account acc6=new Account();
		acc6.setAccountData(6666, "SIX", 66000, 0.6);
		
		System.out.println("------모든계좌잔고 100원증가----");
		acc1.deposit(100);
		acc2.deposit(100);
		acc3.deposit(100);
		acc4.deposit(100);
		acc5.deposit(100);
		acc6.deposit(100);
		
		acc1.headerPrint();
		acc1.print();
		acc2.print();
		acc3.print();
		acc4.print();
		acc5.print();
		acc6.print();
		

	}

}