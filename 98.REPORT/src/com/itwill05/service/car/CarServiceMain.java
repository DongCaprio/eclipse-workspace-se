package com.itwill05.service.car;
public class CarServiceMain {
	public static void main(String[] args) {
//		CarService carService=new CarService(30);
		CarService carService = new CarService();
//		carService.
		System.out.println("0.CarService객체야 차객체인자로줄께 입차시켜줘");
		carService.ipCha(new Car("2344",3));
		carService.ipCha(new Car("1221",4));
		carService.ipCha(new Car("2345",5));
		carService.ipCha(new Car("1212",6));
		carService.ipCha(new Car("7891",7));
		carService.ipCha(new Car("5436",8));
		carService.ipCha(new Car("6672",9));
		carService.ipCha(new Car("4567",10));
		boolean isSuccess=carService.ipCha(new Car("2344",10));
		System.out.println(isSuccess);
		
		System.out.println("1.CarService객체야 전체차량출력해줘");
		carService.allPrint();
		System.out.println("2.CarService객체야 전체주차구획수반환해줘");
		int aa=carService.canCar();
		System.out.println(aa);
		
		System.out.println("3.CarService객체야 주차가능주차구획수반환해줘");
		int aaa=carService.cantCar();
		System.out.println(aaa);
		
		System.out.println("4.CarService객체야 차량번호(4567번) 인자로줄께  차객체한대 참조변수반환해줘");
		Car find = carService.chamjoban("4567");
		System.out.println(find);
		find.print();
		
		System.out.println("5.CarService객체야 입차시간(8시이후) 인자로줄께 차객체배열 참조변수반환해줘");
		Car[] nn = carService.ibchaAfter(8);
		System.out.println(nn);
		System.out.println(nn.length);
		for (int i = 0; i < nn.length; i++) {
			nn[i].print();
		}
		System.out.println("6.CarService객체야 차량번호(7891번) 출차시간(12시)인자로줄께 출차시켜주고 출차차량참조변수반환해줘");
		Car a = carService.carOut("7891", 12);
		System.out.println(a);
		a.print();
		
	}

}