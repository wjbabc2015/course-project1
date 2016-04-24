package sound;

import java.util.*;

public class Piece {
	
	private final Paser parser;
	private final List<Note> voice;
	
	
	public Piece (Paser p) {
		this.parser = p;
		voice = parser.getVoice();
	}
	
	public void pitchPrint (){
		for (int i = 0 ; i < voice.size() ; i ++){
			Note test = voice.get(i); 
//System.out.println(test.toString());
		}
	}
	
	
}
