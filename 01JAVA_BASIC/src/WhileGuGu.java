
public class WhileGuGu {
	public static void main(String[] args) {
		int i = 1;
		while (i <= 9) {
			int j = 2;
			while (j <= 9) {
				System.out.printf("%d * %d = %2d  ", j, i, i * j);
				j++;
			}

			System.out.print("\n");
			i++;
		}
		System.out.println(" 2. ----------------");
		/*
		 * ☆★★★★
		 * ★☆★★★
		 * ★★☆★★
		 * ★★★☆★
		 * ★★★★☆
		 */
		
		
		
	}
}
