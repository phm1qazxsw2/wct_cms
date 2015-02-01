package com.ever.smit.test;

import com.ever.smit.util.StringUtils;


public class Test {


	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) {
		try {
			//String urlBuf = "http://127.0.0.1:8080/smcp/ali/smit/service";
			String urlBuf = "http://112.124.44.254:8080/smcp/ali/smit/service";
			String response = null;
			String getChallenge = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			getChallenge += "<request method=\"bind\">";
			getChallenge += "<parameters>";
			getChallenge += "<deviceId>"+"11"+"</deviceId>";
			getChallenge += "<deviceDRMId>SMITCODM0010203040500000000000000000000000000000000000000000</deviceDRMId>";
			getChallenge += "<appVersion>"+"11"+"</appVersion>";
			getChallenge += "<mac>"+"11"+"</mac>";
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
  class test{
	  private int id;
	  private String title;
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	  
  }
}

