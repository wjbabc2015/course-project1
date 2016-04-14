package sound;

import java.util.regex.*;

public class Lexer {

	String lexerString;
	
	//Header elements
	public static final String fieldNnmber = "X:[0-9]*\\n";
	public static final String fieldTitle = "T:.*\\n";
	public static final String fieldComposer = "C:.*\\n";
	public static final String fieldMeter = "M:[0-9]+/[0-9]+\\n";
	public static final String fieldDefaultLength = "L:[0-9]+/[0-9]+\\n";
	public static final String fieldTempo = "Q:[0-9]+\\n";
	public static final String fieldKey = "K:[A-Ga-g]+\\n";
	public static final String headerRegex = fieldNnmber + fieldTitle + fieldComposer + fieldMeter + fieldDefaultLength
												+ fieldTempo + fieldKey;
	
	//Body elements
	public static final String basenote = "(C|D|E|F|G|A|B|c|d|e|f|g|a|b)";
	public static final String accidental = "(\\^)+|(\\_)+";
	public static final String octave = "(\\')+|(\\,)+";
	public static final String note_length = "(\\d)*((\\/)?(\\d)*)";
	public static final String barline = "(\\Q|:\\E)|(\\Q|\\E)|(\\Q||\\E)|(\\Q:|\\E)";
	
	public static final String pitch = "(" + accidental + ")?(" + basenote
            + ")(" + octave + ")?";
	public static final String note = "(" + pitch +")(" + note_length + ")?";
	public static final String element = "(" + note + ")|(\\s+)|("+ barline + ")";
	
	public static final String abcRegex = "("+ headerRegex + ")((" + element + ")+)";
	
	Pattern pattern;
	Matcher matcher;
	
	String header;
	String body;

	public Lexer(String piece) {
		this.lexerString = piece;
		run();
	}
	/**
	 * Use regular expression to extract body and header from abc file separately 
	**/
	public void run() {
		// System.out.println(lexerString);
		pattern = Pattern.compile(abcRegex);
		matcher = pattern.matcher(lexerString);
		while (matcher.find()) {
			//String r = matcher.group();
			header = matcher.group(1);
			body = matcher.group(2);
			 //System.out.println(matcher.group(0));
			 //System.out.println("Found Value 1: " + matcher.group(1));
			 //System.out.println("Found Value 2: " + matcher.group(2));
		}
	}
	
	public String getHeader(){
		return header;
	}
	
	public String getBody(){
		return body;
	}

	/**
	 * Create for testing method
	**/
	public static void main(String args[]) {
		String lex = "X:1\nT:Simple scale\n";
		Lexer lexer = new Lexer(lex);
		lexer.run();
	}
}
