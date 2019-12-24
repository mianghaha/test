package date;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		System.out.println("now=" + calendar.getTime());
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		System.out.println("sec0=" + calendar.getTime());
		calendar.set(Calendar.MINUTE, 0);
		System.out.println("min0=" + calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		System.out.println("hour0=" + calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		System.out.println("week0=" + calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("month0=" + calendar.getTime());
		Calendar clone = (Calendar)calendar.clone();
		clone.set(Calendar.DAY_OF_MONTH, 2);
		System.out.println("clone=" + clone.getTime() + ",calendar=" + calendar.getTime());
		
		Date end = clone.getTime();
		System.out.println(getAllInterval(calendar, end, Calendar.DAY_OF_YEAR, 5));
	}
	
	public static List<Long> getAllInterval(Calendar beginTime, Date endTime, int timeUtil, int intervalNum){
		int count = 0;
		List<Long> intervalList = new LinkedList<>();
		intervalList.add(beginTime.getTimeInMillis());
		
		Calendar tmp = (Calendar) beginTime.clone();
		while(count < intervalNum) {
			int num = tmp.get(timeUtil);
			tmp.set(timeUtil, num + 1);
			intervalList.add(tmp.getTimeInMillis());
			count++;
			if(endTime != null) {
				if(tmp.getTimeInMillis() >= endTime.getTime()) {
					break;
				}
			}
		}
		return intervalList;
	}

}
