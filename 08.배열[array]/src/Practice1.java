
public class Practice1 {

	public static void main(String[] args) {

		System.out.println("--------------학생데이타를 저장하기위한배열객체생성초기화-----------------");
		int[] noArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String[] nameArray = { "KIM", "LEE", "PARK", "CHOI", "SIM", "GOO", "PIM", "MIN", "AIM", "LIM" };
		int[] korArray = { 11, 22, 33, 44, 87, 66, 77, 88, 99, 100 };
		int[] engArray = { 11, 22, 33, 44, 87, 66, 77, 88, 99, 100 };
		int[] mathArray = { 11, 22, 33, 44, 87, 66, 77, 88, 99, 100 };
		int[] totArray = new int[10];
		double[] avgArray = new double[10];
		char[] gradeArray = new char[10];
		int[] rankArray = new int[10];

		/*
		 * 총점,평균,평점 계산
		 */
		// 총점
		for (int j = 0; j < totArray.length; j++) {
			totArray[j] = korArray[j] + engArray[j] + mathArray[j];
		}
		// 평균
		for (int i = 0; i < rankArray.length; i++) {
			avgArray[i] = totArray[i] / 3.0;
		}
		// 평점
		for (int i = 0; i < rankArray.length; i++) {
			if (avgArray[i] >= 90)
				gradeArray[i] = 'A';
			else if (avgArray[i] >= 80)
				gradeArray[i] = 'B';
			else if (avgArray[i] >= 70)
				gradeArray[i] = 'C';
			else if (avgArray[i] >= 60)
				gradeArray[i] = 'D';
			else
				gradeArray[i] = 'F';
		}
		// 석차 전부 플러스 1
		for (int i = 0; i < rankArray.length; i++) {
			rankArray[i]++;
		}
		/*
		 * [Quiz]석차계산
		 */
		for (int i = 0; i < rankArray.length; i++) {
			for (int j = 0; j < rankArray.length; j++) {
				if (totArray[i] < totArray[j])
					rankArray[i]++;
			}
		}
		/*
		 * 0번학생석차계산
		 */

		/*
		 * 3번학생 찾아서 1명 정보출력(학생번호는 중복되지않는다.)
		 */
		System.out.printf("---------------3번학생 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n", "학번", "이름", "국어", "영어", "수학", "총점", "평균", "평점", "석차");
		for (int i = 0; i < rankArray.length; i++) {
			if (noArray[i] == 3) {
				System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
						mathArray[i], engArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
			}
		}

		System.out.println();
		/*
		 * F학점 학생 모두찾아서 여러명 정보출력
		 */

		System.out.printf("---------------학생 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n", "학번", "이름", "국어", "영어", "수학", "총점", "평균", "평점", "석차");
		System.out.printf("-----------------------------------------------%n");
		for (int i = 0; i < rankArray.length; i++) {
			if (gradeArray[i] == 'F') {
				System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
						mathArray[i], engArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
			}
		}

		System.out.println();
		
		for (int i = 0; i < rankArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
					mathArray[i], engArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
		}
		/*
		 * [Quiz]오름차순(내림차순)정렬
		 */
		// 1회

		System.out.printf("---------------학생 성적출력[오름차순정렬]-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n", "학번", "이름", "국어", "영어", "수학", "총점", "평균", "평점", "석차");
		System.out.printf("-----------------------------------------------%n");

		for(int i=0;i<noArray.length-1;i++) {
			for(int j=0;j < noArray.length-1;j++) {
				if(totArray[j] < totArray[j+1]) {
					//swap
					//총점교환
					int tempTot=totArray[j+1];
					totArray[j+1]=totArray[j];
					totArray[j]=tempTot;
					
					//번호교환
					int tempNo = noArray[j+1];
					noArray[j+1]=noArray[j];
					noArray[j]=tempNo;
					//이름교환
					String tempName = nameArray[j+1];
					nameArray[j+1]=nameArray[j];
					nameArray[j]=tempName;
					
					//국어
					int tempKor = korArray[j+1];
					korArray[j+1]=korArray[j];
					korArray[j]=tempKor;
					//영어
					int tempEng = engArray[j+1];
					engArray[j+1]=engArray[j];
					engArray[j]=tempEng;
					//수학
					int tempMath = mathArray[j+1];
					mathArray[j+1]=mathArray[j];
					mathArray[j]=tempMath;
					//평균
					double tempAvg=avgArray[j+1];
					avgArray[j+1]=avgArray[j];
					avgArray[j]=tempAvg;
					//평점
					char tempGrade=gradeArray[j+1];
					gradeArray[j+1]=gradeArray[j];
					gradeArray[j]=tempGrade;
					//석자
					int tempRank=rankArray[j+1];
					rankArray[j+1]=rankArray[j];
					rankArray[j]=tempRank;
					
					
				}
			}
		}

		for (int i = 0; i < rankArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n", noArray[i], nameArray[i], korArray[i],
					mathArray[i], engArray[i], totArray[i], avgArray[i], gradeArray[i], rankArray[i]);
		}

	}

}
