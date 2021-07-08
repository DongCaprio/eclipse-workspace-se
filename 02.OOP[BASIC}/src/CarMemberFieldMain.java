	
public class CarMemberFieldMain {

	public static void main(String[] args) {
		/*
		 * 1. 차량입차 1234번차량 12시
		 */

		// 차객체 데이터를 저장할 참조변수 car0 선언
		Car car0;
		// Car클래스를 사용해서 객체를 생성한후 car0참조변수 대입
		// 여기서 car0은 Car인스턴스의 주소를 참조하고 있는것이다.
		car0 = new Car();

		// 차 객체의 no멤버필드에 스트링값 대입
		car0.no = "1234";
		// 차 객체의 inTime멤버필드에 스트링값 대입
		car0.inTime = 12; 

		// 2. 16시에 car0차량 출차(=1234차량 출차)한 상황
		
		//차 객체의 outTime멤버필드(변수)에 16 대입
		car0.outTime = 16;
		
		//fee 계산식
		//car0의 멤버변수값을 이용해 fee를 계산후 car0의 fee멤버변수에 값 대입
		car0.fee = (car0.outTime - car0.inTime) * 1000;
		
		//영수증출력 ( 참조변수 car0의 멤버변수(속성)값 출력)
		car0.print();
	}

}
