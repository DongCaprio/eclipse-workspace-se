package com.itwill05.service.car;

public class CarService {

	private Car[] carArray;

	public CarService() {
		carArray = new Car[30];
		for (int i = 0; i < carArray.length; i++) {
			carArray[i] = null;
		}
	}

	public CarService(int count) {
		carArray = new Car[count];
	}

	/*
	 * 0. 차객체인자로받아서 입차후 성공실패여부반환 - 주차장이 만차이면 입차실패 - 차량번호중복체크
	 */

	public boolean ipCha(Car car) {
		boolean isSuccess = false;
		boolean isEqualsNo = false;

		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) { // 만차 체크
				isEqualsNo = carArray[i].getNo().equals(car.getNo()); // 같을 경우 trure
			}
		}
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] == null) { // 만차 체크
				if (!isEqualsNo) {
					carArray[i] = car;
					isSuccess = true;
					break;
				} else {
					System.out.println("차량 중복");
				}
			}
		}
		return isSuccess;
	}

//	1. 전체차량출력
	public void allPrint() {
		Car.headerPrint();
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				carArray[i].print();
			}
		}
	}

//	2. 주차구획수반환
	public int canCar() {
		int num = 0;
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null) {
				num++;
			}
		}

		return num;
	}

//	3. 주차가능주차구획수반환
	public int cantCar() {
		int num = 0;
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] == null) {
				num++;
			}
		}

		return num;
	}

//	4. 차량번호(4567번) 인자받아서 차객체한대 참조변수반환
	public Car chamjoban(String no) {
		Car findcar = null;
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] != null && carArray[i].getNo().equals(no)) {
				findcar = carArray[i];
				break;
			}
		}
		return findcar;
	}

//	5. 입차시간(8시이후)인자받아서 차객체배열 참조변수반환
	public Car[] ibchaAfter(int clock) {
		Car[] carr = null;
		
		int num = 0;
		for (int i = 0; i < carArray.length; i++) {
			if (carArray[i] !=null && carArray[i].getInTime()>=8) {
				num++;
			}
		}
		
		carr = new Car[num];
		for (int i = 0, j= 0; i < carArray.length; i++) {
			if (carArray[i] !=null && carArray[i].getInTime()>=8) {
				carr[j] = carArray[i];
				j++;
			}
		}
		return carr;
	}
	
	
//	6. 차량번호(7891번),출차시간(12시)인자로 받아서 출차"
	public Car carOut(String s, int n) {
		Car ccc = null;
		for (int i = 0; i < carArray.length; i++) {
			if(carArray[i] != null && carArray[i].getNo().equals(s)) {
				carArray[i].setOutTime(n);
				carArray[i].calculateFee();
				ccc = carArray[i];
				carArray[i]=null;
				break;
			}
			
		}
		
		return ccc;
	}
	
	

}