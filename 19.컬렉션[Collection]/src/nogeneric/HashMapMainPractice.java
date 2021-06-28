package nogeneric;
import java.util.HashMap;
import java.util.Iterator;

public class HashMapMainPractice {

	public static void main(String[] args) {
		HashMap<String, Car> cc = new HashMap<String, Car>();
		System.out.println("map size : "+cc.size());
		
		cc.put("10", new Car("1111", 13));
		cc.put("20", new Car("2222", 13));
		cc.put("30", new Car("3333",15));
		cc.put("40", new Car("4444",17));
		cc.put("50", new Car("5555",20));
		System.out.println("map size : "+cc.size());
		System.out.println(cc);
		
		
//		cc.put("50", new Car("12345",99));
		cc.put("50", new Car("5555",20));
		System.out.println(cc);
		cc.put("50", new Car("5555",20));
		System.out.println(cc);
		System.out.println("map size : "+cc.size());
		
		cc.get("10").print();
		System.out.println(cc.keySet());
		
		//remove
		cc.remove("30");
		System.out.println(cc.keySet());
		
		//업무실행
		Car.headerPrint();
		Iterator<String> keyKey = cc.keySet().iterator();
		while(keyKey.hasNext()) {
			String key = keyKey.next();
			System.out.println("key = "+key);
			Car tmp = cc.get(key);
			if(tmp.getInTime() > 15) {
				tmp.print();
			}
		}
		
	}

}
