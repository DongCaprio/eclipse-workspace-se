
public class VariableTypes {

	public static void main(String[] args) {

	/*
	 논리형(1byte)
	 - 논리형변수
	 - 논리형리터럴(값, 데이터) : true, false
	 */
		boolean bool1;
		boolean bool2;
		boolean bool3;
		bool1 = true;
		bool2 = false;
		bool3 = true;
		
		/*
		 * ctrl + alt + 방향키(위, 아래) = 해당방향으로 한줄복사 단축키
		 */
		System.out.println(bool1);
		System.out.println(bool2);
		System.out.println(bool3);
		/*
		 * 문자한개형(2byte)
		 * 문자형리터럴(값,데이터):'A', '힣', 'C' , '1'
		 */
		char munja1, munja2, munja3, munja4, munja5;
		munja1 = 'A';
		munja2 = 'B';
		munja3 = '김';
		munja4 = '3';
		munja5 = 'ㅁ';
		System.out.println(munja1);
		System.out.println(munja2);
		System.out.println(munja3);
		System.out.println(munja4);
		System.out.println(munja5);
		
		/*
		 * 숫자형
		 */
		int i1, i2, i3, i4; // 4byte
		i4 = 2147483647;
		System.out.println(i4);
		double d1, d2; // 8byte
		d2 = 123459123;
		
	//	System.out.println(1);
		//System.out.println(i2);
		//System.out.println(i3);
		//System.out.println(i4);

		String str1="오늘은";
		String str2="금요일";
		String str3="변수를 공부합니다";
		System.out.println(str1+"              "+str2+"토요일"+str3);
		System.out.println(str2);
		System.out.println(str3);
		
		String name = "김동진";
		int age = 40;
		String address = "서울";
		char gender = 'M';
		System.out.println("이름 : "+name+"\n나이 : "+age+"\n주소 : "+address+"\n성별 : "+gender);
		
		
		
		
		
	}

}
