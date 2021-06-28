package nogeneric;
import java.util.HashMap;
import java.util.Iterator;

public class GenericHashMapMainPractice {

	public static void main(String[] args) {
		HashMap<Integer, Car> jucha = new HashMap<Integer, Car>();
		System.out.println(jucha.size());
		
		jucha.put(1, new Car("7272", 12));
		jucha.put(2, new Car("5758", 15));
		jucha.put(3, new Car("4801", 18));
		System.out.println(jucha.size());
		System.out.println(jucha);
		
		jucha.put(3, new Car("4810", 19));
		System.out.println(jucha);
		System.out.println(jucha.size());
		
//		jucha.remove(3);
//		System.out.println(jucha);
//		System.out.println(jucha.size());
		
		Iterator<Integer> cc = jucha.keySet().iterator();
		Car.headerPrint();
//		System.out.println(cc.next());
		while(cc.hasNext()) { //if(jucha.get(cc.next()).getInTime() > 14)   next 메서드에 뭔가 문제가 있는듯
			jucha.get(cc.next()).print();
//			Car a=jucha.get(cc.next());
//			a.print();
			
		}
		
		
		
		
	}

}
