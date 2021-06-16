public class AcdemyMemberCastingMain {

	public static void main(String[] args) {
		AcademyStudent st1=new AcademyStudent(1,"KIM","LINUX");
		AcademyMember m1=st1; 
		AcademyMember m2=new AcademyStudent(2,"KANG","OFFICE");
		AcademyMember m3=new AcademyStudent(3,"HONG","JAVA");
		
		AcademyMember m4=new AcademyGangsa(4,"SIM","ARDUINO");
		AcademyMember m5=new AcademyGangsa(5,"HIM","DESIGN");
		
		AcademyMember m6=new AcademyStaff(6,"KING","영업부");
		AcademyMember m7=new AcademyStaff(7,"JUNG","총무부");
		
		m1.print();
		m2.print();
		m3.print();
		m4.print();
		m5.print();
		m6.print();
		
	}

}