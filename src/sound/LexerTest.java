package sound;

import static org.junit.Assert.*;

import org.junit.Test;

import sound.Token.TokenType;

public class LexerTest {

	@Test
	public void testLexer1() {
		Lexer lexer = new Lexer ("X:1\n");
		String expectedToken = "1";
		
		assertEquals (lexer.run().getToken(), expectedToken);
	}
	
	@Test
	public void testLexer2() {
		Lexer lexer = new Lexer ("A1/4");
		String expectedToken = "A1/4";
		
		assertEquals (lexer.run().getToken(), expectedToken);
	}
	
	@Test
	public void testLexer3() {
		Lexer lexer = new Lexer ("M:4/4\nL:1/8\nQ:120\nK:C\nA1/4 A/4");
		String expectedToken = "4/4 1/8 120 C A1/4 A/4 ";
		StringBuilder testToken = new StringBuilder();
		
		for (int i = 0 ; i < 6 ; i ++){
			Token test = lexer.run();
			testToken.append (test.getToken() + " ");
//System.out.println(testToken);
		}
		
		String test = testToken.toString();
System.out.println(test);
		assertEquals (test, expectedToken);
	}
}
