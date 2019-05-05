package string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String realname = "面";
		System.out.println(isInValidRealname(realname));
	}

	public static boolean isInValidRealname(String realname)
	{
		Pattern p = Pattern.compile(".*[\\pP\\pM\\pS\\pC\\pZ\\pN]+.*");
		Matcher m = p.matcher(realname);
		if (!m.matches())
		{
			return false;
		}
		return true;
	}
}
