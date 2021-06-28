public class OuterClass {
	/*
	 * 인스턴스 멤버필드
	 */
	public int outer_member_field;
	/*
	 * 인스턴스멤버 메쏘드
	 */
	public void outer_member_method() {
		System.out.println("OuterClass.outer_member_method()");
	}
	/*
	 * 인스턴스멤버 클래스[Nested class,Inner Class,내부클래스]
	 */
	public class InnerClass{
		public int inner_member_field;
		public void inner_member_method() {
			System.out.println("InnerClass.inner_member_method()");
		}
		/*
		 내부클래스의 사용이유
		    - 내부클래스(객체)에서 외부클래스(객체)의
		      멤버필드,멤버메쏘드 접근을 손쉽게하기위해서 
		 */
		public void inner_outer_member_access() {
			//OuterClass.this.outer_member_field=7878;
			outer_member_field=7878;
			outer_member_method();
			
		}
		
	}
	
}