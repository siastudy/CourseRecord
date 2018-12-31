package utils;

public class TimeUtils {
	
	/*
	 * Return total minutes from HH:MM:SS format time
	 * */
	public static Integer getMinutesFromHMS(String hms) {
		
		if(hms.length() == 8) {
			
			String[] str = hms.split("\\:");
			
			int h = Integer.parseInt(str[0]);
			int m = Integer.parseInt(str[1]);
			
			return h * 60 + m * 1 + 1;
			
		} else if (hms.length() == 5) {
			
			String[] str = hms.split("\\:");
			
			int m = Integer.parseInt(str[0]);
			
			return m * 1 + 1;
			
		}
		
		return 0;
		
	}

}
