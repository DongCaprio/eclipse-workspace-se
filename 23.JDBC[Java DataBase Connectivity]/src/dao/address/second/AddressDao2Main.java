package dao.address.second;

public class AddressDao2Main {

	public static void main(String[] args) throws Exception{
		AddressDao2 addressDao2 = new AddressDao2();
		addressDao2.deleteByNo(10);
		
		addressDao2.selectByNo(15);
		
		addressDao2.selectAll();
		
		addressDao2.insert("zzz","김수로","888-9090","서울시민");
		addressDao2.insert("ccc","김수미","234-1234","부산시민");
		
		addressDao2.updateByNo(9,"eight","팔팔팔","888-8888","팔팔시");
	}

}
