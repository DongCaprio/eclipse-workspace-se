import java.util.Iterator;

/*
 배열(타입):  
      - 같은데이타형을 가진 변수여러개의 모음타입 
      - 배열변수의 선언형식
           데이타타입[]   변수이름; 
           배열데이타타입 변수이름;
	  - 사용: 
	       1.배열타입객체의생성	
		   2.배열객체멤버들의 초기화 
      -특징   
		  1.모든 배열타입의 변수는 참조변수이다.
		    (배열은 객체다.)
		  2.같은타입만 정의가가능하다 
		  3.길이가 고정되어있다. 
		 
 */
public class PrimitiveTypeArrayMain {

	public static void main(String[] args) {
		int intType=0;
		intType=99;
		
		int[] intArray;
		intArray=null;
		intArray = new int[5]; 
		//intArray.0 = 11;	
		intArray[0] = 11;	
		intArray[1] = 22;	
		intArray[2] = 33;	
		intArray[3] = 44;	
		intArray[4] = 55;
		
		System.out.println(intArray[0]);
		System.out.println(intArray[1]);
		System.out.println(intArray[2]);
		System.out.println(intArray[3]);
		System.out.println(intArray[4]);
		System.out.println("----------for loop------------");
		for(int i=0;i<intArray.length;i++) {
			System.out.println(intArray[i]);
		}
		System.out.println();
		System.out.println("배열의길이:"+intArray.length);
		System.out.println("-----------기본형1차원배열---------------");
		int[] korArray= new int[10];
		korArray[0] = 80;
		korArray[1] = 56;
		korArray[2] = 78;
		korArray[3] = 99;
		korArray[4] = 34;
		korArray[5] = 78;
		korArray[6] = 93;
		korArray[7] = 13;
		korArray[8] = 91;
		korArray[9] = 77;
		int korTot=0;
		for (int i = 0; i < korArray.length; i++) {
			korTot=korTot+korArray[i];
		}
		System.out.println("우리반국어총점:"+korTot);
		System.out.println("우리반국어평균:"+korTot/korArray.length);
		System.out.println("------------char[]---------");
		char[] ca=new char[6];
		ca[0]='H';
		ca[1]='e';
		ca[2]='l';
		ca[3]='l';
		ca[4]='o';
		ca[5]='!';
		for (int i = 0; i < ca.length; i++) {
			System.out.print(ca[i]+" ");
		}
		System.out.println();
		System.out.println("------------String[]---------");
		String[] strArray=new String[5];
		strArray[0]="KIM";
		strArray[1]="LIM";
		strArray[2]="GIM";
		strArray[3]="DIM";
		strArray[4]="SIM";
		for (int i = 0; i < strArray.length; i++) {
			System.out.println(strArray[i]);
		}
		
		
	}

}


