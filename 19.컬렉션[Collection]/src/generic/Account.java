package generic;

public class Account implements Comparable<Account>{
	/*
	 * 멤버필드
	 */
	private int no;//계좌번호
	private String owner;//계좌주
	private int balance;//잔고
	private double iyul;//이율
	/*
	 * 생성자
	 */
	/**
	 * 기본값으로 초기화된계좌객체생성
	 */
	public Account() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 매개변수값으로 최기화된계좌객체생성
	 * @param no 계좌번호
	 * @param owner 계좌주
	 * @param balance 잔고
	 * @param iyul 이율
	 */
	public Account(int no,String owner,int balance,double iyul) {
		this.no=no;
		this.owner=owner;
		this.balance=balance;
		this.iyul=iyul;
	}
	
	
	/*
	 * 멤버메쏘드
	 */
	//계좌데이타를 set하는메쏘드

	public void setAccountData(int no, String owner, int balance, double iyul) {
		this.no = no;
		this.owner = owner;
		this.balance = balance;
		this.iyul = iyul;
	}

	//입금
	public void deposit(int m) {
		this.balance += m;
	}

	//출금
	public void withDraw(int m) {
		//this.balance-=m;
		this.balance = this.balance - m;
	}

	public void headerPrint() {
		System.out.printf("%s%n", "---------------------------");
		System.out.printf("%s %5s %4s %4s%n", "번호", "이름", "잔고", "이율");
		System.out.printf("%s%n", "---------------------------");
	}

//	@Override
//	public String toString() {
//		return "("+no+" "+owner+")";
//	}
	public void print() {
		System.out.printf("%d %6s %8d %5.1f %n", this.no, this.owner, this.balance, this.iyul);
	}

	//getter method
	public int getBalance() {
		return this.balance;
	}

	//setter method
	public void setIyul(double iyul) {
		this.iyul = iyul;
	}

	//getter,setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getIyul() {
		return iyul;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public int compareTo(Account nextAccount) {
//		if(this.owner.compareTo(nextAccount.getOwner()) >0) {
//			return 1; //숫자는 관계없다 236을 하든 999를 하든 대신        중요!!!
//					  //양수이면 바꾸라는소리이고 음수이면 바꾸지 말라는 말이다. 
//		}else return -1;
		if(this.balance >nextAccount.getBalance()) {
			return 1;
		} else return -1;
	}

}