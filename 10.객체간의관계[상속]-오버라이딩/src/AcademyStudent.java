public class AcademyStudent extends AcademyMember {
	public String ban;// 반

	public AcademyStudent() {
		// TODO Auto-generated constructor stub
	}

	public AcademyStudent(int no, String name, String ban) {
		this.no = no;
		this.name = name;
		this.ban = ban;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public void print() {
		super.print();
		System.out.println(this.ban);
	}

}
