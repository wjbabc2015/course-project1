package sound;

import java.util.*;
import java.util.regex.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Paser {
	
	String pitch;
	String header;
	
	int tempo; //beats Per Minute in SequencePlayer 
	int TickPerQuarter; 
	
	//Map<String, Character> voice = new HashMap<String, Character>();
	List voice = new ArrayList(); //List for storing note
		
	public Paser(String pitch, String header){
		this.pitch = pitch;
		this.header = header;
		
		calTempo();
		calTick();
		processBody();
	}
	
	/**
	 * Calculate the Tempo based on the parameter from header
	 **/
	public void calTempo(){
		Pattern p = Pattern.compile(Lexer.fieldTempo);
		Matcher m = p.matcher(header);
		
		while (m.find()){
			String t = m.group(0);
			String part [] = t.split(":");
			tempo = Integer.parseInt(part[1].replaceAll("\\n", ""));
//System.out.println("" + tempo);
		}
	}
	
	/**
	 * Calculate the Tick per Quarter based on the parameter from header
	 * Tick per Quarter = 1 / (4 * L)
	 **/
	public void calTick(){
		Pattern p = Pattern.compile(Lexer.fieldDefaultLength);
		Matcher m = p.matcher(header);
		
		while (m.find()){
			String t = m.group(0);
			String part [] = t.split("/");
			TickPerQuarter = Integer.parseInt(part[1].replaceAll("\\n", ""));
			TickPerQuarter = TickPerQuarter / 4;
//System.out.println("" + TickPerQuarter);		
		}
	}
/*	
	public int getTempo(){
		return tempo;
	}
	
	public int getTick(){
		return TickPerQuarter;
	}
*/	
	/**
	 * Parse the note, and convert all notes to integer by using method in Pitch class
	 **/
	public void processBody(){
		
		//Remove white space from body part
		String body = pitch.replaceAll("\\s", "");
//System.out.println("Body is: " + body);
		
		for (int i = 0; i < body.length(); i ++){
			char t = body.charAt(i);
			
			if (Character.isLetter(t)){
				
				//Upper case letter denote to Middle note
				if (Character.isUpperCase(t)){
					voice.add(new Pitch(t).toMidiNote());
				//Lower case letter donote to next higher octave
				}else if (Character.isLowerCase(t)){
					t = Character.toUpperCase(t);
					voice.add(new Pitch(t).transpose(Pitch.OCTAVE).toMidiNote());	
				}
			}
		}
	}
	
	/**
	 * Return a sequence player instance for playing notes
	 **/
	public SequencePlayer translator() {
		SequencePlayer sequenceplayer = null;
		try {
			//Initialize sequenceplayer
			sequenceplayer = new SequencePlayer (tempo, TickPerQuarter);
			
			//Add all notes in the instance
			for (int j = 0 ; j < voice.size(); j ++){
				sequenceplayer.addNote((int) voice.get(j), j, j + 1);
			}
			
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}	
		
		return sequenceplayer;
	}

	public static void main(String[] args) {
		
	}

}
