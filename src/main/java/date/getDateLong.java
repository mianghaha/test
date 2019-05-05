package date;

import java.util.Calendar;

public class getDateLong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 10);
		calendar.set(Calendar.DAY_OF_MONTH, 9);
		calendar.set(Calendar.HOUR_OF_DAY,12);
		System.out.println(calendar.getTimeInMillis());
		
	}

}
