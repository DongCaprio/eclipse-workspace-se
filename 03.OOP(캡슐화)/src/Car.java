/*
 * class 선언
 *  - 형식
 *       접근제한자  class  클래스이름{
 *       	- 접근제한자:public,protected,없는거
 *       
 *       } 
 *       ex> public class Car{
 *           }
 *           
 *  - 구성요소
 *     1.멤버변수(필드)선언(속성)
 *        접근제한자(public,proected,없는거,private) 타입 indentifier;
 *        ex> public String carName;
 *        
 *     2.멤버메쏘드 선언(행위)    
 *        접근제한자 리턴타입 메쏘드이름(인자){
 *           -인자: 나를 호출한놈이 데이타를 넣어줄 통로
 *        	 -리턴타입: 나를 호출한놈에게 줄 데이타타입 
 *                     void --> 줄데이타가 없는경우
 *        }
 *        ex> public int test(int a){
 *        	  	
 *            }
 */
public class Car {
	/*
	클래스의 구성요소
	   1. 멤버필드(변수)[속성] (차객체의 속성데이타를 저장할변수)
	   2. 멤버메쏘드[기능](차객체가 가지고있는 기능 )
	*/
	
	//1.멤버필드(변수)(속성)
	private String no;//차량번호
	private int inTime;//입차시간
	private int outTime;//출차시간
	private int fee;//주차요금
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
