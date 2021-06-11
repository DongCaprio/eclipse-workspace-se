public class CarFactoryStaticMain {

	public static void main(String[] args) {
		//Car.count=0;
		Car car1=new Car(1, "LANDROVER", "RED");
		Car car2=new Car(2, "VOLVO", "WHITE");
		Car car3=new Car(3, "PORSCHE", "RED");
		
		Car.headerPrint();
		car1.print();
		car2.print();
		car3.print();

		System.out.println("차량생산대수:"+Car.count);
		System.out.println("차량생산대수:"+car1.count);
		System.out.println("차량생산대수:"+car2.count);
		System.out.println("차량생산대수:"+car3.count);
		
	}

}
