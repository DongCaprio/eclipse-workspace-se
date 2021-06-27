import java.util.HashSet;
import java.util.Iterator;

import generic.Account;

public class GenericHashSetMainPractice {

	public static void main(String[] args) {
		HashSet<Account> qwer= new HashSet<Account>();
		Account acc1=new Account(1111, "BING",33000,0.5);
		Account acc2=new Account(2222, "KING",23000,0.1);
		Account acc3=new Account(3333, "KING",89000,0.2);
		Account acc4=new Account(4444, "YONG",12000,0.5);
		Account acc5=new Account(5555, "SANG",99000,0.8);
		
		qwer.add(acc5);
		qwer.add(acc4);
		qwer.add(acc3);
		qwer.add(acc2);
		qwer.add(acc1);
		for(Account i : qwer) {
			i.print();
		}
		
		System.out.println(qwer.add(acc5));
		
		Iterator<Account> aa = qwer.iterator();
		while(aa.hasNext()) {
			aa.next().print();
		}
		System.out.println("----------------------------");
		
		qwer.add(new Account(1234, "A", 33, 0.6));
		for(Account i : qwer) {
			i.print();
		}
		System.out.println("----------------------------");
		qwer.remove(acc5);
		for(Account i : qwer) {
			i.print();
		}
		//이터레이터는 왜 쓰는것일까
		
		
	}

}
