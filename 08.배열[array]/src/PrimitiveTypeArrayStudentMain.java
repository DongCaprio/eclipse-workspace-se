
public class PrimitiveTypeArrayStudentMain {

	public static void main(String[] args) {
		/*
		 * 배열객체초기화
		 */
		System.out.println("--------------학생데이타를 저장하기위한배열객체생성초기화-----------------");
		int[] noArray = {1,2,3,4,5,6,7,8,9,10};
		String[] nameArray= {"KIM","LEE","PARK","CHOI","SIM","GOO","PIM","MIN","AIM","LIM"};
		int[] korArray=  {12,56,12,12,67,88,93,67,99,56};
		int[] engArray=  {98,66,89,89,73,34,78,22,99,32};
		int[] mathArray= {93,76,42,42,47,77,95,61,78,20};
		int[] totArray=  {0,0,0,0,0,0,0,0,0,0};
		double[] avgArray= {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		char[] gradeArray= {'F','F','F','F','F','F','F','F','F','F'};
		int[] rankArray= {1,1,1,1,1,1,1,1,1,1};
		/*
		 * 총점,평균,평점 계산
		 */
		for (int i = 0; i < noArray.length; i++) {
			totArray[i] =korArray[i]+engArray[i]+mathArray[i];
			avgArray[i] = totArray[i]/3.0;
			if(avgArray[i]>=90) {
				gradeArray[i]='A';
			}else if(avgArray[i]>=80) {
				gradeArray[i]='B';
			}else if(avgArray[i]>=70) {
				gradeArray[i]='C';
			}else if(avgArray[i]>=60) {
				gradeArray[i]='D';
			}else {
				gradeArray[i]='F';
			}
		}
		/*
		 * [Quiz]석차계산
		 */
		
		/*
		 * 0번학생석차계산
		 */
		for(int i=0;i<noArray.length;i++) {
			for(int j=0;j<noArray.length;j++) {
				if(totArray[i]<totArray[j]) {
					rankArray[i]++;
				}
			}
		}
		/*
		 * 3번학생 찾아서 1명 정보출력(학생번호는 중복되지않는다.)
		 */
		System.out.println(">>3번학생 찾아서 1명 정보출력(학생번호는 중복되지않는다.)");
		for (int i = 0; i < noArray.length; i++) {
			if(noArray[i]==3) {
				System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
						noArray[i],nameArray[i],korArray[i],engArray[i],mathArray[i],totArray[i],avgArray[i],gradeArray[i],rankArray[i]);
				break;
			}
		}
		
		/*
		 * F학점  학생 모두찾아서 여러명 정보출력
		 */
		System.out.println(">>F학점  학생 모두찾아서 여러명 정보출력");
		for (int i = 0; i < gradeArray.length; i++) {
			if(gradeArray[i]=='F') {
				System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
						noArray[i],nameArray[i],korArray[i],engArray[i],mathArray[i],totArray[i],avgArray[i],gradeArray[i],rankArray[i]);
			}
		}
		
		
		System.out.printf("---------------학생 성적출력-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n",
						"학번","이름","국어","영어", "수학","총점","평균", "평점","석차");
		System.out.printf("-----------------------------------------------%n");
		
		for (int i = 0; i < noArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
					noArray[i],nameArray[i],korArray[i],engArray[i],mathArray[i],totArray[i],avgArray[i],gradeArray[i],rankArray[i]);
		}
		/*
		 * [Quiz]오름차순(내림차순)정렬
		 */
		//1회
		for(int i=0;i<noArray.length-1;i++) {
			for(int j=0;j < noArray.length-1;j++) {
				if(totArray[j] > totArray[j+1]) {
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
		
		System.out.printf("---------------학생 성적출력[오름차순정렬후]-------------------%n");
		System.out.printf("%s %s %s %s %s %s %3s %s %s%n",
						"학번","이름","국어","영어", "수학","총점","평균", "평점","석차");
		System.out.printf("-----------------------------------------------%n");
		
		for (int i = 0; i < noArray.length; i++) {
			System.out.printf("%3d %5s %4d %4d %4d %4d %5.1f %3c %4d%n",
					noArray[i],nameArray[i],korArray[i],engArray[i],mathArray[i],totArray[i],avgArray[i],gradeArray[i],rankArray[i]);
		}
		
		
	}
}