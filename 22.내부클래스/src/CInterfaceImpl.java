public class CInterfaceImpl 
	implements CInterface {

	@Override
	public void methodC() {
		System.out.println(
				"CInterfaceImpl에서 CInterface.methodC()구현(재정의)");
	}

}