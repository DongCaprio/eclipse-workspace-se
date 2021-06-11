public class ReferenceTypeArrayMain {

	public static void main(String[] args) {
		/*
		Account acc=new Account(8852, "HONG", 56000, 0.3);
		int[] ia=new int[5];
		ia[0]=111131;
		*/
		Account[] accArray=new Account[5];
		
		accArray[0] =new Account(1111, "KIM", 89000, 0.6);
		
		accArray[1] =new Account(2222, "LIM", 49000, 0.3); 
		accArray[2] =new Account(3333, "JIM", 59000, 0.2); 
		accArray[3] =new Account(4444, "OIM", 99000, 0.1); 
		accArray[4] =new Account(5555, "PIM", 19000, 0.8); 
		
		Account.headerPrint();
		for(int i=0;i<accArray.length;i++) {
			accArray[i].print();
		}
		
		
		
		

	}

}