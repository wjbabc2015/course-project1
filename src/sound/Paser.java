package sound;

import java.util.*;
import java.util.regex.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.Token.TokenType;

public class Paser {
	
	private final Lexer currentLex;
	
	int tempo; //beats Per Minute in SequencePlayer 
	int TickPerQuarter;
	int duration;
	
	int meterNumer;
	int meterDenom;
	
	Pattern p;
	Matcher m;
	
	//Map<String, Character> voice = new HashMap<String, Character>();
	List<Note> voiceNote; //List for storing note
	List<Integer> tickDuration = new ArrayList<Integer>(); //List for storing number of ticks
	List<String> header;
	
	
		
	public Paser(Lexer lexer){
		this.currentLex = lexer;
		voiceNote = new ArrayList<Note>(); 
		header = new ArrayList<String>();
		
		defaultInitalize();
		parsing();
	}
	
	private void defaultInitalize (){
		duration = 8;
		meterNumer = meterDenom = 4;
		TickPerQuarter = 100;
	}
	
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
				TickPerQuarter = Integer.parseInt(token.getToken());
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
			}
		}
	}
	
	private String getNote (String str){
		
		String note = "";
		
		p = Pattern.compile(Lexer.basenote);
		m = p.matcher(str);
		
		if (m.find()){
			note = m.group(0);
		}
		return note;	
	}
	
	public List<Note> getVoice (){	
		return voiceNote;
	}
	
	private String getOctave (String str){
		
		String octave = "";
		
		p = Pattern.compile("('+)|(,+)");
		m = p.matcher(str);
		
		if (m.find()){
			octave = m.group(0);
		}
		return octave;	
	}

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
	
	public static void main(String[] args) {

	}

}
