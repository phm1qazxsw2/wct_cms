<%@ page language="java" import="java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%!
	int getRow(String data, int idx, StringBuffer row)
		throws Exception
	{
		int in_quote = 0;
		while (idx<data.length()) {
			char c = data.charAt(idx);
			idx ++;
			if (c=='\n' && in_quote==0)	{
				break;
			}
			if (c=='"') {
				in_quote = (in_quote+1)%2;
			}
			row.append(c);
		}		
		return idx;
	}
	
	int getToken(String line, int idx, StringBuffer token)
		throws Exception
	{
		int in_quote = 0;
		while (idx<line.length()) {
			char c = line.charAt(idx);
			idx ++;
			if (c=='\t')
				break;
			token.append(c);
		}		
		return idx;
	}	
%>
<%
	request.setCharacterEncoding("UTF-8");
	String data = request.getParameter("data");
	if (data!=null) {
		int i =0; 
		while (true) {
			StringBuffer row = new StringBuffer();
			i = getRow(data, i,  row);
			if (row.length()==0)
				break;
			String line = row.toString();
			int j = 0;
			while (j<line.length()) {
				StringBuffer token = new StringBuffer();
				j = getToken(line, j, token);
				out.print(token + "#");
			}
			out.println("<br/>");
		}
	}
%>
<form method="POST">
<textarea name="data" rows="20" columns="100"></textarea>
<br/>
<input type="submit" />
</form>