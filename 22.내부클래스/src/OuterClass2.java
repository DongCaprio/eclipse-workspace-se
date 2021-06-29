
public class OuterClass2 {
	
	
	
	public int outer_member_field;
	public void outer_member_method1() {
		/********외부클래스작성*******/
		/* 
		 * 1.AClass,BClass,CInterface를 상속받은 자식클래스작성
		 * 2.AClass[methodA],BClass[methodB],CInterface[methodC]의 메쏘드를 재정의
		 * 3.AClass,BClass,CInterface를 상속받은 자식클래스객체생성
		 * 4.재정의메쏘드호출
		 */
		AClass aClassChild=new AClassChild();
		BClass bClassChild=new BClassChild();
		CInterface cInterfaceImpl=new CInterfaceImpl();
		aClassChild.methodA();
		bClassChild.methodB();
		cInterfaceImpl.methodC();
	}
	public void outer_member_method2() {
		/********내부클래스작성*******/
		/* 
		 * 1.AClass,BClass,CInterface를 상속받은 자식클래스를 OuterClass2내부 멤버로작성
		 * 2.AClass[methodA],BClass[methodB],CInterface[methodC]의 메쏘드를 재정의
		 * 3.AClass,BClass,CInterface를 상속받은 자식클래스객체생성
		 * 4.재정의메쏘드호출
		 */
		AClass aClass=new AClassChild2();
		BClass bClass=new BClassChild2();
		CInterface cInterface=new CInterfaceImpl2();
		aClass.methodA();
		bClass.methodB();
		cInterface.methodC();
	}
	
	public void outer_member_method3() {
	   /********익명의 local 내부클래스(anonymous local inner class)작성*******/
		/* 
		 * 1.AClass,BClass,CInterface를 상속받은 이름없는자식클래스를 
		 * 	 OuterClass2.outer_member_method3()메쏘드안에작성
		 * 2.AClass[methodA],BClass[methodB],CInterface[methodC]의 메쏘드를 재정의
		 * 3.AClass,BClass,CInterface를 상속받은 자식클래스객체생성
		 * 4.재정의메쏘드호출
		 */
		AClass aClass = new AClass() {
			@Override
			public void methodA() {
				System.out.println(
						"AClass를 상속받는 이름없는 클래스1에서 AClass.methodA()재정의");
			}
		};
		BClass bClass=new BClass() {
			@Override
			public void methodB() {
				System.out.println(
						"BClass를 상속받는 이름없는 클래스1에서 BClass.methodB() 구현(재정의)");
				
			}
			/*
			public void method1() {}
			*/
		};
		CInterface cInterface=new CInterface() {
			@Override
			public void methodC() {
				System.out.println(
						"CInterface를 구현[상속]하는 이름없는 클래스1에서 CInterface.methodC()구현(재정의)");
				
			}
		};
		
		
		aClass.methodA();
		bClass.methodB();
		//bClass.method1();//호출불가능
		cInterface.methodC();
		
	}
	
	public void outer_member_method4() {
		AClass aClass=new AClass() {
			@Override
			public void methodA() {
				System.out.println("AClass를 상속받는 이름없는 클래스2에서 AClass.methodA()재정의");
			}
		};
		BClass bClass=new BClass() {
			
			@Override
			public void methodB() {
				System.out.println(
						"BClass를 상속받는 이름없는 클래스2에서 BClass.methodB() 구현(재정의)");
			}
		};
		CInterface cInterface=new CInterface() {
			
			@Override
			public void methodC() {
				System.out.println(
						"CInterface를 구현[상속]하는 이름없는 클래스2에서 CInterface.methodC()구현(재정의)");
				
			}
		};
		System.out.println("---------java api------------");
		Object o=new Object() {
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "난 Object의 이름없는자식클래스의 재정의메소드A ";
			}
		};
		System.out.println(o.toString());
		System.out.println(
					new Object() {
						@Override
						public String toString() {
							return "난 Object의 이름없는자식클래스의 재정의메소드B ";
						}
					}.toString()
				);
	}
	
	/*****************내부클래스(member inner class)**************/
	/*
	 * 특정클래스를 상속받아서 메쏘드를 재정의한 Inner 클래스
	 */
	public class AClassChild2 extends AClass{
		@Override
		public void methodA() {
			System.out.println(
					"AClassChild2에서 AClass.methodA()재정의");
		}
	}
	public class BClassChild2 extends BClass{
		@Override
		public void methodB() {
			System.out.println(
					"BClassChild2에서 BClass.methodB() 구현(재정의)");
		}
	}
	public class CInterfaceImpl2 implements CInterface{
		@Override
		public void methodC() {
			System.out.println(
					"CInterfaceImpl2에서 CInterface.methodC()구현(재정의)");
		}
	}
	/*****************************************/

}