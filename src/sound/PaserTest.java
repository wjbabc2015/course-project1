package sound;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PaserTest {

	@Test
	public void testGetVoice() {
		Lexer lexer = new Lexer ("A1/4 A/4");
		Paser parser = new Paser(lexer);
		
		List<Note> voice = new ArrayList<Note>();
		StringBuilder actual = new StringBuilder();
		String expected = "Note( A||1/4 ) Note( A||1/4 ) ";
		
		voice = parser.getVoice();
		
		for (int i =0; i<voice.size(); i ++){
			actual.append(voice.get(i).toString() + " ");
		}
		
		String test = actual.toString();
//System.out.println(test);
		assertEquals (test, expected);
	}

	@Test
	public void testGetTempo() {
		Lexer lexer = new Lexer ("Q:120\n");
		Paser parser = new Paser(lexer);
		int expectedTempo = 120;
		parser.parsing();
		
		int test = parser.getTempo();
		
		assertTrue (test == expectedTempo);
	}

	@Test
	public void testGetDuration() {
		Lexer lexer = new Lexer ("L:1/8\n");
		Paser parser = new Paser(lexer);
		int expectedDuration = 8;
		parser.parsing();
		
		int test = parser.getDuration();
		
		assertTrue (test == expectedDuration);
	}

}
