package sound;

import java.util.*;
import java.util.regex.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Paser {
	
	String pitch;
	String header;
	
	int tempo;
	int TickPerQuarter;
	
	//Map<String, Character> voice = new HashMap<String, Character>();
	List voice = new ArrayList();
		
	public Paser(String pitch, String header){
		this.pitch = pitch;
		this.header = header;
		
		calTempo();
		calTick();
		processBody();
	}
	
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
	public void processBody(){
		
		//Remove white space from body part
		String body = pitch.replaceAll("\\s", "");
//System.out.println("Body is: " + body);
		
		for (int i = 0; i < body.length(); i ++){
			char t = body.charAt(i);
			
			if (Character.isLetter(t)){
				
				if (Character.isUpperCase(t)){
					voice.add(new Pitch(t).toMidiNote());
				}else if (Character.isLowerCase(t)){
					t = Character.toUpperCase(t);
					voice.add(new Pitch(t).transpose(Pitch.OCTAVE).toMidiNote());	
				}
			}
		}
	}
	
	public SequencePlayer translator() {
		SequencePlayer sequenceplayer = null;
		try {
			sequenceplayer = new SequencePlayer (tempo, TickPerQuarter);
			
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
