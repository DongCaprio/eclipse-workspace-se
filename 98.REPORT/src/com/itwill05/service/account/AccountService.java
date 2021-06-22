package com.itwill05.service.account;

import java.util.Iterator;

/*
 * Account객체전체에관련된 업무를 실행할클래스
 * 		1.계좌객체들(Account[]) 을멤버변수로가진다.
 * 		2.계좌객체들을 사용해서 업무실행
 */
 
public class AccountService {
	/*
	private Account[] accounts= {
			new Account(1111, "KIM", 89000, 1.3),
			new Account(2222, "AIM", 45000, 2.7),
			new Account(3333, "FIM", 89000, 4.7),
			new Account(4444, "XIM", 34000, 6.7),
			new Account(5555, "hIM", 78000, 3.7),
			new Account(6666, "KIM", 89000, 5.7),
			new Account(7777, "KIM", 89000, 4.7),
			new Account(8888, "qIM", 91000, 1.7),
			new Account(9999, "mIM", 12000, 0.7),
	};
	*/
	private Account[] accounts;
	public AccountService() {
		accounts=new Account[9];
		accounts[0]=new Account(1111, "KIM", 89000, 1.3);
		accounts[1]=new Account(2222, "AIM", 45000, 2.7);
		accounts[2]=new Account(3333, "FIM", 89000, 4.7);
		accounts[3]=new Account(4444, "XIM", 34000, 6.7);
		accounts[4]=new Account(5555, "hIM", 78000, 3.7);
		accounts[5]=new Account(6666, "KIM", 89000, 5.7);
		accounts[6]=new Account(7777, "KIM", 89000, 4.7);
		accounts[7]=new Account(8888, "qIM", 91000, 1.7);
		accounts[8]=new Account(9999, "mIM", 12000, 0.7);
	}
	
	/*
	 * 0.계좌객체를 인자로받아서 Account[]에추가[OPTION]
	*/
	public void addAccount(Account newAccount) {
		Account[] newAccount1 = new Account[(accounts.length)+1];
		for (int i = 0; i < accounts.length; i++) {
			newAccount1[i] = accounts[i]; //크기가 1큰 배열생성 
		} 
<<<<<<< HEAD
//		newAccount1[newAccount1.length] = new Account(); 추가는 잘 모르겠다.
=======
		newAccount1[newAccount1.length-1] = newAccount; 
		this.accounts=newAccount1;
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
	}
		/*
		 * 1.배열크기증가
		 *   - 기존배열보다큰배열생성
		 *   - 기존데이타 옮김
		 */
	/*
	 * 0.계좌데이타를 인자로받아서 Account[]에추가[OPTION]
	*/
	public void addAccount(int no,String owner,int balance,double iyul) {
		/*
		 * 1.배열크기증가
		 *   - 기존배열보다큰배열생성
		 *   - 기존데이타 옮김
		 */
		Account[] newAccount2 = new Account[(accounts.length)+1];
		for (int i = 0; i < accounts.length; i++) {
			newAccount2[i] = accounts[i];
		} //1칸이큰 새로운배열생성하고 기존의 값들 다 복사해서 넣고 세로생긴 마지막 1칸에는 새로 생성하는값 대입
<<<<<<< HEAD
		newAccount2[newAccount2.length] = new Account(no, owner, balance, iyul);
=======
		newAccount2[newAccount2.length-1] = new Account(no, owner, balance, iyul);
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
	}
	/*
	 1.은행계좌들 총계좌수출력메쏘드정의
	 */
	public void totAccountNumberPrint() {
		System.out.println(accounts.length);
	}
	/*
	 * 2.은행계좌들 전체출력메쏘드 정의
	 */
	public void print() {
		Account.headerPrint();
		for (int i = 0; i < accounts.length; i++) {
			accounts[i].print();
		}
	}
		
	/*
	 * 3.은행계좌들 총잔고 출력메쏘드 정의
	 */
	public void totBalancePrint() {
		int totBlance =0;
		for (int i = 0; i < accounts.length; i++) {
			totBlance += accounts[i].getBalance();
		}
		System.out.println(totBlance);
	}
	/*
	 4.계좌번호 인자로받아서 계좌한개출력
	 */
	public void findByNoPrint(int no) {
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getNo()==no) {
				accounts[i].print();
				break;
			}
		}
	}	
	/*
	 *  5.계좌잔고 인자로 받아서 잔고이상인 계좌들출력
	 */
	public void findByBalancePrint(int balance) {
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getBalance() >= balance) {
				accounts[i].print();
			}
		}
		
	}
	/*
	6.계좌이율 인자로 받아서 이율이상인 계좌들출력
	*/ 
	public void findByIyulPrint(double iyul) {
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getIyul() > iyul) {
				accounts[i].print();
			}
		}
		
	}
	/*
	7.계좌주이름 인자로 받아서 인자이름과동일한 계좌들출력
	 */ 
	public void findByNamePrint(String name) {
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getOwner().equals(name) ){
				accounts[i].print();
			}
		}
	}
	/*
	8.계좌번호,입금할돈 인자로 받아서 입금
	 */ 
	public void ipGum(int no,int m) {
		/*
		 * 1.계좌번호로 계좌찾기
		 * 2.입금
		 */
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getNo() == no) {
				accounts[i].deposit(m);
				//accounts[i].getBalance();
				accounts[i].print();
				break;
			}
		}
	}
	/*
	9.계좌번호,출금할돈 인자로 받아서 출금
	 */ 
	public void chulGum(int no,int m) {
		for (int i = 0; i < accounts.length; i++) {
			if(accounts[i].getNo() == no) {
				accounts[i].withDraw(m);
				accounts[i].print();
				break;
			}
		}
	}
	
	/*
	 10.<< 정렬 >>
	 * standard --> 1:번호,2:이름,3:잔고,4:이율
	 * order    --> 1:오르차순,2:내림차순
	 */
	
	/*
	 10.계좌를 잔고순으로 오름차순정렬
	 */
	public void sortByBalanceAscending() {
		for (int i = 0; i < accounts.length - 1; i++) {
			for (int j = 0; j < accounts.length - 1 - i; j++) {
				if (accounts[j].getBalance() > accounts[j + 1].getBalance()) {
					Account temAccount = accounts[j + 1];
					accounts[j + 1] = accounts[j];
					accounts[j] = temAccount;
				}
			}
		}
		print();
	}
	/*
	 11.계좌를 잔고순으로 내림차순정렬
	 */
	public void sortByBalanceDescending() {
		for (int i = 0; i < accounts.length - 1; i++) {
			for (int j = 0; j < accounts.length - 1 - i; j++) {
				if (accounts[j].getBalance() < accounts[j + 1].getBalance()) {
					Account temAccount = accounts[j + 1];
					accounts[j + 1] = accounts[j];
					accounts[j] = temAccount;
				}
			}
		}
		print();
	}	
	/*
	12.계좌객체를 인자로 받아서 이름,잔고,이율 수정(update)[OPTION]
	*/
	//만약에 계좌객체가 4번 고객자리로의 수정을 원한다면
<<<<<<< HEAD
=======
//	public void updateAccount(Account updateAccount) {
//		for (int i = 0; i < accounts.length; i++) {
//			if(i == 3) {
//				accounts[i]=updateAccount;
//				print();
//				break;
//			}
//			
//		}
//	}  이건내꺼고
	
	//강사꺼
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
	public void updateAccount(Account updateAccount) {
		for (int i = 0; i < accounts.length; i++) {
<<<<<<< HEAD
			if(i == 3) {
				accounts[i]=updateAccount;
				print();
				break;
			}
			
=======
			if(accounts[i].getNo()==updateAccount.getNo()) {
				accounts[i]=updateAccount;
				break;
			}
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
		}
	}
	
	
	
	/*
	13.번호,이름,잔고,이율 인자로받아서 계좌객체수정(update)[OPTION]
	*/
	public void updateAccount(int no,String owner,int balance,double iyul) {
		//예를 들어 2번 고객이 바뀐다고 하면
		//배열의 순서 2번고객의 계좌번호 ~ 이율까지 다 변경
<<<<<<< HEAD
		for (int i = 0; i < accounts.length; i++) {
			if(i == 1) {
				accounts[i].setNo(no);
				accounts[i].setOwner(owner);
				accounts[i].setBalance(balance);
				accounts[i].setIyul(iyul);
				print();
				break;
			}
		}
=======
//		for (int i = 0; i < accounts.length; i++) {
//			if(i == 1) {
//				accounts[i].setNo(no);
//				accounts[i].setOwner(owner);
//				accounts[i].setBalance(balance);
//				accounts[i].setIyul(iyul);
//				print();
//				break;
//			}
//		}
		//강사꺼
			Account updateAccount=new Account(no, owner, balance, iyul);
			this.updateAccount(updateAccount);
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
		
			
		}
	}
<<<<<<< HEAD
=======
		}
	
>>>>>>> branch 'master' of https://github.com/DongCaprio/eclipse-workspace-se.git
