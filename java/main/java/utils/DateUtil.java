package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class DateUtil {
	static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT);

	public static String transToString(Date date) {
		return fdf.format(date);
	}
	
	public static String transToString(Long date) {
		return fdf.format(date);
	}
	
	public static Date ParseString(String date) throws ParseException{
		return new SimpleDateFormat(DATE_FORMAT).parse(date);
	}
	
	public static Date ParseString(String date, String fomat) throws ParseException{
		return new SimpleDateFormat(fomat).parse(date);
	}
	
	public static void main(String args[]){
		try {
			System.out.println(ParseString("1982-12-12 00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
