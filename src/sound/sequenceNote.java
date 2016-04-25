package sound;

public class sequenceNote {
	
	private int pitch;
	private int duration;
	
	public sequenceNote(int pitch, int duration){
		this.pitch = pitch;
		this.duration = duration;
	}
	
	public int getPitch (){
		return pitch;
	}
	
	public int getDuration (){
		return duration;
	}
}
