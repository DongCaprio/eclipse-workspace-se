package dao.address.fourth;

public class AddressServiceTestMain {

	public static void main(String[] args) throws Exception{
		AddressService addressService = new AddressService();
		System.out.print("1. insert --> "+addressService.insert(new Address(0,"ooo","오오오","555-5555","오정리")));
		System.out.println("2.update    -->" + addressService.updateByNo(new Address(1, "first", "일일일", "111-1111", "일산")));
		System.out.println("3.selectByNo-->" + addressService.selectByNo(6));
		System.out.println("4.deleteByNo-->" + addressService.deleteByNo(14));
		System.out.println("5.selectAll-->" + addressService.selectAll());
	}

}
