

public class OverloadingMain {

	public static void main(String[] args) {
		Overloading ol=new Overloading();
		ol.method();
		ol.method(12);
		ol.method(12,34);
		ol.method(12,24,46);
		ol.method("문자");
		ol.method("문자1","문자2");
		ol.method(12,34,"문자");
		ol.method("ㅇㄹㄹ", 6, 'a');
	}

}
