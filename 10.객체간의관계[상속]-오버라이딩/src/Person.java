
public class Person {

	private String think;
	private int no;
	public Person() {
		// TODO Auto-generated constructor stub
	}
	public void print()	{
		System.out.println(think+"\t"+no);
	}
	public Person(String think, int no) {
		this.think = think;
		this.no = no;
	}
	public String getThink() {
		return think;
	}
	public void setThink(String think) {
		this.think = think;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
}
