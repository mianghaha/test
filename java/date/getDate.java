package date;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class getDate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT);
		long date = 1478665080990L;
		int int_date = 1466069763;
		System.out.println(fdf.format(date)) ;

		Date date1 = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(System.currentTimeMillis());
		System.out.println(ca.get(ca.YEAR));
		
	}

}
