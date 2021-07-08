/**
 * 이 클래스는 주차장에서 차객체를 생성해기 위한 클래스입니다.
 * @author 김동진
 * @version 0.1
 * @since 2021
 *
 */
public class Car {
	/*
	클래스의 구성요소
	   1. 멤버필드(변수)[속성] (차객체의 속성데이타를 저장할변수)
	   2. 멤버메쏘드[기능](차객체가 가지고있는 기능 )
	*/
	
	//1.멤버필드(변수)(속성)
	/**
	 * 차량번호저장필드
	 */
	
	public String no;//차량번호
	public int inTime;//입차시간
	public int outTime;//출차시간
	public int fee;//주차요금
	//2. 멤버메쏘드[기능](차객체가 가지고있는 기능 )
	/*
	 * 출차시간셋팅
	 */
	public void setOutTime(int outTime) {
		this.outTime=outTime;
		return;
	}
	
	/*
	 * 입차데이타(번호,입차시간) 셋팅
	 */
	public void setIpChaData(String no, int inTime) {
		this.no=no;
		this.inTime=inTime;
	}
	
	/*
	 * 요금계산
	 */
	public void calculateFee() {
		this.fee=(this.outTime-this.inTime)*1000;
	}
	
	/*
	 * 차량정보출력
	 */
	public void print() {
		System.out.println("-----------------------------------");
		System.out.printf("%s %s %s %s%n","차량번호", "입차시간","출차시간","주차요금");
		System.out.println("-----------------------------------");
		System.out.printf("%7s %7d %9d %8d%n",this.no,this.inTime,this.outTime,this.fee);
		return;
	}

	
	
}
