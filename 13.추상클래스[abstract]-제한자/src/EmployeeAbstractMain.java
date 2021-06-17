
public class EmployeeAbstractMain {

	public static void main(String[] args) {
		SalaryEmployee emp1 = new SalaryEmployee(1, "KIM", 30000000);
		HourlyEmployee emp2 = new HourlyEmployee(2, "JIM", 100, 20000);

		emp1.caculatePay();
		emp2.caculatePay();
		emp1.print();
		emp2.print();

		Employee e1 = emp1;
		Employee e2 = emp2;

		Employee[] emps = new Employee[5];
		emps[0] = e1;
		emps[1] = e2;
		emps[2] = new SalaryEmployee(3, "DIM", 35000000);
		emps[3] = new SalaryEmployee(4, "SIM", 28000000);
		emps[4] = new HourlyEmployee(5, "FIM", 120, 15000);
		System.out.println();
		for (int i = 0; i < emps.length; i++) {
			if (emps[i] instanceof SalaryEmployee) {
				emps[i].caculatePay();
				System.out.println("-------" + emps[i].getName() + " 님 급여명세표------------");
				emps[i].print();
				System.out.println("------------------------------------------------------");
			}
		}

	}

}
