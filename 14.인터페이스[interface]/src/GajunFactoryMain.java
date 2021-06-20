public class GajunFactoryMain {

	public static void main(String[] args) {
		System.out.println("------------가전제품공장-----------------");
		GajunTV tv1=new GajunTV();
		GajunTV tv2=new GajunTV();
		GajunTV tv3=new GajunTV();
		GajunAudio audio1=new GajunAudio();
		GajunAudio audio2=new GajunAudio();
		
		GajunOnOff gajun1=tv1;
		GajunOnOff[] gajunArray=new GajunOnOff[5];
		gajunArray[0]=tv1;
		gajunArray[1]=tv2;
		gajunArray[2]=tv3;
		gajunArray[3]=audio1;
		gajunArray[4]=audio2;
		System.out.println("--------------가전제품 검사소(볼륨,OnOff)-----------------");
		/*
		 * 난 절대로 자식클래스타입을 사용안할래요
		 * 난 부모타입[GajunVolume,GajunOnOff]만 사용할래요
		 * 그래야 가전제품 검사프로그램을 한번만들어서 변경없이 계속사용할수있으니까요
		 */
		GajunOnOff[] recvGajunArray=gajunArray;
		for (int i = 0; i < recvGajunArray.length; i++) {
			recvGajunArray[i].on();
			GajunVolume tempGajun=(GajunVolume)recvGajunArray[i];
			System.out.println(recvGajunArray[i]);
			tempGajun.up();
			tempGajun.down();
			recvGajunArray[i].off();
			System.out.println("----------검사끝 출고------------");
		}
	}

}



