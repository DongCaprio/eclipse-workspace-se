public class GajunAudio implements GajunVolume, GajunOnOff {
	public void operation1() {
		System.out.println("Audio.operation1()");
	}

	@Override
	public void on() {
		System.out.println("Audio.on()");
	}

	@Override
	public void off() {
		System.out.println("Audio.off()");
		
	}

	@Override
	public void up() {
		System.out.println("Audio.volumeUp()");
	}

	@Override
	public void down() {
		System.out.println("Audio.volumeDown()");
		
	}
	
}