package com.itwill05.service.account;

/*
 * 1.계좌객체들(Account[]) 을멤버변수로가진다.
 * 2.계좌객체들을 사용해서 업무실행
 */
public class AccountServiceReturn {

	private Account[] accounts = { new Account(1111, "KIM", 85000, 5.9), new Account(2222, "JIM", 77000, 4.2),
			new Account(3333, "FIM", 56000, 1.2), new Account(4444, "SIM", 77000, 0.2),
			new Account(5555, "GIM", 94000, 3.2), new Account(6666, "AIM", 56000, 6.2),
			new Account(7777, "XIM", 33000, 7.2), new Account(8888, "QIM", 77000, 5.2),
			new Account(9999, "AIM", 80000, 1.2) };

	/*
	 * 0.계좌객체를 인자로받아서 Account[]에추가[OPTION]
	 */
	public void addAccount(Account newAccount) {
		/*
		 * 1.배열크기증가 - 기존배열보다큰배열생성 - 기존데이타 옮김
		 */
		Account[] newAccounts = new Account[accounts.length + 1];
		for (int i = 0; i < accounts.length; i++) {
			newAccounts[i] = accounts[i];
		}
		newAccounts[accounts.length] = newAccount;
		this.accounts = newAccounts;
	}

	/*
	 * 0.계좌데이타를 인자로받아서 Account[]에추가[OPTION]
	 */
	public void addAccount(int no, String owner, int balance, double iyul) {

		Account newAccount = new Account(no, owner, balance, iyul);
		/*
		 * 1.배열크기증가 - 기존배열보다큰배열생성 - 기존데이타 옮김
		 */
		Account[] newAccounts = new Account[accounts.length + 1];
		for (int i = 0; i < accounts.length; i++) {
			newAccounts[i] = accounts[i];
		}
		newAccounts[accounts.length] = newAccount;
		this.accounts = newAccounts;
	}

	/*
	 * 1.은행계좌들 총계좌수 반환메써드
	 */
	public int getTotAccountNumber() {
		return accounts.length;
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
	 * 3.은행계좌들 총잔고를 반환하는 메쏘드
	 */
	public int getAccountTotBalance() {
		int totBalance = 0;
		for (int i = 0; i < accounts.length; i++) {
			totBalance += accounts[i].getBalance();
		}
		return totBalance;
	}

	/*
	 * 4.계좌번호를 인자로받아서 계좌객체주소 한개반환
	 */
	public Account findByNo(int no) {
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == no) {
				return accounts[i];
			}
		}
		return null;

	}

	/*
	 * 5.계좌잔고 인자로받아서 잔고이상인 계좌배열객체 참조변수반환
	 */
	public Account[] findByBalance(int balance) {
		Account[] findAccounts = null;
		/*
		 * A. 만족하는 객체의갯수구하기 - 예를들어 3개라면
		 */
		int overBalance = 0;
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getBalance() >= balance) {
				overBalance++;
			}
		}

		/*
		 * B. Account객체배열생성 - findAccounts=new Account[3];
		 */
		findAccounts = new Account[overBalance];
		/*
		 * C. 만족하는Account객체들 Account배열에담기
		 */
//		for(int i = 0; i < overBalance; i++) {
//			for (int j = 0; j < accounts.length; j++) {
//				if(accounts[j].getBalance() >= balance) {
//					findAccounts[i]=accounts[j];
//					break;
//				}
//			}
//		}
		for (int i = 0, j = 0; i < findAccounts.length; i++) {
			if (accounts[i].getBalance() >= balance) {
				findAccounts[j] = accounts[i];
				j++;
			}
		}
		return findAccounts;
	}

	/*
	 * 6.계좌이율인자로받아서 인자이상인 계좌들배열객체 참조변수반환
	 */
	public Account[] findByIyul(double iyul) {
		Account[] findAccounts = null;

		int overIyul = 0;
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getIyul() >= iyul) {
				overIyul++; // 특정 이율을 넘는 계좌의 갯수 구하기
			}
		}
		findAccounts = new Account[overIyul]; // 구한 갯수만큼을 배열의 길이로 결정해서 새 배열 생성
		for (int i = 0, j = 0; i < findAccounts.length; i++) {
			if (accounts[i].getIyul() >= iyul) {
				findAccounts[j] = accounts[i]; // 다시 이율넘는 계좌를 구하면서 그것을 새롭게 만든 배열에 대입
				j++; // i가 대입된후 새로운 배열의 다음칸에 조건을 충족한 기존배열 다음것을 넣기위해 ++해줌
			}
		}
		return findAccounts;
	}

	/*
	 * 7.계좌주이름 인자로받아서 이름과일치하는계좌들배열객체 참조변수반환
	 */
	public Account[] findByName(String name) {
		Account[] findAccounts = null;
		int sameName = 0;
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getOwner().equals(name)) {
				sameName++; // 특정 이율을 넘는 계좌의 갯수 구하기
			}
		}
		findAccounts = new Account[sameName]; // 구한 갯수만큼을 배열의 길이로 결정해서 새 배열 생성
		for (int i = 0, j = 0; i < findAccounts.length; i++) {
			if (accounts[i].getOwner().equals(name)) {
				findAccounts[j] = accounts[i]; // 다시 이름이 같은 계좌를 구하면서 그것을 새롭게 만든 배열에 대입
				j++; // i가 대입된후 새로운 배열의 다음칸에 조건을 충족한 기존배열 다음것을 넣기위해 ++해줌
			}
		}
		return findAccounts;
	}

	/*
	 * 8.계좌번호,입금할돈 인자로 받아서 입금
	 */
	public Account ipGum(int no, int m) {
		/*
		 * 1.계좌번호로 계좌찾기 2.입금
		 */
		Account findAccount = this.findByNo(no);
		findAccount.deposit(m);
		return findAccount;

	}

	/*
	 * 9.계좌번호,출금할돈 인자로 받아서 출금
	 */
	public Account chulGum(int no, int m) {
		Account findAccount = this.findByNo(no);
		findAccount.withDraw(m);
		return findAccount;
	}

	/*
	 * 10,11 정렬 standard --> 1:번호,2:이름,3:잔고,4:이율 order --> 1:오름차순,2:내림차순
	 */
	public void sort(int standard, int order) {

		if (standard == 1 && order == 1) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getNo() > accounts[j + 1].getNo()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 1 && order == 2) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getNo() < accounts[j + 1].getNo()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 2 && order == 1) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getOwner().compareTo(accounts[j + 1].getOwner()) > 0) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 2 && order == 2) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getOwner().compareTo(accounts[j + 1].getOwner()) < 0) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 3 && order == 1) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getBalance() > accounts[j + 1].getBalance()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 3 && order == 2) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getBalance() < accounts[j + 1].getBalance()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 4 && order == 1) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getIyul() > accounts[j + 1].getIyul()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		} else if (standard == 4 && order == 2) {
			for (int i = 0; i < accounts.length - 1; i++) {
				for (int j = 0; j < accounts.length - 1 - i; j++) {
					if (accounts[j].getIyul() < accounts[j + 1].getIyul()) {
						Account temAccount = accounts[j + 1];
						accounts[j + 1] = accounts[j];
						accounts[j] = temAccount;
					}
				}
			}
		}

	}

	/*
	 * 12.계좌객체를 인자로 받아서 이름,잔고,이율 수정(update)[OPTION]
	 */
	public void updateAccount(Account updateAccount) {
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == updateAccount.getNo()) {
				/*********
				 * case1************ accounts[i].setOwner(updateAccount.getOwner());
				 * accounts[i].setBalance(updateAccount.getBalance());
				 * accounts[i].setIyul(updateAccount.getIyul());
				 **************************/
				/************ case2 **********/
				accounts[i] = updateAccount;
				break;
			}

		}
	}

	/*
	 * 13.번호,이름,잔고,이율 인자로받아서 계좌객체수정(update)[OPTION]
	 */
	public void updateAccount(int no, String owner, int balance, double iyul) {
		this.updateAccount(new Account(no, owner, balance, iyul));
	}

	/*
	 * 14.계좌번호 인자로받아서 삭제해줘[OPTION] A. 배열에서 Account객체삭제 B. 배열사이즈감소 C. 삭제한계좌객체반환
	 * 
	 */
	public Account deleteByNo(int no) {
		Account deleteAccount = null;

		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i].getNo() == no) {  //일단 먼저 no와 똑같은 계좌번호찾기
				for (; i < accounts.length; i++) { //찾은후 삭제를 위해 배열번호 다음칸값을 앞에칸 값으로 덮어씌운다
					accounts[i] = accounts[i + 1]; 
				}
				break;
			}
		}
		accounts[accounts.length-1] = null; //다 덮어씌우면 마지막 칸이 비게 되므로 마지막칸에 널 대입
		//deleteAccount = accounts;
		// 마지막에 이렇게 주소값만 변경하고 싶은데 방법을 잘 모르겠다
		return deleteAccount;
	}

}
