package com.itwill05.service.student;

import java.util.Iterator;

//import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class StudentService {
	private Student[] students = { new Student(1, "KIM", 89, 93, 94), new Student(2, "HIM", 88, 77, 98),
			new Student(3, "AIM", 65, 87, 99), new Student(4, "KIM", 75, 97, 60), new Student(5, "XIM", 85, 98, 90),
			new Student(6, "TIM", 95, 88, 77), new Student(7, "KIM", 99, 93, 95), new Student(8, "LIM", 83, 80, 99),
			new Student(9, "QIM", 73, 90, 80),

	};

	/*
	 * 0.전체학생출력
	 */
	public void print() {
		Student.headerPrint();
		for (int i = 0; i < students.length; i++) {
			students[i].print();
		}
	}

	/*
	 * 0.학생객체받아서 추가
	 */
	public void addStudent(Student student) {
		Student[] newStudents = new Student[students.length + 1];
		for (int i = 0; i < students.length; i++) {
			newStudents[i] = students[i];
		}
		newStudents[students.length] = student;
		students = newStudents;
	}

	/*
	 * 1. 전체학생총점,평균,평점계산
	 */
	public void calculate() {
		for (int i = 0; i < students.length; i++) {
			students[i].calculateTotal();
			students[i].calculateAvg();
			students[i].calculateGrade();
		}
	}

	/*
	 * 2. 전체학생 총점으로 석차계산
	 */
	public void calculateRank() {

		for (int i = 0; i < students.length; i++) {
			students[i].setRank(1);
		}
		for (int i = 0; i < students.length; i++) {
			for(int j=0;j<students.length;j++) {
				if(students[i].getTot()< students[j].getTot()) {
					students[i].setRank(students[i].getRank()+1);
				}
			}
		}
	}

	/*
	 * 3. 전체학생반환
	 */
	public Student[] findAll() {
		return students;
	}

	/*
	 * 4. 번호3번 학생한명 반환
	 */
	public Student findByNo(int no) {
		Student findStudent = null;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getNo() == no) {
				findStudent = students[i];
				break;
			}
		}
		return findStudent;
	}
//이름으로 반환
//	public Student findByName(String name) {
//		Student findStudent = null;
//		for (int i = 0; i < students.length; i++) {
//			if (students[i].getName().equals(name)) {
//				findStudent = students[i];
//				break;
//			}
//		}
//		return findStudent;
//	}

	/*
	 * 5. 학점A인 학생들 반환
	 */
	public Student[] returnGradeA(char hakjum) {
		calculate();
		// Student[] gradeA = null;
		int numm = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getGrade() == hakjum) {
				numm++;
			}
		}
		Student[] gradeA = new Student[numm];
		for (int i = 0, j = 0; i < students.length; i++) {
			if (students[i].getGrade() == hakjum) {
				gradeA[j] = students[i];
				j++;
			}
		}
		return gradeA;
	}

	// 이름이 KIM인 학생들 반환
	public Student[] returnName(String name) {
		int numm = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getName().equals(name)) {
				numm++;
			}
		}
		Student[] nameWho = new Student[numm];
		for (int i = 0, j = 0; i < students.length; i++) {
			if (students[i].getName().equals(name)) {
				nameWho[j] = students[i];
				j++;
			}
		}
		return nameWho;
	}

	/*
	 * 6. 학생석차로 오름차순정렬
	 */

	public void ola() {
		// calculateRank();
		for (int i = 0; i < students.length; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j].getRank() > students[j + 1].getRank()) {
					Student temAccount = students[j + 1];
					students[j + 1] = students[j];
					students[j] = temAccount;
				}
			}
		}
	}

//	학생총점으로 오름차순
	public void olachog() {
		for (int i = 0; i < students.length; i++) {
			students[i].calculateTotal();
		}
		for (int i = 0; i < students.length; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j].getTot() > students[j + 1].getTot()) {
					Student temAccount = students[j + 1];
					students[j + 1] = students[j];
					students[j] = temAccount;
				}
			}
		}
	}

//	학생이름순으로 오름차순정렬
	public void nameola() {
		// calculateRank();
		for (int i = 0; i < students.length; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j].getName().compareTo(students[j + 1].getName()) > 0) {
					Student temAccount = students[j + 1];
					students[j + 1] = students[j];
					students[j] = temAccount;
				}
			}
		}
	}
}
