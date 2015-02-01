package com.ever.smit.test;

import com.ever.smit.util.StringUtils;

public class RegisterTest {

    /**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String urlBuf = "http://112.124.44.254:8080/smcp/ali/smit/service";
			String response = null;
			String getChallenge = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			getChallenge += "<request method=\"register\">";
			getChallenge += "<parameters>";
			getChallenge += "<mobile>13520297961</mobile>";
			getChallenge += "<validateCode>488467</validateCode>";
			getChallenge += "<token>348e003ebb40d43fabe6dc86f8230126</token>";
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
