
public class WhileNested {

	public static void main(String[] args) {
		/*
		 * ♥♥♥♥♥ ♥♥♥♥♥ ♥♥♥♥♥ ♥♥♥♥♥ ♥♥♥♥♥
		 */

		int i = 0;
		while (i < 5) {
			int j = 0;
			while (j < 5) {
				System.out.printf("%s[%d,%d]", "★", i, j);
				j++;
			}
			System.out.print("\n");
			i++;
		}

		System.out.println("2. ----------------------------");
		i = 0;
		while (i < 5) {
			int j = 0;
			while (j < 5) {
				if (i == j) {
					System.out.printf("%s", "☆");
				} else {
					System.out.printf("%s", "★");
				}
				j++;
			}
			i++;
			System.out.println();
			
		}

		System.out.println("3. ---------------------");
		i = 0;
		while (i < 5) {
			int j = 0;
			while (j < 5) {
				if (i > j) {
					System.out.printf("%s", "☆");
				} else {
					System.out.printf("%s", "★");
				}
				j++;
			}
			i++;
			System.out.println();
			
		}
	}

}
