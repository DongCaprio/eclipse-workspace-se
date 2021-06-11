
public class CarConstructorMain {

	public static void main(String[] args) {
		System.out.println("--------------차량입차출차---------------");
		Car car1=new Car("2345", 10);
		car1.setOutTime(12);
		car1.calculateFee();
		car1.headerPrint();
		car1.print();
		
		Car car2=new Car();
		car2.setIpChaData("5634", 11);
		car2.setOutTime(15);
		car2.calculateFee();
		car2.headerPrint();
		car2.print();
		
		
		System.out.println("----------오늘주차장이용차량출력---------");
		Car cara=new Car("1111", 1, 2, 2000);
		Car carb=new Car("3242", 3, 5, 3000);
		Car carc=new Car("4423", 11, 13, 2000);
		Car card=new Car("8987", 10, 15, 5000);
		Car care=new Car("5412", 5, 7, 2000);
		Car carf=new Car("7631", 7, 10, 3000);
		cara.headerPrint();
		carb.print();
		carc.print();
		card.print();
		care.print();
		carf.print();
		
	}

}