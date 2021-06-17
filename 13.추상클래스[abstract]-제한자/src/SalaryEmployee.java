public class SalaryEmployee extends Employee {
	private int annualSalary;//연봉
	public SalaryEmployee() {
		// TODO Auto-generated constructor stub
	}
	
	public SalaryEmployee(int no,String name,int annualSalary) {
		super(no, name);
		this.annualSalary = annualSalary;
	}
	public void print() {
		super.print();
		System.out.println(this.annualSalary);
	}
	public int getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(int annualSalary) {
		this.annualSalary = annualSalary;
	}

	@Override
	public void caculatePay() {
		int pay = this.annualSalary/12;
		this.setPay(pay);
	}

}
