public class MemberMethodReturnMain {

	public static void main(String[] args) {
		MemberMethodReturn mmr=new MemberMethodReturn();
		mmr.member1=78;
		mmr.member2=11;
		/*
		 public int method1();
		 */
		int recvData1 = mmr.method1();
		System.out.println("main block --> recvData1:"+recvData1);
		/*
		public boolean method2() 
		 */
		boolean recvData2 = mmr.method2();
		System.out.println("main block --> recvData2:"+recvData2);
		/*
		public int add(int a,int b)
		 */
		int addResult = mmr.add(12,789979);
		System.out.println("main block --> addResult:"+addResult);
		addResult=mmr.add(213123, 212);
		System.out.println("main block --> addResult:"+addResult);
		
		System.out.println("---------------------this를사용하여서 멤버필드의데이타를더해서반환--------------------");
		int recvAddMembers = mmr.addMembers();
		System.out.println("main --> recvMembers:"+recvAddMembers);
		
		
		System.out.println("----------------------멤버변수의 데이타를 set하기(setter)-------------------------------");
		mmr.setMember1(333);
		mmr.setMember2(666);
		System.out.println("----------------------멤버변수의 데이타를 반환받기(getter)-------------------------------");
		//int recvMember1=mmr.member1;
		int recvMember1 = mmr.getMember1();
		System.out.println("recvMember1:"+recvMember1);
		int recvMember2=mmr.getMember2();
		System.out.println("recvMember2:"+recvMember2);
		
	}
}