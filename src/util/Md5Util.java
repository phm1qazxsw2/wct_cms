package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	public static String Md5(String plainText) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			md5 = buf.toString();
			
			//System.out.println("result: " + buf.toString());// 32位的加密

			//System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

}
