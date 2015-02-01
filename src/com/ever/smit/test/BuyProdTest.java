package com.ever.smit.test;

import com.ever.smit.util.StringUtils;

public class BuyProdTest {


        /**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String urlBuf = "http://112.124.44.254:8080/smcp/ali/smit/service";
			String response = null;
			String getChallenge = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			getChallenge += "<request method=\"buyProd\">";
			getChallenge += "<parameters>";
			getChallenge += "<token>"+"token"+"</token>";
			getChallenge += "<prodId>"+"prodId"+"</prodId>";
			getChallenge += "</parameters>";
			getChallenge += "</request>";
			System.out.println(System.currentTimeMillis()+"请求：\n"+getChallenge);
			response =StringUtils.request(urlBuf, getChallenge);
			System.out.println(System.currentTimeMillis()+"响应：\n"+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
