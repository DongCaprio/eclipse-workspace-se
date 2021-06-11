public class StaticMain {

	public static void main(String[] args) {
		System.out.println("-----static member access--------");
		/*
		 * 정적멤버접근
		 *   - 클래스이름.정적멤버이름
		 *   - Static.static_field
		 */
		System.out.println("1.Static.static_field:"+Static.static_field);
		Static.static_field=898989;
		System.out.println("2.Static.static_field:"+Static.static_field);
		Static.static_method();
		System.out.println("-----instance member access--------");
		
		Static static1=new Static();
		System.out.println(static1);
		static1.instance_field=555;
		System.out.println(static1.instance_field);
		static1.instance_method();
		
		Static static2=new Static();
		System.out.println(static2);
		static2.instance_field=3333;
		System.out.println(static2.instance_field);
		static2.instance_method();
		
		System.out.println("----------객체주소를 사용해서 static멤버접근-----------");
		/*
		 The static field Static.static_field should be accessed in a static way
		 */
		static1.static_field=8989;
		static1.static_method();
		System.out.println("static1.static_field:"+static1.static_field);
		System.out.println("static2.static_field:"+static2.static_field);
		System.out.println("Static.static_field:"+Static.static_field);
		
		
		
	}

}