package lang;

public class StringUseMain {

	public static void main(String[] args) {
		String str1 = "Have a nice day!!!";
		String str2 = "오늘은 수요일 추상클래스를 공부할거여요.";

		System.out.println("----------------------length-------------");
		int length1 = str1.length();
		int length2 = str2.length();
		int length3 = "자바가 재미있네요".length();
		String emptyStr = "";
		int length4 = emptyStr.length();
		System.out.println(length1);
		System.out.println(length2);
		System.out.println(length3);
		System.out.println(length4);
		System.out.println("-------------null ,\"\"----------------");
		String id = null;
		// String id = "";
		if (id == null || id.equals("")) {
			System.out.println("아이디를 입력하세요!!!");
		}
		System.out.println("--------------String.substring-----------");
		System.out.println("Have a nice day!!!".substring(7));
		System.out.println("Have a nice day!!!".substring(7, 11));
		System.out.println("Have a nice day!!!".substring(7, 11).length());
		System.out.println("오늘은 수요일 추상클래스를 공부할거여요.".substring(15));
		System.out.println("오늘은 수요일 추상클래스를 공부할거여요.".substring(8, 13));
		System.out.println("------------------String.charAt(index)------------");
		String userId = "123test#00ABC$%";
		char firstChar = userId.charAt(0);
		System.out.println("'1'-->" + firstChar);
		System.out.println("'1'의 asciicode값-->" + (int) firstChar);
		for (int i = 0; i < userId.length(); i++) {
			char tempChar = userId.charAt(i);
			System.out.print(tempChar + "(" + (int) tempChar + ") ");

		}
		System.out.println();
		for (int i = 0; i < userId.length(); i++) {
			char tempChar = userId.charAt(i);
			if (tempChar >= 'a' && tempChar <= 'z') {
				System.out.println(tempChar + " -> 영문소문자");
			}
			if (tempChar >= 48 && tempChar <= 57) {
				System.out.println(tempChar + " -> 숫자형문자");
			}
		}
		/*
		 * Quiz: 아이디로 적합한지판단하시요 - 길이가 3~7 사이여야한다. - 영문,숫자로만이루어져 있어야한다. - 첫글자가 숫자여서는안된다
		 */
		System.out.println("+++++++++++++++++++++quiz 풀기+++++++++++++++++++++++ 아직다못품 꼭풀기");
		String[] idArray = { "guard", "7up", "test", " dfdj", "my id", "%super%", "아이디", "aaaaaaaaaa", "c" };
		for (int i = 0; i < idArray.length; i++) {
			if (idArray[i].length() >= 3 && idArray[i].length() <= 8) {
				System.out.println(idArray[i]);
			}
		}
//		for (int i = 0; i < idArray.length; i++) {
//			for (int j = 0; j < idArray.length; j++) {
//				char munja = idArray[i].charAt(j);
//				if((munja >= 'a' && munja <= 'z') || (munja >= '0' && munja <= '9')) {
//					System.out.println(idArray[i]);
//				}
//			}
//			
//		}

		System.out.println("-----------String.split()---------------");
		String cardNo= "2345-3434-234123";
		String ssn="201212-1482398";
		String nameList="김경호,신명숙,이은희,정봉화,신명숙";
		String[] cardNoArray= cardNo.split("-");
		String[] ssnArray= ssn.split("-");
		String[] nameArray= nameList.split(",");
		for (int i = 0; i < cardNoArray.length; i++) {
			System.out.println(i+"-->"+cardNoArray[i]);
		}
		for (int i = 0; i < ssnArray.length; i++) {
			System.out.println(i+"-->"+ssnArray[i]);
		}
		for (int i = 0; i < nameArray.length; i++) {
			System.out.println(i+"-->"+nameArray[i]);
		}
		
		System.out.println("-------------String.replace()----------------");
		String str3="우리는 자바를 공부합니다. 자바는 객체지향언어입니다. 자바를 자바라!";
		String result3 = str3.replace('자', '저');
		System.out.println(result3);
		System.out.println(str3.replace("자바","파이썬"));
		System.out.println(str3.replaceFirst("자바","파이썬"));
		System.out.println("-------------String.toUppercase, toLowercase--------------------");
		System.out.println("jaVA".toUpperCase());
		System.out.println("jaVA".toLowerCase());
		System.out.println("--------------equals, equalsIgnoreCase--------------------");
		System.out.println("yes".equals("YES"));
		System.out.println("yes".equalsIgnoreCase("YES"));
		System.out.println("------------String.indexOf()----------------------");
		String fileName = "xxx.gif";
		int dotIndex = fileName.indexOf(".");
		System.out.println("dotIndex:"+dotIndex);
		String fileNameNoExt= fileName.substring(0,dotIndex);
		String fileExt = fileName.substring(dotIndex);
		System.out.println(fileNameNoExt+"       "+fileExt);
		String copyFileName = fileNameNoExt+"-복사본."+fileExt;
		System.out.println(copyFileName);
		
		
		
	}

}
