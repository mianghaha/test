package string;

import org.apache.commons.lang.StringEscapeUtils;

public class Escape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String aString = "123''";
		System.out.println(StringEscapeUtils.escapeHtml(aString));
	}

}
