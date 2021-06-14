
class ObjectChild extends Object{
	/*
	 * extends Object 생략가능
	 */
}


public class ObjectChildeMain {
	public static void main(String[] args) {
		ObjectChild oc = new ObjectChild();
		int hashCode = oc.hashCode();
		System.out.println(hashCode);
		System.out.println(Integer.toHexString(hashCode));
		
		//public Stirng toStinrg()
		String str = oc.toString();
		System.out.println(str);
		
	}
}
