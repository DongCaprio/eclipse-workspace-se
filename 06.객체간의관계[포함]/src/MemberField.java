public class MemberField {
	/*
	 * 1-1.멤버필드는 객체생성시 기본값으로 자동 초기화
	 */
	public int member1;
	public double member2;
	public char member3;
	public boolean member4;
	public String member5;
	
	public int member6;
	public double member7;
	public char member8;
	public boolean member9;
	public String member10;
	/*
	 * 1-2.멤버필드는 객체생성시 기본값대신에초기값을줄수있다
	 */	
	public int member11=99999;
	public double member12=9.99999;
	public char member13='힣';
	public boolean member14=true;
	public String member15="문자열열열~~~";
	
	
	public MemberField() {
	}

	public MemberField(int member1, double member2, char member3, boolean member4, String member5) {
		
		this.member1 = member1;
		this.member2 = member2;
		this.member3 = member3;
		this.member4 = member4;
		this.member5 = member5;
	}
	
}