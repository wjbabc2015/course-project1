package sound;

import java.util.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Piece {
	
	private final Paser parser;
	private final List<Note> voice;
	
	SequencePlayer sequenceplayer;
	
	private int beatsPerMinute;
	private int ticksPerQuarterNote;
	
	private int topLengthDenom = 0;
	
	private List<sequenceNote> sequenceNote;
	
	public Piece (Paser p) {
		this.parser = p;
		voice = parser.getVoice();
		sequenceNote = new ArrayList<sequenceNote>();
		beatsPerMinute = p.getTempo();
		ticksPerQuarterNote = p.getDuration() / 4;
		
		calTick();
		calNote();
	}
	
	public void pitchPrint (){
		for (int i = 0 ; i < voice.size() ; i ++){
			Note test = voice.get(i); 
//System.out.println(test.toString());
		}
	}
	
	public void calTick (){
		for (int i = 0; i<voice.size(); i ++){
			Note note = voice.get(i);
			int lengthDenom = note.getLengthDenom();
			
			if (lengthDenom > topLengthDenom)
					topLengthDenom = lengthDenom;
		}
		
		ticksPerQuarterNote = ticksPerQuarterNote * topLengthDenom;
	}
	
	public void calNote(){
		for (int j = 0; j < voice.size(); j ++){
			Note note = voice.get(j);
			int pitch = 0;
			int duration = 0;
			
			char noteChar = note.getNote().charAt(0);
			String octave = note.getOctave();
			int lengthNumer = note.getLengthNumer();
			int lengthDenom = note.getLengthDenom();
			
			if (Character.isLetter(noteChar)){
				
				if (Character.isUpperCase(noteChar)){
					
					if (octave.equals(",")){
						pitch = new Pitch(noteChar).transpose(-Pitch.OCTAVE).toMidiNote();
					}else {
						pitch = new Pitch(noteChar).toMidiNote();
					}
					
				}else if (Character.isLowerCase(noteChar)){
					
					noteChar = Character.toUpperCase(noteChar);
					
					if (octave.matches("`")){
						pitch = new Pitch(noteChar).transpose(Pitch.OCTAVE).transpose(Pitch.OCTAVE).toMidiNote();
					}else if (octave.matches("``")){
						pitch = new Pitch(noteChar).transpose(Pitch.OCTAVE).transpose(Pitch.OCTAVE).transpose(Pitch.OCTAVE).toMidiNote();
					} else {
						pitch = new Pitch(noteChar).transpose(Pitch.OCTAVE).toMidiNote();
					}		
				}
			}
			duration = lengthNumer * topLengthDenom / lengthDenom;
System.out.println("Pitch: " + pitch + " Duration: " + duration);
			sequenceNote.add(new sequenceNote(pitch, duration));
		}
	}
	
	public SequencePlayer play(){		
		try {
			sequenceplayer = new SequencePlayer (beatsPerMinute, ticksPerQuarterNote);
			
			int startTick = 0;
			
			for (int i = 0; i < sequenceNote.size(); i ++) {
				sequenceplayer.addNote(sequenceNote.get(i).getPitch(), startTick, sequenceNote.get(i).getDuration());
				startTick += sequenceNote.get(i).getDuration();
			}
			
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sequenceplayer;
	}
}
