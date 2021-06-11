public class Member {
	private int no;
	private String name;
	public Member() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * alt+shift+s --> o -->enter
	 */
	public Member(int no, String name) {
		this.no = no;
		this.name = name;
	}
	public void print() {
		System.out.println(this+"-->"+no+","+name);
	}
	
	public void method(int a) {
		/*
		<<The local variable tot may not have been initialized>>
		int tot;
		System.out.println(tot);
		*/
		System.out.println("local variable a:"+a);
		int b = a+4;
		System.out.println("local variable b:"+b);
	}
	
	
	
	/*
	 * alt+shift+s --> r --> space bar -- down arrow
	 */
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}