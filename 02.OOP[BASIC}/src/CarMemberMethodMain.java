
public class CarMemberMethodMain {

	public static void main(String[] args) {

		Car car11= new Car(); // 인스턴스 생성
		
//		car11.no="7788";
//		car11.inTime=5;
		car11.setIpChaData("7788",5);
		
		Car car22=new Car();
//		car22.no="6767";
//		car22.inTime=6;
		car22.setIpChaData("6767", 9);;
		//4시간후 출차(car1, car2)
		//10시출차
		
		//1. 출차시간대입
		car11.setOutTime(10);
		car22.setOutTime(18);
		//2. 요금계산
		car11.calculateFee();
		car22.calculateFee();
		//3. 영수증 출력
		car11.print();
		car22.print();
		
		
		
		
		
	}

}
