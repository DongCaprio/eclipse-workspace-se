package com.itwill01.method;

/*
 * 성적처리를위해 필요한 학생객체를 만들기위한클래스(틀,타입)
 */
public class Student {
	/*
	 * << 속성 >> 번호,이름, 국어, 영어, 수학, 총점, 평균, 평점(A,B,C), 석차를 저장할 필드의선언
	 */
	int no;
	String name;
	int kor;
	int eng;
	int math;
	int tot;
	double avg;
	char grade;
	int rank;

	/*
	 * << 기능 >> 기본데이타입력,총점계산,평균계산,평점계산, 출력
	 */
	// 기본 데이터를 입력받을 메소드 선언
	public void basicData(int no, String name, int kor, int eng, int math) {
		this.no = no;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}

	// 총점을 계산할 메소드의 선언
	public void makeTot() {
		this.tot = this.kor + this.eng + this.math;
	}
	// 평균을 계산할 메소드
	public void makeAvg() {
		this.avg = this.tot / 3.0;
	}
	// 평점을 계산하는 메소드
	public void makeGrade() {
		if(this.avg >= 90) {
			this.grade = 'A';
		}else if(this.avg >= 80) {
			this.grade = 'B';
		}else if (this.avg >=70) {
			this.grade = 'c';
		}else if (this.avg >=60) {
			this.grade = 'D';
		}else 
			this.grade = 'F';
	}
	
	//출력하는 메소드
	public void printt() {
		System.out.printf("%2d %4s %d %4d %4d %5d %5.1f %3c %3d \n",
				this.no,this.name,this.kor,this.eng,this.math,this.tot,this.avg,this.grade,this.rank);
	}

}