package util;

import java.io.*;

public class FileUtil
{
	public static String readFile(String file) throws Exception {
		File f = new File(file);
		FileInputStream fin = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fin,
				"UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}
		return sb.toString();
	}
	
	public static void Copy(File oldfile, String newPath)    
    {    
          try {    
              int bytesum = 0;    
              int byteread = 0;    
              if (oldfile.exists()) {      
                  InputStream     inStream     =     new     FileInputStream(oldfile);     
                  FileOutputStream     fs     =     new     FileOutputStream(newPath);    
                  byte[]     buffer     =     new     byte[1444];    
                  while     (     (byteread     =     inStream.read(buffer))     !=     -1)     {    
                          bytesum     +=     byteread;        
                          System.out.println(bytesum);    
                          fs.write(buffer,     0,     byteread);    
                  }    
                  inStream.close();    
              }    
          }    
          catch (Exception e) {    
              System.out.println( "error  ");    
              e.printStackTrace();    
          }    
	}	
}