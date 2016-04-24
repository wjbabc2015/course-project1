package sound;

public class Note {
	
	private final String note;
	private final String octave;
	private final String length;
	private int lengthNumer = 1;
	private int lengthDenom = 1;
	
	public Note (String Note, String octave, String length)
	{
		this.note = Note;
		this.octave = octave;
		this.length = length;
		
		processLength();
	}
	
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
	
	public String getNote (){
		return note;
	}
	
	public String getOctave (){
		return octave;
	}
	
	public int getLengthNumer (){
		return lengthNumer;
	}
	
	public int getLengthDenom (){
		return lengthDenom;
	}
	
	public String toString(){
		return "Note( " + note + "|" + octave + "|" + lengthNumer + "/" + lengthDenom + " )";
	}
}
