<%@ page language="java" import="com.postmark.java.*,java.util.*,java.io.*,org.apache.commons.codec.binary.Base64"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	PostmarkMessage message = new PostmarkMessage("service@phm.com.tw",
	        "nicepeter@gmail.com;henry.kung@phm.com.tw",
	        "service@phm.com.tw",
	        null,
	        "This is a test",
	        "test content test content test content test content ",
	        false,
	        null,
	        null); 
	
	PostmarkClient client = new PostmarkClient("a589c6ca-b8ed-4579-b299-9a5c26c07648");
	
	Attachment attachment = new Attachment(); 
	attachment.setContentType("image/png");
	attachment.setName("foot.png");

	// convert file contents to base64
	File file = new File(request.getRealPath("/foot.png"));
	byte[] ba = new byte[(int) file.length()];
	FileInputStream fis = new FileInputStream(file);
	fis.read(ba);
	fis.close();
	attachment.setContent(new Base64().encodeToString(ba));

	// can handle multiple attachments
	Vector<Attachment> v = new Vector<Attachment>();
	v.add(attachment);
	message.setAttachments(v);
	
	try {
	       client.sendMessage(message);
	       out.println("Done!");
	} catch (PostmarkException pe) {
		   out.println("Error!");
	       pe.printStackTrace();
	}	
%>