
public class Buyer {
	int money = 10000;
	int bonus = 0;
	public Buyer() {
		// TODO Auto-generated constructor stub
	}
	Product[] cart= new Product[10];
	int i =0;
	
	void buy(Product pp){
		if(this.money < pp.price) {
			System.out.println("넌못사");
			return;
		}
		this.money -= pp.price;
		this.bonus += pp.point;
		System.out.println(pp+"를 구입하셨습니다");
		cart[i] = pp;
		i++;
	}
	
	void summary() {
		int sum = 0;
		int pointSum = 0;
		String list = "";
		
		for (int i = 0; i < cart.length; i++) {
			if(cart[i] == null) {
				break;
			}
			sum += cart[i].price;
			pointSum += cart[i].point;
			list  += (cart[i] + ", ");
	}
		System.out.println("구입총액은 : "+sum);
		System.out.println("보너스총점은 : "+pointSum);
		System.out.println("장바구니는 "+list);
	}
	
}
