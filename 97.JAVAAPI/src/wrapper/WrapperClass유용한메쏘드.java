package wrapper;

public class WrapperClass유용한메쏘드 {

	public static void main(String[] args) {
		System.out.println("int-->String");
		int y=2147483647;
		String yStr1 = Integer.toString(y);
		System.out.println(yStr1);
		String yStr2 = y+"";
		System.out.println(yStr2);
		String yStr3=Integer.toBinaryString(y);
		System.out.println(yStr3);
		String yStr4=Integer.toHexString(y);
		System.out.println(yStr4);
		System.out.println("String-->int,double,boolean");
		int i1 = Integer.parseInt("56153212");
		System.out.println(i1);
	    double d1=Double.parseDouble("3.14159");
	    System.out.println(d1);
		boolean b1=Boolean.parseBoolean("true");
		System.out.println(b1);
		/*
		 * + 연산자 오버로딩
		 *  - 항이 문자열일경우 concat
		 */
		
		int number=454354;
		String numberStr1=number+"";
		String numberStr2=1+2+3+4+(5+"6");
		System.out.println(numberStr2);
		System.out.println("----------------Character----------");
		Character wc1=new Character('김');
		Character wc2='김';
		char c1=wc1.charValue();
		char c2=wc2;
		System.out.println("-------String-->char,char[]----");
		char firstChar = "김경호".charAt(0);
		System.out.println(firstChar);
		char[] ca = "오늘은 수요일 WrapperClass를 공부합니다.".toCharArray();
		System.out.println(ca[0]+""+ca[1]);
		System.out.println("-------char,char[]-->String---");
		char c3='김'; //'김' -->  "김"  
		String str3=String.valueOf(c3);
		String str4 = c3+"";
		System.out.println(str3+","+str4);
		char[] ca5= {'김','은','희'};
		String str5=String.valueOf(ca5);
		System.out.println("char[]-->String:"+str5);
		String str6=new String(ca5);
		System.out.println("char[]-->String:"+str6);
		
		
		String strs="Hello Java!!##%&@#!()[]{}01234 김경호제임스";
		for (int i = 0; i < strs.length(); i++) {
			char tempChar=strs.charAt(i);
			int type = Character.getType(tempChar);
			//System.out.println(tempChar+" type:"+type);
			if(type== Character.UPPERCASE_LETTER) {
				System.out.println("UPPERCASE_LETTER:"+tempChar);
			}
			if(type==Character.LOWERCASE_LETTER) {
				System.out.println("LOWERCASE_LETTER:"+tempChar);
			}
			if(type==Character.DECIMAL_DIGIT_NUMBER) {
				System.out.println("DECIMAL_DIGIT_NUMBER:"+tempChar);
			}
			if(type==Character.SPACE_SEPARATOR) {
				System.out.println("SPACE_SEPARATOR:"+tempChar);
			}
			if(Character.isSpaceChar(tempChar)) {
				System.out.println("Character.isSpaceChar:"+tempChar);
			}
			if(Character.isAlphabetic(tempChar)) {
				System.out.println("Character.isAlphabetic:"+tempChar);
			}
				
		}
		
		
		
	}

}

