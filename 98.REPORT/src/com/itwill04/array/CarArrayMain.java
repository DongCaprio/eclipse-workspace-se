package com.itwill04.array;

/*
 * 주차관리프로그램
 */
public class CarArrayMain {

	public static void main(String[] args) {
		Car[] carArray = { null, null, new Car("5654", 8), null, null, new Car("3422", 12), null, null, null, null,
				null, null, null, null, new Car("7789", 11), new Car("1120", 10), null, null, null, null, null,
				new Car("2389", 9), null, null, null, null, null, null, null, new Car("5555", 6) };

		System.out.println("1.전체 차량출력");
		Car.headerPrint();
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				carArray[i].print();
			}
		}
		System.out.println("2.전체주차구획수");
		int canNo = 0;
		for (int j = 0; j < carArray.length; j++) {
			if (carArray[j] != null) {
				canNo++;
			}
		}
		System.out.println(canNo);

		System.out.println("3.주차가능주차구획수");
		int cantNo = 0;
		for (int j = 0; j < carArray.length; j++) {
			if (carArray[j] != null) {
			} else {
				cantNo++;
			}
		}
		System.out.println(cantNo);
		System.out.println();

		System.out.println("4.입차");
		/*
		 * 1.차량객체생성 2.빈자리(null)찾아서대입
		 */
		int random = (int) (Math.random() * 30);
		System.out.println("주차자리는 " + random); // 주차는 내가 원하는곳 아무대나 할수있음으로 random을 사용해서 배열의 0~29번째중 아무데나 들어가게함
		Car myCarr = new Car("7272", 6);
		if (carArray[random] == null) {
			carArray[random] = myCarr;
		}
		System.out.println("7272 입차확인용 출력");
		Car.headerPrint();
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				carArray[i].print();
			}
		}
		System.out.println();

		System.out.println("5.차량번호 7789번  차한대 정보출력");
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null && carArray[i].getNo().equals("7789")) { // &&앞뒤의 위치가 바뀌면 컴파일이 안되는데 왜그런지 모르겠다.
				carArray[i].print(); // 굳이 != null 조건이 왜 있어야하는지 모르겠다. 이거없이도 7789일때만 출력하라인데 왜..
			}
		}
		System.out.println();
		System.out.println("6.입차시간 10시이후 차량여러대 찾아서 정보출력");
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null && carArray[i].getInTime() >= 10) {
				carArray[i].print(); // 이것도 마찬가지로 null이 아닐때를 왜 추가?
			}
		}
		System.out.println();
		System.out.println("7.2389번차량 12시 출차");
		/*
		 * 1. 2389번차량찾기 2. 출차 3. 영수증출력 4. 주차에서 차량삭제
		 */
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null && carArray[i].getNo().equals("2389")) {
				carArray[i].print(); // 차량찾기
				carArray[i].setOutTime(12); //출차시간설정
				carArray[i].calculateFee(); //돈계산하고
				Car.headerPrint(); //헤더찍고
				carArray[i].print(); //출력하고
				carArray[i] = null; //차있던 자리를 null로 초기화한다(삭제작업)
			}

		}
		System.out.println();
		System.out.println("--------출차를 하고 난후 확인용--------"); //그 후 제대로 차가 출차했나 확인용으로 다시한번 전체 주차장상황을 찍어본다
		Car.headerPrint();
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				carArray[i].print();
			}
		} //OK

	}

}