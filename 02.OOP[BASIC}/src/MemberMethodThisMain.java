public class MemberMethodThisMain {

	public static void main(String[] args) {
		MemberMethodThis mmt1=new MemberMethodThis();
		/*
		mmt1.memberField1=1;
		mmt1.memberField2='A';
		mmt1.memberField3=0.1;
		*/
		mmt1.setData(1, 'A', 0.1);
		//System.out.println(mmt1.memberField1+"\t"+mmt1.memberField2+"\t"+mmt1.memberField3);
		mmt1.print();
		mmt1.setMemberField1(100);
		mmt1.print();
		
		
		MemberMethodThis mmt2=new MemberMethodThis();
		/*
		mmt2.memberField1=2;
		mmt2.memberField2='B';
		mmt2.memberField3=0.2;
		*/
		mmt2.setData(2, 'B', 0.2);
		//System.out.println(mmt2.memberField1+"\t"+mmt2.memberField2+"\t"+mmt2.memberField3);
		mmt2.print();
		mmt2.setMemberField1(200);
		mmt2.print();
		
		MemberMethodThis mmt3=new MemberMethodThis();
		/*
		mmt3.memberField1=3;
		mmt3.memberField2='C';
		mmt3.memberField3=0.3;
		*/
		mmt3.setData(3,'C', 0.3);
		//System.out.println(mmt3.memberField1+"\t"+mmt3.memberField2+"\t"+mmt3.memberField3);
		mmt3.print();
		mmt3.setMemberField1(300);
		mmt3.print();
		
		
		
		
		
		
		
	}

}