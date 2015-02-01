package com.ever.smit.test;

import com.ever.smit.util.StringUtils;

public class RechargeTest {


        /**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String urlBuf = "http://127.0.0.1:8080/smcp/ali/smit/service";
			String response = null;
			String getChallenge = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			getChallenge += "<request method=\"recharge\">";
			getChallenge += "<parameters>";
			getChallenge += "<token>"+"token"+"</token>";
			getChallenge += "<wctToken>"+"wctToken"+"</wctToken>";
			getChallenge += "<voucherCode>"+"voucherCode"+"</voucherCode>";
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
