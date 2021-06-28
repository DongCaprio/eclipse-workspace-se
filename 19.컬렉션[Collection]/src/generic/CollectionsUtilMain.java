package generic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsUtilMain {

	public static void main(String[] args) {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(34);
		intList.add(44);
		intList.add(64);
		intList.add(74);
		intList.add(14);
		intList.add(24);
		intList.add(84);
		
		List<Integer> intList2 = Arrays.asList(34,44,64,74,14,24,84);
		List<String> nameList = Arrays.asList("김태희", "이소라", "유재석","조세호","김경호");
		List<Account> accountList = Arrays.asList(
				new Account(1111, "BING",33000,0.5),
				new Account(2222, "KING",23000,0.1),
				new Account(3333, "KING",89000,0.2),
				new Account(4444, "YONG",12000,0.5),
				new Account(5555, "SANG",99000,0.8)  );
		
		System.out.println("--------------sort------------------");
		System.out.println(intList);
		Collections.sort(intList);
		System.out.println(intList);
		System.out.println(nameList);
		Collections.sort(nameList);
		System.out.println(nameList);
		
		/*
		 * enhanced for
		 */
		for(Account i:accountList) {
			i.print();
		}
		/*
		 * 1. List의 element는 반드시 Comparable interface를 구현해야한다.
		 * 
		 */
		System.out.println();  //이거 Account.java랑 비교해서 살펴보기
		Collections.sort(accountList); //내가 정한 타입의 sort메서드는 컴페어러블 인터페이스를 구현해서 컴페어투 메서드를 재정의해서 사용한다 
		for (Account account : accountList) { //여기서 중요한점은 바꾸고싶으면 return을 양수 안바꾸고 싶으면 return을 음수로 하면 된다.
			account.print();
		}
		System.out.println();
		System.out.println("------------reverse-----------------");
		Collections.reverse(intList);
		Collections.reverse(nameList);
		Collections.reverse(accountList);
		System.out.println(intList);
		System.out.println(nameList);
		System.out.println(accountList);
		System.out.println("--------------shuffle----------------");
		Collections.shuffle(intList);
		Collections.shuffle(nameList);
		System.out.println(intList);
		System.out.println(nameList);
		Collections.reverse(intList);
		Collections.reverse(nameList);
		System.out.println(intList);
		System.out.println(nameList); //reverse메서드는 내림차순이 아니라 미리 선언된것을 거꾸로 바꾸는 작업을 해준다.
		
		
		
	}

}
