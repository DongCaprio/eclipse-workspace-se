package dao.address.third;

import java.util.ArrayList;

public class AddressDao3Main {

	public static void main(String[] args) throws Exception{
		AddressDao3 addressDao3 = new AddressDao3();
		System.out.println(">> selectByNo");
		Address findAddress = addressDao3.selectByNo(30);
		System.out.println(findAddress);
		
		System.out.println(addressDao3.selectAll());
		
		addressDao3.insert(findAddress);
		addressDao3.insert(new Address(1, "박박", "박박박", "010-1111-1234", "경기도"));
		
		addressDao3.deleteByNo(4);
		addressDao3.updateByNo(new Address(6, "박박", "박박박", "010-1111-1234", "경기도"));
		
		System.out.println(addressDao3.selectByNo(8));
		System.out.println(addressDao3.selectAll());
//		addressDao3.deleteByNo(4);
//		
////		addressDao3.selectByNo(15);
//		
//		addressDao3.selectAll();
//		
//		
//		/***************select**************제일최근!*/
//		addressDao3.selectAll();
//		System.out.println(">> selectAll");
//		ArrayList<Address> addressList = addressDao3.selectAll();
//		for (Address address : addressList) {
//			System.out.println(address);
//		}
//		
//		
//		/************************case1 ***************************/
//		addressDao3.insert("zzz","김수로","888-9090","서울시민");
////		addressDao3.insert("ccc","김수미","234-1234","부산시민");
////		
////		addressDao3.updateByNo(9,"eight","팔팔팔","888-8888","팔팔시");
//		
//		/*********************** case 2 (DTO Address)*************************/
//		Address insertAddress = new Address(0, "vvv", "김부이", "111-9090", "제주시민");
//		addressDao3.insert(insertAddress);
//		Address updateAddress = new Address(22,"twotwo","투투투","222-2222","광주시민");
//		addressDao3.updateByNo(updateAddress);
//		addressDao3.insert("zzz", "김수로", "888-9090", "서울시민");
//		addressDao3.
//		addressDao3.
//		addressDao3.
//		
//		addressDao3.deleteByNo(8);
		
	}

}
