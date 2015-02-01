package util;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import com.postmark.java.*;


public class EmailTool {
    String mailServer = "localhost";
    boolean debug;

    public EmailTool(String mailServer, boolean debug) {
    	System.out.println("## 1");
        this.mailServer = mailServer;
        this.debug = debug;
        System.out.println("## 2");
    }

    public void send(String to, String cc, String bcc, String from,
                     String subject, String content, boolean html, String messageEncoding)
            throws Exception {
        send(to, cc, bcc, from, null, subject, content, html, messageEncoding, null);
    }

    class AsynThread extends Thread 
    {
    	String to=null, cc=null, bcc=null, from=null, fromName=null;
        String subject=null, content=null, messageEncoding=null;
    	boolean html = false;
        File[] attachments = null;
    	
    	AsynThread(String to, String cc, String bcc, String from, String fromName,
                String subject, String content, boolean html, String messageEncoding, 
                File[] attachments) 
        {
    		this.to = to;
    		this.cc = cc;
    		this.bcc = bcc;
    		this.from = from;
    		this.fromName = fromName;
    		this.subject = subject;
    		this.content = content;
    		this.html = html;
    		this.messageEncoding = messageEncoding;
    		this.attachments = attachments;
    	}
    	
    	public void run() {
    		Tracer t = new Tracer();
    		try {
    			System.out.println("emailthread #1 " + t.printTimeDiff());
    			//send(this.to, this.cc, this.bcc, this.from, this.fromName, this.subject,
       			//	 this.content, this.html, this.messageEncoding, this.attachments);
    			
    			if (this.to.indexOf("@sohu.com")>=0) {
        			send(this.to, this.cc, this.bcc, this.from, this.fromName, this.subject,
        				 this.content, this.html, this.messageEncoding, this.attachments);
    			}
    			else {
    			
	    	        List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	    	        //headers.add(new NameValuePair("HEADER", "test"));
	
	    	        PostmarkMessage message = new PostmarkMessage(
	    	        		this.from,
	    	        		this.to,
	    	        		this.from,
	    	        		this.cc,
	    	        		this.subject,
	    	        		this.content,
	    	        		this.html,
	    	                "",
	    	                headers);
	
	    	        //String apiKey = "88ca8cd0-440f-4b16-9ebe-c446c96a5918"; // qooye
	    	        //String apiKey = "7ae79cea-c20c-4a64-8142-70318b11c7c7"; // yvent
	    	        String apiKey = "de2c9b12-73b1-491c-93c4-472cda76af24"; // yunxiche
	    	            
	    	        PostmarkClient client = new PostmarkClient(apiKey);
	
	    	        try {
	    	            client.sendMessage(message);
	    	        } catch (PostmarkException pe) {
	    	            System.out.println("An error has occured : " + pe.getMessage());
	    	        }    			
    			}
    			
    			System.out.println("emailthread #2 " + t.printTimeDiff());
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    }	
    
    
    class SendgridThread extends Thread 
    {
    	String to=null, cc=null, bcc=null, from=null, fromName=null;
        String subject=null, content=null, messageEncoding=null;
    	boolean html = false;
        File[] attachments = null;
    	
        SendgridThread(String to, String cc, String bcc, String from, String fromName,
                String subject, String content, boolean html, String messageEncoding, 
                File[] attachments) 
        {
    		this.to = to;
    		this.cc = cc;
    		this.bcc = bcc;
    		this.from = from;
    		this.fromName = fromName;
    		this.subject = subject;
    		this.content = content;
    		this.html = html;
    		this.messageEncoding = messageEncoding;
    		this.attachments = attachments;
    	}
    	
    	public void run() {
    		Tracer t = new Tracer();
    		try {
    			Properties props = new Properties();
    	        props.put("mail.transport.protocol", "smtp");
    	        props.put("mail.smtp.host", "smtp.sendgrid.net");
    	        props.put("mail.smtp.port", 587);
    	        props.put("mail.smtp.auth", "true");
    	        	
    	        Authenticator auth = new SMTPAuthenticator();
    	        Session mailSession = Session.getInstance(props, auth);
    	        // uncomment for debugging infos to stdout
    	        // mailSession.setDebug(true);
    	        Transport transport = mailSession.getTransport();

    	        MimeMessage message = new MimeMessage(mailSession);

    	        Multipart multipart = new MimeMultipart("alternative");

    	        //BodyPart part1 = new MimeBodyPart();
    	        //part1.setText("This is multipart mail and u read part1......");

    	        BodyPart part2 = new MimeBodyPart();
    	        String type = "text/plain;charset=UTF-8";
    	        if (this.html)
    	        	type = "text/html;charset=UTF-8";
    	        part2.setContent(this.content, type);

    	        //multipart.addBodyPart(part1);
    	        multipart.addBodyPart(part2);

    	        message.setContent(multipart);
    	        message.setFrom(new InternetAddress(this.from));
    	        message.setSubject(this.subject);
    	        message.addRecipient(Message.RecipientType.TO,
    	             new InternetAddress(this.to));

    	        transport.connect();
    	        transport.sendMessage(message,
    	            message.getRecipients(Message.RecipientType.TO));
    	        transport.close();
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    }
    
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "nicepeter";
           String password = "xo3i4ne3";
           return new PasswordAuthentication(username, password);
        }
    }
    
    
    public void send_async(String to, String cc, String bcc, String from, String fromName,
            String subject, String content, boolean html, String messageEncoding, File[] attachments)
    	throws Exception
	{
    	System.out.println("## in send async");
    	new AsynThread( to,  cc,  bcc,  from,  fromName,
                 subject,  content,  html,  messageEncoding, 
                 attachments).start();
	}
    
    public void send_sendgrid(String to, String cc, String bcc, String from, String fromName,
            String subject, String content, boolean html, String messageEncoding, File[] attachments)
    	throws Exception
	{
    	System.out.println("## in send sendgrid");
    	new SendgridThread( to,  cc,  bcc,  from,  fromName,
                subject,  content,  html,  messageEncoding, 
                attachments).start();
	}    

    public void send(String to, String cc, String bcc, String from, String fromName,
                     String subject, String content, boolean html, String messageEncoding, File[] attachments)
            throws Exception {
        InternetAddress[] address = null;
        InternetAddress[] cc_address = null;
        InternetAddress[] bcc_address = null;

        boolean sessionDebug = debug;

        java.util.Properties props = System.getProperties();
System.out.println("### sending email, mailServer=" + mailServer + " to=" + to);
        props.put("mail.host", mailServer);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", sessionDebug + "");
        props.put("mail.smtp.host", mailServer);

        Session mailSession = Session.getInstance(props, null);
        mailSession.setDebug(sessionDebug);

        Message msg = new MimeMessage(mailSession);

        InternetAddress fromaddr = null;
        if (from == null)
            fromaddr = new InternetAddress(from);
        else
            fromaddr = new InternetAddress(from, fromName, messageEncoding);

        msg.setFrom(fromaddr);

        address = InternetAddress.parse(to, false);
        msg.setRecipients(Message.RecipientType.TO, address);
        //msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver_mail));

        if (cc != null) {
            cc_address = InternetAddress.parse(cc, false);
            msg.setRecipients(Message.RecipientType.CC, cc_address);
        }

        if (bcc != null) {
            bcc_address = InternetAddress.parse(bcc, false);
            msg.setRecipients(Message.RecipientType.BCC, bcc_address);
        }

        String enSubject =
                javax.mail.internet.MimeUtility.encodeText(subject, messageEncoding, null);

        msg.setSubject(enSubject);

        msg.setSentDate(new Date());

       
        MimeMultipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();

        if (messageEncoding == null)
            messageEncoding = "latin1";

        if (html)
            mbp.setContent(content,"text/html;charset="+messageEncoding);
        else
            mbp.setText(content, messageEncoding);

        //mbp.setDataHandler(
        //    new DataHandler(new HtmlDataSource(content, "text/html", messageEncoding)));


        mp.addBodyPart(mbp);

        for (int i=0; attachments!=null && i<attachments.length; i++)
        {
            MimeBodyPart mbody = new MimeBodyPart();
            DataSource source = new FileDataSource(attachments[i]);
            mbody.setDataHandler(new DataHandler(source));
            mbody.setFileName(attachments[i].getName());
            mp.addBodyPart(mbody);
        }

        msg.setContent(mp);

        Transport.send(msg);
    }

    public void send(String to, String sender_mail, String subject,
                     String content, boolean html, String messageEncoding)
            throws Exception {
        send(to, null, null, sender_mail, subject,
                content, html, messageEncoding);
    }

    public static void main(String[] args) {
        try {
System.out.println("arg0=" + args[0]);        	
            EmailTool e = new EmailTool(args[0], true);
            e.send("nicepeter@gmail.com", null, null, "peter@poweracademycn.com", "林君豪", "測試郵件寄送", 
                    "今天天氣很好,要快樂哦!", false, "UTF-8", null);

            File[] attachments = new File[1];
            attachments[0] = new File("build.xml");    
           // attachments[1] = new File("testdata/demo.pdf");            
            // e.send("pjhlin@hotmail.com", null, null, "nicepeter@gmail.com", "from Peter", "report test", 
            //        "今天天氣很好,要快樂哦!", false, "UTF-8", attachments);
            // e.send("peter@poweracademycn.com", null, null, "peter@poweracademycn.com", "from Peter", "report test", 
            //    "<html><h1>今天天氣很好,要快樂哦!</h1></html>", true, "UTF-8", attachments);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class HtmlDataSource implements javax.activation.DataSource {
    String mimeType;
    String content;
    String encoding;

    public HtmlDataSource(String content, String mimeType, String encoding) {
        this.mimeType = mimeType;
        this.content = content;
        this.encoding = encoding;
    }

    public String getContentType() {
        return mimeType;
    }

    public InputStream getInputStream()
            throws java.io.UnsupportedEncodingException {
        return new ByteArrayInputStream(content.getBytes(encoding));
    }

    public String getName() {
        return "HtmlDataSource";
    }

    public OutputStream getOutputStream() {
        return null;
    }
}