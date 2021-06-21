import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;

import com.itwill.shop.ShopService;
import com.itwill.shop.member.Member;
import com.itwill.shop.order.OrderService;
import com.itwill.shop.product.Product;
import com.itwill.shop.product.ProductService;

/*
 * java.lang package 있는 클래스들은 import구문생략가능
 * import java.lang.*;
 */

public class JavaClassImportMain {

	public static void main(String[] args) {

		/*
		 * java.lang package 있는 클래스들은 import구문생략가능
		 */
		java.lang.String str ="임포트";
		String name = "김경호";
		System.out.println("java.lang package는 import 생략가능");
		System.out.println((int)(Math.random()*10+1));
		System.out.println(java.lang.Math.PI);
		
		/*
		 * import 단축키
		 * ctrl+shift+o 꼭 외우기!!!!!!!!!!!!!!!!!!1
		 */
		
		InputStream in;
		OutputStream out;
		Socket socket;
		Connection con;
		
		ShopService shopService;
		Member m1;
		OrderService os;
		Product p;
		ProductService ps;
		
		
	}

}
