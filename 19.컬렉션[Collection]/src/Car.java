public class Car {
	
	
	//1.멤버필드(변수)(속성)
	private String no;//차량번호
	private int inTime;//입차시간
	private int outTime;//출차시간
	private int fee;//주차요금
	/*
	 * 생성자
	 */
	public Car() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Car(String no, int inTime) {
		this.no = no;
		this.inTime = inTime;
	}
	


	public Car(String no, int inTime, int outTime, int fee) {
		
		this.no = no;
		this.inTime = inTime;
		this.outTime = outTime;
		this.fee = fee;
	}



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
	 *  헤더출력
	 */
	public void headerPrint() {
		System.out.println("-----------------------------------");
		System.out.printf("%s %s %s %s%n","차량번호", "입차시간","출차시간","주차요금");
		System.out.println("-----------------------------------");
	}
	/*
	 * 차량정보출력
	 */
	public void print() {
		
		System.out.printf("%7s %7d %9d %8d%n",this.no,this.inTime,this.outTime,this.fee);
		return;
	}
	//setter
	public void setNo(String no) {
		this.no = no;
	}

	public void setInTime(int inTime) {
		this.inTime = inTime;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
	//getter
	public String getNo() {
		return no;
	}

	public int getInTime() {
		return inTime;
	}

	public int getOutTime() {
		return outTime;
	}

	public int getFee() {
		return fee;
	}

	
	
	
}