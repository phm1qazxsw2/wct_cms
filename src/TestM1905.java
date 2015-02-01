

import java.io.*;
import java.util.*;
import java.net.*;

import util.*;

public class TestM1905 {
    public TestM1905() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	try {
    		String url1 = "http://flv.vodfile.m1905.com/movie/2014/06/09/m20140609YRDIOFH10IQO1DLD/m20140609YRDIOFH10IQO1DLD.flv";
    		String url2 = "http://flv1.vodfile.m1905.com/movie/2014/06/09/m20140609YRDIOFH10IQO1DLD/m20140609YRDIOFH10IQO1DLD.flv";

    		byte[] b = new byte[1024];
    		URL u1 = new URL(url1);
    		URLConnection uc1 = u1.openConnection();
    		try {
    			InputStream is1 = uc1.getInputStream();
    			System.out.println("success getting inputstream1");
    		}
    		catch (Exception e) {
    			System.out.println("error in getting inputstream1");
    		}

    		URL u2 = new URL(url2);
    		URLConnection uc2 = u2.openConnection();
    		try {
    			InputStream is2 = uc2.getInputStream();
    			System.out.println("success getting inputstream2");
    		}
    		catch (Exception e) {
    			System.out.println("error in getting inputstream2");
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
