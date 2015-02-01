package util;

import javax.crypto.*;
import javax.crypto.spec.*; 

public class SymmetricCipher
{
    final static String xform = "DES/ECB/NoPadding";      
    
    static String byteToHexString(byte b) 
    {
        String digits[] = { "0", "1", "2", "3", "4" , "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        int n = (int) b;
        if (n<0)
            n += 256;
        return digits[n/16 & 0xf ]+digits[n%16];
    }
    
    public static String byteArrayToHexString(byte[] b) 
    {
        String s = "";
        for(int i=0; i< b.length; i++)
            s+= byteToHexString(b[i]);            
        return s;        
    }
    
    public static byte hexToByte(char h1, char h2)
    {
        int n1=0, n2=0, n=0;
        
        if (h1>='A' && h1<='F')
            n1 = 10 + (h1 - 'A');
        else if (h1>='0' && h1<='9')
            n1 = h1 - '0';
        
        if (h2>='A' && h2<='F')
            n2 = 10 + (h2 - 'A');
        else if (h2>='0' && h2<='9')
            n2 = h2 - '0';
            
        n = n1 * 16 + n2;
        return (byte) n;
    }
    
    public static byte[] hexStringToByteArray(String hexstr)
    {        
        int len = hexstr.length()/2;
        byte[] ret = new byte[len];
        for (int i=0; i<len; i++)
        {
            ret[i] = hexToByte(hexstr.charAt(i*2), hexstr.charAt(i*2+1));
        }
        return ret;
    }
    
    static byte[] padbyte(byte[] b) {
        int pad = b.length % 8;
        if (pad>0)
        	pad = 8 - pad;
        byte[] newbytes = new byte[b.length + pad];
        for (int i=0; i<b.length; i++)
        	newbytes[i] = b[i];
        for (int i=0; i<pad; i++)
        	newbytes[b.length + i] = 0; 
        return newbytes;
    }
    
    static byte[] unpadbyte(byte[] b) {
	    int pad = 0;
	    for (int i=b.length-1; i>=0 && b[i]==0; i--)
	    	pad++;
	    byte[] newbytes = new byte[b.length - pad];
	    for (int i=0; i<newbytes.length; i++)
	    	newbytes[i] = b[i];
	    return newbytes;
    }
	    
    static byte[] encrypt(byte[] inpBytes, SecretKey key) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(xform);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // 2010/6/22 added by peter, must multiple of 8 bytes
        return cipher.doFinal(padbyte(inpBytes));
    }        

    static byte[] decrypt(byte[] inpBytes, SecretKey key) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(xform);    
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] tmp = cipher.doFinal(padbyte(inpBytes));
        return unpadbyte(tmp);
    }

    /*
    static byte[] decrypt(byte[] inpBytes, SecretKey key) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(xform);    
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] tmp = cipher.doFinal(inpBytes);
        int pad = 0;
        for (int i=tmp.length-1; i>=0 && tmp[i]==0; i--)
        	pad++;
        byte[] newbytes = new byte[tmp.length - pad];
        for (int i=0; i<newbytes.length; i++)
        	newbytes[i] = tmp[i];
        return newbytes;
    }
    */
    
    public static byte[] encodeECBBytes(String key, String plainText)
        throws Exception
    {
        SecretKey secretkey = new SecretKeySpec(key.getBytes(), "DES");
        return encrypt(plainText.getBytes(), secretkey);
    }    
    
    public static String encodeECBAsHexString(String key, String plainText)
        throws Exception
    {
        return byteArrayToHexString(encodeECBBytes(key, plainText));
    }
    
    public static String decodeECBString(String key, byte[] encryptBytes)
        throws Exception
    {
        SecretKey secretkey = new SecretKeySpec(key.getBytes(), "DES");
        byte[] b = decrypt(encryptBytes, secretkey);        
        return new String(b);
    }
     
    public static String padding8(String m)
    {
        int mod = m.length() % 8;
        if (mod > 0)
        {
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<(8-mod); i++)
                sb.append(' ');
            m = sb.toString() + m;   
        }        
        return m;
    }
    
    public static void main(String args[])
    {
        try
        {
            System.out.println("orig text=" + args[1] + " key=" + args[0]);
            System.out.println("encr hex=" + encodeECBAsHexString(args[0], args[1]));
            System.out.println("decr text=" + decodeECBString(args[0],encodeECBBytes(args[0], args[1])));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}