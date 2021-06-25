package generic;

public class GenericMain {
	
	public static void main(String[] args) {
		System.out.println("**************NoGeneric********************");
		NoGenericClass noGenericClass = new NoGenericClass();
//		noGenericClass.setMember(new String("멤버"));
//		noGenericClass.setMember(new Integer(123));
//		noGenericClass.setMember(new Car());
		noGenericClass.setMember(new Account(1111,"FIRST", 100000,1.0));
		Account getAccount = (Account)noGenericClass.getMember();
		getAccount.print();
		System.out.println("************Generic******************");
		GenericClass<Account> genericClass = new GenericClass<Account>();
		genericClass.setMember(new Account(1111,"FISRT", 10000,1.0));
		getAccount = genericClass.getMember();
		getAccount.print();
		GenericClass<Car> genericClass2 = new GenericClass<Car>();
		genericClass2.setMember(new Car("1234", 12, 0,0));
		Car getCar = genericClass2.getMember();
		getCar.print();
		
	}
}
