package practice;

import java.util.Date;

public class GetCurrentDateAndTime {

	public static void main(String[] args) {
		Date d = new Date();
		String date = d.toString().replace(" ","_").replace(":", "_");
		System.out.println(date);
	}

}
