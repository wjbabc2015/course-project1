package sound;

import java.util.regex.*;

public class Lexer {

	String lexerString;
	public static final String fieldNnmber = "X:[0-9]*\\n";
	public static final String fieldTitle = "T:.*\\n";
	public static final String fieldComposer = "C:.*\\n";
	public static final String fieldMeter = "M:[0-9]+/[0-9]+\\n";
	public static final String fieldDefaultLength = "L:[0-9]+/[0-9]+\\n";
	public static final String fieldTempo = "Q:[0-9]+\\n";
	public static final String fieldKey = "K:[A-Ga-g]+\\n";
	public static final String headerRegex = "(" + fieldNnmber + fieldTitle + fieldComposer + fieldMeter + fieldDefaultLength
												+ fieldTempo + fieldKey + ")";
	Pattern pattern;
	Matcher matcher;

	public Lexer(String piece) {
		this.lexerString = piece;
	}

	public String run() {
		// System.out.println(lexerString);
		pattern = Pattern.compile(headerRegex);
		matcher = pattern.matcher(lexerString);
		String result = "";
		if (matcher.find()) {
			result = matcher.group(1);
			// System.out.println(matcher.group(0));
			// System.out.println("Found Value: " + matcher.group(1));
			// System.out.println("Found Value: " + matcher.group(2));
		} else {
			System.out.println("No Match");
		}
		return result;
	}

	public static void main(String args[]) {
		String lex = "X:1\nT:Simple scale\n";
		Lexer lexer = new Lexer(lex);
		lexer.run();
	}
}
