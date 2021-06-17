package com.itwill04.array;
/*
 * 주차관리프로그램
 */
public class CarArrayMain {

	public static void main(String[] args) {
		Car[] carArray= {
				null,null,new Car("5654", 8),null,null,
				new Car("3422", 12),null,null,null,null,
				null,null,null,null,new Car("7789", 11),
				new Car("1120", 10),null,null,null,null,
				null,new Car("2389", 9),null,null,null,
				null,null,null,null,new Car("5555", 6)
		};
		System.out.println("1.전체 차량출력");
		Car.headerPrint();
		for (int i = 0; i < carArray.length; i++) {
			if(carArray[i]!=null) {
				carArray[i].print();
			}
		}
		System.out.println("2.전체주차구획수");
		int canNo = 0;
		for (int j = 0; j < carArray.length; j++) {
			if(carArray[j]!=null) {
				canNo++;
			}
		}
		System.out.println(canNo);
		
		System.out.println("3.주차가능주차구획수");
		int cantNo = 0;
		for (int j = 0; j < carArray.length; j++) {
			if(carArray[j]!=null) {
			} 
			else { cantNo++;
			}
		}
		System.out.println(cantNo);
		
		System.out.println("4.입차");
		/*
		 * 1.차량객체생성
		 * 2.빈자리(null)찾아서대입
		 */
		carArray[5] = new Car("7272", 2);
		carArray[5].print();
		
		System.out.println("5.차량번호 7789번  차한대 정보출력");
		
		System.out.println("6.입차시간 10시이후 차량여러대 찾아서 정보출력");
		System.out.println("7.2389번차량 12시 출차");
		/*
		 * 1. 2389번차량찾기
		 * 2. 출차
		 * 3. 영수증출력
		 * 4. 주차에서 차량삭제
		 */
		
		
	}

}