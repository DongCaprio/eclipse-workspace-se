public class MemberMethodThis{
	int memberField1;
	char memberField2;
	double memberField3;
	
	public void setMemberField1(int memberField1) {
		/*
		 * this : 
		 *    - self참조변수
		 *    - setMemberField1 메쏘드를 가지고있는 객체의 주소값
		 */
		this.memberField1=memberField1;
	}
	public void setData(int memberField1,char memberField2,double memberField3) {
		/*
		 * this : 
		 *    - self참조변수
		 *    - setData메쏘드를 가지고있는 객체의 주소값
		 */
		this.memberField1 = memberField1;
		this.memberField2 = memberField2;
		this.memberField3 = memberField3;
	}
	public void print() {
		/*
		 * this : 
		 *    - self참조변수
		 *    - print메쏘드를 가지고있는 객체의 주소값
		 */
		System.out.println("print()-->"+this);
		System.out.println(this.memberField1+"\t"+this.memberField2+"\t"+this.memberField3);
	}
	
}