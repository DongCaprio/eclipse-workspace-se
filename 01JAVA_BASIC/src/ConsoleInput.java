import java.util.Scanner;

public class ConsoleInput {

	public static void main(String[] args) {
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("국어점수를 입력하세요:");
		int kor = console.nextInt();
		System.out.println("영어점수를 입력하세요:");
		int eng = console.nextInt();
		System.out.println("수학점수를 입력하세요:");
		int math = console.nextInt();
		
		System.out.println("----입력한 점수----");
		System.out.println(kor);
		System.out.println(eng);
		System.out.println(math);
		
		System.out.println("점수를 입력하세요(34 67 89)");
		kor = console.nextInt();
		eng = console.nextInt();
		math = console.nextInt();
		
		System.out.println("이름을 입력하세요");

	}

}
