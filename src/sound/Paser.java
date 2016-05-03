package sound;

import java.util.*;
import java.util.regex.*;

import sound.Token.TokenType;

/**
 * Parser for processing music staff
 * @author jiabin
 * Return all parameters used by sequencePlayer class, and music note
 */
public class Paser {
	
	private final Lexer currentLex;
	
	int tempo; //beats Per Minute in SequencePlayer 
	int duration; //The denominator of pitch length 
	
	int meterNumer;
	int meterDenom;
	
	Pattern p;
	Matcher m;
	
	//Map<String, Character> voice = new HashMap<String, Character>();
	List<Note> voiceNote; //List for storing note
	List<Integer> tickDuration = new ArrayList<Integer>(); //List for storing number of ticks
	List<String> header;
	
	/**
	 * Constructor for Parser
	 * @param lexer Lexer class, which is implemented to process regular expression
	 * Initialize two array lists for note and header, parameters for sequence player, and parse token
	 */
		
	public Paser(Lexer lexer){
		this.currentLex = lexer;
		voiceNote = new ArrayList<Note>(); 
		header = new ArrayList<String>();
		
		defaultInitalize();
		parsing();
	}
	
	/**
	 * Default parameter initialization
	 */
	private void defaultInitalize (){
		duration = 8;
		meterNumer = meterDenom = 4;
		tempo = 100;
	}
	/**
	 * parse token
	 * assign value to header, voiceNote array lists, tempo, duration, and meterNumer/meterDenom
	 */
	public void parsing () {
		
		for (Token token = this.currentLex.run(); token.getTokenType() != TokenType.END; token = this.currentLex.run()) {
			
			switch (token.getTokenType()){
			case INDEX_NUMBER: {
				String temp = "Index: " + token.getToken();
				header.add(temp);
			}break;
			
			case TITLE: {
				String temp = "Piece Title: " + token.getToken();
				header.add(temp);
			}break;
			
			case COMPOSER_NAME: {
				String temp = "Composer: " + token.getToken();
				header.add(temp);
			}break;
			
			case METER: {
				String fraction [] = token.getToken().split("/");
				meterNumer = Integer.parseInt(fraction[0]);
				meterDenom = Integer.parseInt(fraction[1]);
			}break;
			
			case NOTE_DURATION: {
				String fraction [] = token.getToken().split("/");
				duration = Integer.parseInt(fraction[1]);
			}break;
			
			case TEMPO: {
				tempo = Integer.parseInt(token.getToken());
			}break;
			
			case NOTE: {
				String element = token.getToken();
//System.out.println(element);
				//String accidental = processNote(m);
				String note = getNote (element);
				String octave = getOctave (element);
				String length = getLength (element);
				
//System.out.println("Note: " + note + " Octave: " + octave + " Duration: " + length);
				
				Note pitch = new Note(note, octave, length);
				voiceNote.add(pitch);
			}break;
			default:
				break;
			}
		}
	}
	
	/**
	 * get note by using regular expression
	 * @param str, string of words
	 * @return note, character of A-G or a-g, not null
	 */
	private String getNote (String str){
		
		String note = "";
		
		p = Pattern.compile(Lexer.basenote);
		m = p.matcher(str);
		
		if (m.find()){
			note = m.group(0);
		}
		return note;	
	}
	
	/**
	 * get voiceNote list
	 * @return voiceNote, array list storing note, octave, and length
	 */
	public List<Note> getVoice (){	
		return voiceNote;
	}
	
	/**
	 * get octave
	 * @param str, string of words, which may contains octave
	 * @return octave, either "`" or ",", which may be null
	 */
	private String getOctave (String str){
		
		String octave = "";
		
		p = Pattern.compile("('+)|(,+)");
		m = p.matcher(str);
		
		if (m.find()){
			octave = m.group(0);
		}
		return octave;	
	}

	/**
	 * get duration of a note
	 * @param str, string of words, which may contains a fraction 
	 * @return length, string of words, which may be null, fraction, or integer
	 */
	private String getLength (String str){
		
		String length = "";
		
		p = Pattern.compile(Lexer.note_length);
		m = p.matcher(str);
		
		if (m.find()){
			length = m.group(0);
		}
		
		if (length.contains("/")){
			
			if (length.length() == 1) {
				length = "1" + length + "2";
			}
			
			if (length.length() == 2) {
				length = "1" + length; 
			}
		}
			
		return length;	
	}
	
	public int getTempo(){
		return tempo;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public static void main(String[] args) {

	}

}
