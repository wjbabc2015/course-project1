package sound;

/**
 * Represents a single note.
 * @author jiabin
 * 
 */
public class Note {
	
	private final String note;
	private final String octave;
	private final String length;
	private int lengthNumer = 1;
	private int lengthDenom = 1;
	
	/**
	 * Creator for note
	 * @param Note, string of words. requires not null
	 * @param octave, string of words.
	 * @param length, string of words.
	 */
	public Note (String Note, String octave, String length)
	{
		this.note = Note;
		this.octave = octave;
		this.length = length;
		
		processLength();
	}
	
	/**
	 * Process the length of a note
	 */
	public void processLength(){
		String regex = "/";
		
		if (length != ""){
			if (length.contains(regex)){
				String part[] = length.split(regex);
				lengthNumer = Integer.parseInt(part[0]);
				lengthDenom = Integer.parseInt(part[1]);
			}else {
				lengthNumer = Integer.parseInt(length);
			}
		}
	}
	
	/**
	 * Get note
	 * @return note, string of words. requires not null
	 */
	public String getNote (){
		return note;
	}
	
	/**
	 * Get octave
	 * @return octave, string of words.
	 */
	public String getOctave (){
		return octave;
	}
	
	/**
	 * Get the numerator of length
	 * @return lengthNumer, integer. requires lengthNumer >= 1
	 */
	public int getLengthNumer (){
		return lengthNumer;
	}
	
	/**
	 * Get the Denominator of length
	 * @return lengthDenom, integer. requires lengthDenom >= 1
	 */
	public int getLengthDenom (){
		return lengthDenom;
	}
	
	public String toString(){
		return "Note( " + note + "|" + octave + "|" + lengthNumer + "/" + lengthDenom + " )";
	}
}
