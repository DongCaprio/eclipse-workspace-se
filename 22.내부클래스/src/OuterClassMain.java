
public class OuterClassMain {

	public static void main(String[] args) {
		OuterClass oc = new OuterClass();
		oc.outer_member_field=4543;
		oc.outer_member_method();
		
		
		// 이 밑에것이 되긴하지만 이렇게 쓰지않는다
//		이너 클래스는 아우터 클래스에서만 사용한다
		OuterClass.InnerClass ic = oc.new InnerClass();
		ic.inner_member_field =3425;
		ic.inner_member_method();
	}

}
