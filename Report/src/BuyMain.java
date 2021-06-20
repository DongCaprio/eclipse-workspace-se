
public class BuyMain {

	public static void main(String[] args) {

		Buyer b = new Buyer();
		
		Tv t = new Tv();
		b.buy(new Tv());
		b.buy(t);
		b.buy(new Computer());
		b.buy(new Audio());
		System.out.println(b.money);
		System.out.println(b.bonus);
		b.summary();
	}

}
