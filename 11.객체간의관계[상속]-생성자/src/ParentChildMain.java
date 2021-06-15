class Parent extends Object{
	private int member1;
	private int member2;
	public Parent() {
		/*
		 * - 클래스생성자 첫번째라인에서는 반드시 
		 *   부모의 생성자(디폴트)가 반드시호출되어야한다.
		 * - 생략시 부모의 디폴트생성자가 자동호출된다.  
		 */
		super();//Object클래스 기본생성자
		System.out.println("2.Parent()디폴트생성자");
	}
	public Parent(int member1, int member2) {
		super();
		this.member1 = member1;
		this.member2 = member2;
		System.out.println("2.Parent(int member1, int member2) 생성자호출");
	}
	public void print() {
		System.out.print(this.member1+"\t"+this.member2+"\t");
	}
	public void method1() {
		System.out.println("Parent.method1()");
	}
	public void method2() {
		System.out.println("Parent.method2()");
	}
	public int getMember1() {
		return member1;
	}
	public void setMember1(int member1) {
		this.member1 = member1;
	}
	public int getMember2() {
		return member2;
	}
	public void setMember2(int member2) {
		this.member2 = member2;
	}
}
class Child extends Parent{
	private int member3;
	public Child() {
		//System.out.println();
		/*
		 * - 클래스생성자 첫번째라인에서는 반드시 
		 *   부모의 생성자(디폴트)가 반드시호출되어야한다.
		 *   (Constructor call must be the first statement in a constructor)
		 * - 생략시 부모의 디폴트생성자가 자동호출된다.  
		 */
		super();//Parent클래스 기본생성자
		System.out.println("3.Child()디폴트생성자");
	}
	public Child(int member1,int member2,int member3) {
		/*
		 * - 매개변수를 가지고있는 생성자를 호출할수있다.
		 * - 매개변수를 가지고있는 생성자는 명시적으로호출코드를 기술하여야한다.
		 */
		/*
		<<The field Parent.member1 is not visible>>
		this.member1=member1;
		this.member2=member2;
		*/
		/**********case1******
		this.setMember1(member1);
		this.setMember2(member2);
		**********************/
		/********case2********/
		super(member1, member2);//Parent클래스 Parent(int member1, int member2)생성자
		this.member3=member3;
		System.out.println("3.Child(int member1,int member2,int member3) 생성자호출");
	}
	public void method3() {
		System.out.println("Child.method3()");
	}
	public void print() {
		/*
		<< The field Parent.member1 is not visible >>
		System.out.println(this.member1+"\t"+this.member2+"\t"+this.member3);
		*/
		/**************case1***********
		System.out.println(this.getMember1()+"\t"+this.getMember2()+"\t"+this.member3);
		******************************/
		super.print();
		System.out.println(this.member3);
	}
	public int getMember3() {
		return member3;
	}
	public void setMember3(int member3) {
		this.member3 = member3;
	}
}
public class ParentChildMain {

	public static void main(String[] args) {
		System.out.println("----------Child()----------------------------------------------");
		Child c1=new Child();
		System.out.println("----------Child(int member1,int member2,int member3)-----------");
		Child c2=new Child(1, 2, 3);
		c2.print();
	}
}