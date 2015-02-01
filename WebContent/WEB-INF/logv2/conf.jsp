<%@ page language="java" import="java.io.*"
	pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%>
<%!
	static int counter = 0;
%>
<%
	File f = new File(request.getRealPath("/WEB-INF/logv2/config.txt"));
	counter ++;
	if (counter%100==0) {
		f = new File(request.getRealPath("/WEB-INF/logv2/config.timing.txt"));
		System.out.println("## sampleing " + (counter/100));
	}
	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	StringBuffer sb = new StringBuffer();
	char[] buf = new char[4096];
	int n = 0;
	while ((n=br.read(buf,0,4096))>0) {
		sb.append(buf, 0, n);
	}
	out.println(sb.toString());
%>