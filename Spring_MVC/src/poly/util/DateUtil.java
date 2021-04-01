package poly.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getDateTime (String fm) {
		
		Date toDay = new Date();
		System.out.println(toDay);
		
		SimpleDateFormat date = new SimpleDateFormat();
		
		
		return date.format(toDay);
	}
	
	public static String getDateTime() {
	
		
		return getDateTime("yyyy.MM.dd");
	}
	
}
