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
	int duration;
	
	//Map<String, Character> voice = new HashMap<String, Character>();
	List<Integer> voice = new ArrayList<Integer>(); //List for storing note
	List<Integer> tickDuration = new ArrayList<Integer>(); //List for storing number of ticks
	
	
		
	public Paser(String pitch, String header){
		this.pitch = pitch;
		this.header = header;
		
		calTempo();
		calTick();
		calDur();
		//processBody();
	}
	
	/**
	 * Calculate the tempo based on the parameter from header
	 **/
	private void calTempo(){
		Pattern p = Pattern.compile(Lexer.fieldTempo);
		Matcher m = p.matcher(header);
		
		if (m.find()){
			String t = m.group(0);
			String part [] = t.split(":");
			tempo = Integer.parseInt(part[1].replaceAll("\\n", ""));
		}else {
			tempo = 100;
		}
//System.out.println("" + tempo);
	}
	
	/**
	 * Calculate the Tick per Quarter based on the parameter from header
	 * Tick per Quarter = 1 / (4 * L)
	 **/
	private void calTick(){
		Pattern p = Pattern.compile(Lexer.fieldDefaultLengthR);
		Matcher m = p.matcher(header);
		
		String lengthNumer, lengthDenom;
		
		if (m.find()){
			String t = m.group(1);
//System.out.println(m.group(0));
			String part [] = t.split("/");
			lengthNumer = part[0];
			lengthDenom = part[1];
			
			TickPerQuarter = Integer.parseInt(lengthDenom) / (4 * Integer.parseInt(lengthNumer));
		}else {
			TickPerQuarter = 2;
		}
//System.out.println("" + TickPerQuarter);	
	}
	
	/**
	 * Calculate the duration of a bar based on the parameter from header
	 * Duration = Meter / Length  
	 **/
	private void calDur(){
		Pattern p = Pattern.compile(Lexer.fieldMeterR);
		Matcher m = p.matcher(header);
		
		if (m.find()){
			String t = m.group(1);
//System.out.println(m.group(0));
			String part [] = t.split("/");
			String meterNumer = part[0];
			String meterDenom = part[1];
			
			duration = TickPerQuarter * 4 * Integer.parseInt(meterNumer) / Integer.parseInt(meterDenom);
			}else {
				duration = TickPerQuarter * 4;
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
	private void processBody(){
		
		//Remove white space from body part
		String body = pitch.replaceAll("\\s", "");
//System.out.println("Body is: " + body);
		
		for (int i = 0; i < body.length(); i ++){
			char current = body.charAt(i);
		
			if (Character.isLetter(current)){
				
				//Upper case letter denote to Middle note
				if (Character.isUpperCase(current)){
					voice.add(new Pitch(current).toMidiNote());
				//Lower case letter denote to next higher octave
				}else if (Character.isLowerCase(current)){
					current = Character.toUpperCase(current);
					voice.add(new Pitch(current).transpose(Pitch.OCTAVE).toMidiNote());	
				}
				
				char next = body.charAt(i + 1);
				if (Character.isDigit(next)){
					
					char afterNext = body.charAt(i + 2);
					
					if (Character.isLetter(afterNext)){
						tickDuration.add(Integer.parseInt("" + next));
					}else if (afterNext == '/'){
						char theLast = body.charAt(i + 3);
						
					}
				}else {
					tickDuration.add(1);
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
			//Initialize sequence player
			sequenceplayer = new SequencePlayer (tempo, TickPerQuarter);
			
			//Add all notes in the instance
			for (int j = 0 ; j < voice.size(); j ++){
				sequenceplayer.addNote(voice.get(j), j, j + 1);
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
