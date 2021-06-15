public class AcademyMemberOverridingMain {

	public static void main(String[] args) {
		//AcademyMember m1=new AcademyMember(0, "누구게");
		AcademyStudent st1=new AcademyStudent(1, "KIM", "LINUX");
		AcademyStudent st2=new AcademyStudent(2, "JIM", "JAVA");
		AcademyStudent st3=new AcademyStudent(3, "HIM", "DESIGN");

		AcademyGangsa gs1=new AcademyGangsa(4, "LEE", "ANDROID");
		AcademyGangsa gs2=new AcademyGangsa(5, "SIM", "IOS");
		
		AcademyStaff sf1=new  AcademyStaff(6, "WIN", "MARKETING");
		AcademyStaff sf2=new  AcademyStaff(7, "JUNG", "ACCOUNTING");
		
		/*
		st1.print();
		st2.print();
		st3.print();
		gs1.print();
		gs2.print();
		sf1.print();
		sf2.print();
		 */
		
		System.out.println("------------Student-------------");
		st1.print();
		st2.print();
		st3.print();
		System.out.println("------------Gangsa-------------");
		gs1.print();
		gs2.print();
		System.out.println("------------Staff-------------");
		sf1.print();
		sf2.print();
	}

}