public class CarEncapsulationMain {

	public static void main(String[] args) {
		Car car1=new Car();
		car1.setIpChaData("1244", 12);
		Car car2=new Car();
		car2.setIpChaData("3342", 14);
		//4시간후
		car1.setOutTime(20);
		car2.setOutTime(20);
		
		car1.calculateFee();
		car2.calculateFee();
		car1.print();
		car2.print();
		System.out.println();
		
		/*
		 * car1 주차요금반환
		 */
		int car1Fee=car1.getFee();
		System.out.println("car1 주차요금 --> car1Fee:"+car1Fee);

	}

}