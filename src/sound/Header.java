package sound;

import java.util.*;

public class Header {
	
	String header;
	private Map<String, String> headerElements;
	
	public Header (String header){
		this.header = header;
	}
	
	public Map<String, String> getHeader(){
		headerElements = new HashMap<String, String>();
		return headerElements;
	}

}
