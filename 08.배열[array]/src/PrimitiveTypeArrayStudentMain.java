
public class PrimitiveTypeArrayStudentMain {

	public static void main(String[] args) {
		/*
		 * 배열객체초기화
		 */
		System.out.println("-------학생데어터를 저장하기위한 배열객체 생성초기화----------------");
		int[] noArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String[] nameArray = { "KIM", "LEE", "PARK", "CHOI", "SIM", "GOO", "PIM", "MIN", "AIM", "LIM" };
		int[] korArray = { 34, 56, 34, 56, 32, 12, 64, 46, 55, 44 };
		int[] engArray = { 100, 4554, 34, 56, 32, 12, 64, 46, 55, 44 };
		int[] mathArray = { 1000, 45, 34, 56, 32, 12, 64, 46, 55, 44 };
		int[] totArray = { 10000, 56, 237, 56, 32, 12, 64, 46, 55, 44 };
		double[] avgArray = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		char[] gradeArray = { 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F' };
		int[] rankArray = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		// 총점 평균 평점 계산
		for (int i = 0; i < noArray.length; i++) {
			totArray[i] = korArray[i] + engArray[i] + mathArray[i];
			avgArray[i] = totArray[i] / 3.0;

			if (avgArray[i] >= 90) {
				gradeArray[i] = 'A';
			} else if (avgArray[i] >= 80) {
				gradeArray[i] = 'B';
			} else if (avgArray[i] >= 70) {
				gradeArray[i] = 'C';
			} else if (avgArray[i] >= 60) {
				gradeArray[i] = 'D';
			} else {
				gradeArray[i] = 'F';
			}
		}

		// Quiz 석차계산
		for (int j = 0; j < noArray.length; j++) {
			if (totArray[0] < totArray[j]) {
				rankArray[0]++;
			}
		}

		// 3번학생 찾아서 1명 정보출력(학생번호는 중복되지 않는다)
		System.out.println("3번학생 찾아서 1명 정보출력(학생번호는 중복되지 않는다)");
		for (int i = 0; i < noArray.length; i++) {
			if (noArray[i] == 3)
				System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
						engArray[i], mathArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
			break;
		}

		// F학점 학생 모두 찾아서 여러명 정보 출력
		System.out.println("F학점 학생 모두 찾아서 여러명 정보 출력");
		for (int i = 0; i < gradeArray.length; i++) {
			if (gradeArray[i] == 'F') {
				if (noArray[i] == 3)
					System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
							engArray[i], mathArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
			}
		}

		System.out.printf("---------------학생 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n", "학번", "이름", "국어", "영어", "수학", "총점", "평균", "평점", "석차");
		System.out.printf("-----------------------------------------------%n");

		for (int i = 0; i < noArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
					engArray[i], mathArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
		}

		// 오름차순 정렬
		// 1회
		for (int j = 0; j < noArray.length-1; j++) {
			if (totArray[j] > totArray[j + 1]) {
				int tempNo = noArray[j];
				noArray[j] = noArray[j + 1];
				noArray[j + 1] = tempNo;
				//이름교환
				String tempName = nameArray[j];
				nameArray[j]=nameArray[j+1];
				nameArray[j+1]=tempName;
			
			}
		}
		
		
		
		
		
		
		
		
		System.out.printf("---------------[정 렬 후 ] 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n", "학번", "이름", "국어", "영어", "수학", "총점", "평균", "평점", "석차");
		System.out.printf("-----------------------------------------------%n");
		
		for (int i = 0; i < noArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
					engArray[i], mathArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
		}

	}

}
