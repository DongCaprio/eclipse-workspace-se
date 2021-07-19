package dao.address.first;

public class AddressDao1TestMain {

	public static void main(String[] args) throws Exception{
		AddressDao1 addressDao1 = new AddressDao1();
		System.out.println("1.insert");
		addressDao1.insert();
		System.out.println("2.update");
		addressDao1.updateByNo();
		System.out.println("3.delete");
		addressDao1.deleteByNo();
		System.out.println("4.selectByNo");
		addressDao1.selectByNo();
		System.out.println("5.selectAll");
		addressDao1.selectAll();
	}

}
