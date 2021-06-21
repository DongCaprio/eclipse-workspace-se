package Prince.korea;

import President.korea.YS;

public class YSChild extends YS {
	public void access2() {
		this.member1=1;
		this.member2=2;
//		this.member3=3;
//		this.member4=4; 3,4메소드는 안됌
		
		this.method1();
		this.method2();
//		this.method3();
//		this.method4(); 이것도 3,4 안됌
	}
	
}
