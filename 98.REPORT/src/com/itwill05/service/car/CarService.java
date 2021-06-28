package com.itwill05.service.car;

public class CarService {

	private Car[] carArray;

	public CarService() {
		carArray = new Car[30];
	}

	public CarService(int count) {
		carArray = new Car[count];
	}

	/*
	 * 0. 차객체인자로받아서 입차후 성공실패여부반환 - 주차장이 만차이면 입차실패 - 차량번호중복체크
	 */
	public boolean ipCha(Car car) {
		boolean isSuccess = false;
		/*
		 * 1.주차장만차인지체크 2.차량번호 중복체크
		 */
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				System.out.println("만차입니다.");
				break;
			} else if (carArray[i] != null && carArray[i].getNo().equals(car.getNo())) {
				System.out.println("차량번호가 중복되었습니다");
				break;
			} else
				isSuccess = true;
		}
		return isSuccess;
	}

//	1. 전체차량출력
	public void allPrint() {
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				carArray[i].print();
			}
		}
	}

//	2. 주차구획수반환
//	3. 주차가능주차구획수반환
//	4. 차량번호(4567번) 인자받아서 차객체한대 참조변수반환
//	5. 입차시간(8시이후)인자받아서 차객체배열 참조변수반환
//	6. 차량번호(7891번),출차시간(12시)인자로 받아서 출차"

}