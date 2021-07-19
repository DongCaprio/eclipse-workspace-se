package dao.address.fourth;

/*
VO(Value Object),DTO(Data Transfer Object)
  	- 한개의 주소객체  데이타를 가지고있다.(VO)
  	- 한개의 주소객체  데이타이동(파라메타,리턴데이타)시키기위한객체(DTO)
  	- 특정 테이블과 동일한 속성을 멤버변수로가지는 객체
 */
/*
이름      널?       유형           
------- -------- ------------ 
NO      NOT NULL NUMBER(4)    
ID               VARCHAR2(20) 
NAME             VARCHAR2(50) 
PHONE            VARCHAR2(50) 
ADDRESS          VARCHAR2(60)
 */
public class Address {
	/*
	 * address table컬럼과 대응하는멤버필드를 가짐
	 */
	private int no;
	private String id;
	private String name;
	private String phone;
	private String address;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(int no, String id, String name, String phone, String address) {
		super();
		this.no = no;
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address [no=" + no + ", id=" + id + ", name=" + name + ", phone=" + phone + ", address=" + address
				+ "]";
	}

}