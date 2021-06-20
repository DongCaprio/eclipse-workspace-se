
public class Product {
	int price;
	int point;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(int money) {
		super();
		this.price = money;
		this.point=(int)(money/10);
	}
	
}
