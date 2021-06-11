
public class Car {
	public static int count; //차량의 총생산대수
	private int no; //차번호
	private String model;//차모델명
	private String color;//차색상
	
	public Car() {
		// TODO Auto-generated constructor stub
	}

	public Car(int no, String model, String color) {
		this.no = no;
		this.model = model;
		this.color = color;
	Car.count++;
	}

	public static void headerPrint() {
		System.out.println("--------------------");
		System.out.println("번호 \t 모델 \t 색상");
		System.out.println("--------------------");
	}
	
	
	public void print()	{
		System.out.println(this.no+"\t"+this.model+"\t"+this.color);
	}
	
	
	
	

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
