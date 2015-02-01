

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import util.*;

public class VerifySource {
    public VerifySource() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	String[] channels = { "CCTV1", "CCTV2", "CCTV3" };
    	try {
    		for (int i=0; i<channels.length; i++) {
	    		String url = "http://112.124.124.186/realAddress!getJson.do?srcType=live&url=http://[";
	    		url += channels[i];
	    		url += "]";
	    		
	    		
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
