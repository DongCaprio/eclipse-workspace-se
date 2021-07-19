package driver;

public class DriverLoadingMain {

	public static void main(String[] args) throws Exception {
		String driverClass1 = "basic.OracleDriver";
		String driverClass2 = "basic.MySqlDriver";
		Class.forName(driverClass1);
		Class.forName(driverClass2);

	}

}