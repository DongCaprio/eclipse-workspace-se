package dao.address.fourth;

import java.util.ArrayList;

/*
AddressService객체
 * - 주소록 비즈니스로직(업무)을 수행하는 클래스
 * - Presentation객체(서블릿,JSP)에서 직접접근(메쏘드호출)하는 클래스
 * - AddressDao객체를 멤버변수로 가지고있다.
   - 주소록 업무수행메쏘드에서 DataBase접근(CRUD)이 필요하면 Dao를 사용한다.
 */
public class AddressService {
	private AddressDao addressDao;
	
	public AddressService() {
		addressDao=new AddressDao();
	}
	
	public Address selectByNo(int no) throws Exception {
		Address address = addressDao.selectByNo(no);
		return address;
	}
	public ArrayList<Address> selectAll() throws Exception {
		ArrayList<Address> addressList = addressDao.selectAll();
		return addressList;
	}
	public int insert(Address address) throws Exception {
		int insertRowCount = addressDao.insert(address);
		return insertRowCount;
	}
	public int deleteByNo(int no) throws Exception {
		return addressDao.deleteByNo(no);
		
	}
	public int updateByNo(Address updateAdress) throws Exception {
		return addressDao.updateByNo(updateAdress);
	}
}
