import java.util.*;
import java.text.*;


public class TestHuobi {
    public TestHuobi() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    		Date d1 = sdf.parse("20140901 00:00:00");
    		Date d2 = sdf.parse("20140911 00:00:00");
    		System.out.println("d1=" + d1.getTime() + " d2=" + d2.getTime());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
