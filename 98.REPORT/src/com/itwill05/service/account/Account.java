package com.itwill05.service.account;
/*
 * 은행에서 계좌객체를 생성하기위한 클래스
 */
public class Account {
	/*
	 * 멤버필드
	 */
	private int no;			//계좌번호
	private String owner;   //계좌주
	private int balance;    //잔고
	private double iyul;	//이율
	/*
	 * 생성자
	 */
	public Account() {
		
	}
	public Account(int no, String owner, int balance, double iyul) {
		this.no = no;
		this.owner = owner;
		this.balance = balance;
		this.iyul = iyul;
	}
	/*
	 * 멤버메쏘드
	 */
	//계좌데이타를set하는 메쏘드
	public void setAccountData(
			int no,String owner,int balance,double iyul) {
		this.no=no;
		this.owner=owner;
		this.balance=balance;
		this.iyul=iyul;
	}
	//입금메쏘드
	public void deposit(int m) {
		this.balance=this.balance+m;
	}
	//출금메쏘드
	public void withDraw(int m) {
		this.balance = this.balance-m;
	}
	//헤더프린트
	public static void headerPrint() {
		System.out.println("----------------------");
		System.out.println("번호  이름  잔고  이율");
		System.out.println("----------------------");
	}
	
	//계좌정보출력메쏘드
	public void print() {
		System.out.printf("%d  %s  %d  %4.1f %n",this.no,this.owner,this.balance,this.iyul);
		/*
		 1.첫글자 대문자
		 2.5자리로출력
		 3.첫글자이외에는****(4개)
		  1111	K****	 89000	 1.3
		*/

		
		
	}
	
	//getter 메쏘드
	public int getNo() {
		return this.no;
	}
	public String getOwner() {
		return this.owner;
	}
	public int getBalance() {
		return this.balance;
	}
	public double getIyul() {
		return this.iyul;
	}
	//setter 메쏘드
	public void setNo(int no) {
		this.no = no;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public void setIyul(double iyul) {
		this.iyul = iyul;
	}
}