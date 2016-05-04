package sound;

/**
 * Represents a single pitch.
 * @author jiabin
 * 
 */
public class sequenceNote {
	
	private int pitch;
	private int duration;
	
	/**
	 * Creator for sequenceNote
	 * @param pitch, integer. requires pitch> 0
	 * @param duration, integer. requires duration > 1
	 */
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
