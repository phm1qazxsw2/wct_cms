<%@ page language="java" import="java.math.*,util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%

	
	long n1 = 1399626679;
	
	out.println("n1=" + Long.toHexString(n1));
	out.println("<br/>");
	
	
	
	byte[] b1 = { (byte)((0xFF000000&n1)>>24), (byte)((0xFF0000&n1)>>16), (byte)((0xFF00&n1)>>8), (byte)((0xFF)&n1) };
	byte[] b2 = { (byte)(0x96), (byte)(0x28), (byte)(0x3B), (byte)(0xC0) };
	byte[] b3 = new byte[4];
	
	
	
	for (int i=0; i<b1.length; i++) {
		b3[i] = (byte) (b1[i] ^ b2[i]);
	}
	
	out.println("b3=" + SymmetricCipher.byteArrayToHexString(b3));
	
	out.println("<br>");
	out.println("n5=b2304b64dcb691f675dc9082e6fa942d");
	
//	out.println("n1=" + n1.longValue());
//	out.println("<br/>");
//	out.println("n2=" + n2.);
//	out.println("<br/>");

//	BigInteger n3 = n1.xor(n2);
//	out.println("n3="+n3.longValue());
//	out.println("<br/>");
//	out.println(n3.toByteArray());
	
	byte[] b = SymmetricCipher.hexStringToByteArray("b2304b64dcb691f675dc9082e6fa942d");
	String s = new String(b);
	BigInteger n4 = new BigInteger(b);
	out.println("<br/>");
	out.println("n4=" + n4.toString());
	out.println("<br/>");
	out.println("n42=" + s);
%>