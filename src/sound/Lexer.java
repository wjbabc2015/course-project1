package sound;

import java.util.*;
import java.util.regex.*;

import sound.Token.TokenType;

/**
 * Lexer class
 * Implementing idea came from a project in github
 **/
public class Lexer {

	String lexerString;
	
	//Header elements
	public static final String fieldNnmber = "(X:[0-9]*\\n)";
	public static final String fieldTitle = "(T:.*\\n)";
	public static final String fieldComposer = "(C:.*\\n)";
	public static final String fieldMeter = "(M:[0-9]+/[0-9]+\\n)";
	public static final String fieldDefaultLength = "(L:[0-9]+/[0-9]+\\n)";
	public static final String fieldTempo = "(Q:[0-9]+\\n)";
	public static final String fieldKey = "(K:[A-Ga-g]+\\n)";
	public static final String headerRegex = fieldNnmber + "|" + fieldTitle + "|" + fieldComposer + "|" + fieldMeter + "|" + fieldDefaultLength 
												+ "|" + fieldTempo + "|" + fieldKey; // group 1 ~ 7
	
	//Body elements
	public static final String basenote = "[A-Ga-g]";
	public static final String accidental = "__?|\\^\\^?|="; //group 10
	public static final String octave = "['+,+]*";
	public static final String note_length = "[0-9]*/[0-9]*|[0-9]+";  // group 11
	public static final String barline = "(\\|\\]|\\|\\|?|\\[\\|\\s*)"; //group 12
	
	public static final String pitch = "(" + accidental + ")?" + basenote + octave;
	public static final String note = pitch + "(" + note_length + ")?"; //group 9 
	public static final String element = "((" + note + ")\\s*)"+ "|" + barline; // group 8
	
	public static final String abcRegex = headerRegex + "|" + element;
	
	
	Pattern pattern;
	Matcher matcher;
	
	private static HashMap<Integer, TokenType> bodyMap;
	
	int index = 0;
	
	//Define an array for storing symbol of header, which has the same order with regular expression group
	private static final TokenType[] tokenType = {
		TokenType.INDEX_NUMBER,
		TokenType.TITLE,
		TokenType.COMPOSER_NAME,
		TokenType.METER,
		TokenType.NOTE_DURATION,
		TokenType.TEMPO,
		TokenType.KEY,
		
	};
	
	/**
	 * Build a map for sorting body of piece
	 * Modify bodyMap by assigning value
	 **/
	private void buildMacthMap(){
		bodyMap = new HashMap<Integer, TokenType>();
		bodyMap.put(9, TokenType.NOTE);
		//bodyMap.put(10, TokenType.ACCIDENTAL);
		//bodyMap.put(11, TokenType.NOTE_LENGTH);
		bodyMap.put(12, TokenType.BARLINE);
	}

	/**
	 * Creator for Lexer
	 * @param piece, String of words. requires not null
	 **/
	public Lexer(String piece) {
		this.lexerString = piece;
		pattern = Pattern.compile(abcRegex);
//System.out.println(abcRegex);
		matcher = pattern.matcher(lexerString);
		buildMacthMap();
	}

	/**
	 * Extract token from piece 
	 * @throws IllegalArgumentException
	 * @return Token, a defined class. where the token type and token name are stored
	**/
	public Token run() throws IllegalArgumentException {
		
		//return null
		if (!matcher.find(index))
				return new Token ("", TokenType.END);
		
		String currentToken = matcher.group(0);
		this.index = matcher.end();
		
		//Scan group 1 ~ 7, and sort the header of piece
		for (int i = 0; i < tokenType.length ; ++i){
			if (matcher.group(i + 1) != null){
				TokenType currentTokenName = tokenType[i];
				currentToken = currentToken.replaceAll("[A-Z ]+:\\s*", "").replace("\n", "");

//System.out.println(i + 1 + ": " + matcher.group(i + 1));	

				return new Token(currentToken, currentTokenName);
			}
				
		}
		
		//Scan group 9 and 12, and return Token with current token type and token name
		for (int j : bodyMap.keySet()){
			if (matcher.group(j) !=null){
		
//System.out.println(j + ": " + currentToken);
			
				return new Token(currentToken.trim(), bodyMap.get(j));
			}
		}
		
		throw new RuntimeException("Regex error!");
	}
	
	/**
	 * Create for testing method
	**/
	public static void main(String args[]) {
		String lex = "X:1\nC:Unknown\nT:Simple scale\nM:4/4\nL:1/8\nQ:120\nK:C\nA1/4 A/4 A/ A A2 A3 A4 A6 A8| A,1/4 A,/4 A,/ A, A,2 A,3 A,4 A,6 A,8 |]";
		Lexer lexer = new Lexer(lex);
		//lexer.run();
for (int i = 0; i <50; ++i ){
		Token test = lexer.run();
System.out.println(test.getTokenType() + ": " + test.getToken());
}
	}
}
