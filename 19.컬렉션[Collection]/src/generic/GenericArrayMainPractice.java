package generic;

import java.util.ArrayList;
import java.util.Iterator;

public class GenericArrayMainPractice {

	public static void main(String[] args) {
		Account a1 = new Account(10, "A", 1000, 0.1);
		Account a2 = new Account(20, "A", 2000, 0.2);
		Account a3 = new Account(30, "A", 3000, 0.3);
		Account a4 = new Account(40, "A", 4000, 0.4);
		Account a5 = new Account(50, "A", 5000, 0.5);
		
		ArrayList<Account> accountlist = new ArrayList<Account>();
		System.out.println(accountlist.size());
		accountlist.add(a1);
		accountlist.add(a2);
		accountlist.add(a3);
		accountlist.add(a4);
		accountlist.add(a5);
		System.out.println(accountlist.size());
		
		System.out.println(accountlist);
		accountlist.add(3, new Account(100, "B", 100000, 0.8));
		System.out.println(accountlist);
		accountlist.set(3, new Account(300, "B", 100000, 0.8));
		System.out.println(accountlist);
		accountlist.get(0).print();
		accountlist.get(accountlist.size()-1).print();
		System.out.println("=================================");
		for (Account account : accountlist) {        //향상된 for문의 위력
			account.print();
		}
		System.out.println("=================================");
		for(int i = 0;i<accountlist.size();i++) {
			accountlist.get(i).print();
		}
		System.out.println("=================================");
		//이터레이터
		Iterator<Account> aa = accountlist.iterator();
		while(aa.hasNext()) {
			aa.next().print();  //이터레이터를 활용한 출력 **********위에 3가지 전부 가능!!!
		}						// 이거 굉장히 중요!!!!!!!
		System.out.println("=================================");
		
		ArrayList namem = new ArrayList<String>();
		namem.add("d");
		namem.add("w");
		namem.add("x");
		namem.add("w");
		
		System.out.println(namem.size());
		System.out.println(namem);

		namem.remove(0); //0번째 요소 사라짐
		System.out.println(namem);
		namem.remove("w");
		System.out.println(namem);
		namem.remove("w");
		System.out.println(namem); //앞에요소의 w가 먼저 사라진다
		
		namem.add(3);
		namem.add(2);
		namem.add(1);
		System.out.println(namem);
		namem.remove(new Integer(1)); //이래야 실제값이 정수 1인게 사라진다
		System.out.println(namem);
		namem.remove(1);  // 단순히 컬렌션의 1번째 값 삭제
		System.out.println(namem);

		
		
		
		
		
		
		
		
		
		
	}

}
