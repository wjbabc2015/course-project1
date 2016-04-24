package sound;

public class Token {
	
	//Resprent the token used by Lexer to sort the initial piece
	public static enum TokenType {
		//Header Tokens
		COMPOSER_NAME,
		KEY,
		NOTE_DURATION,
		METER,
		TEMPO,
		TITLE,
		INDEX_NUMBER,
	
		//Body Tokens
		NOTE,
		NOTE_LENGTH,
		ACCIDENTAL,
		REST,
		CHORD,
		DUPLET,
		TRIPLET,
		QUADRUPLET,
		REPEAT_FIRST_ENDING,
		REPEAT_SECOND_ENDING,
		START_REPEAT,
		END_REPEAT,
		BARLINE,
		END
	}
	
	private String token;
	private TokenType tokenType;
	
	/**
	 * Initializes a new token with the specified data. 
	 * @param token - must not be null
	 * @param tokenType - must not be null 
	 */
	public Token(String token, TokenType tokenType) {
		this.token = token;
		this.tokenType = tokenType;
	}
	
	public String getToken() {
		return token;
	}
	
	public TokenType getTokenType() {
		return tokenType;
	}
	
	
	
	
}
