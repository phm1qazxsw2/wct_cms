package com.wct.util;

import java.util.*;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Logger extends HttpServlet implements ExceptionListener {
	private final static long serialVersionUID = 99999999L;
	private static Logger logger = null;
	boolean initializing = false;
	Object init_lock = new Object();
	Session session = null;
	Connection connection = null;
	Map<String, MessageProducer> producerMap = new HashMap<String, MessageProducer>();
	String mq_url = "";

	private void init_logger(String mq_url) throws Exception {
		this.mq_url = mq_url;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				mq_url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		connection.setExceptionListener(this);
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void log(String queue, String text) throws Exception {
		logger._log(queue, text);
	}

	public void _log(String queue, String text) throws Exception {
		if (session == null) {
//			System.out.print("#0");
			synchronized (init_lock) {
				System.out.print("#a=" + initializing);
				if (initializing) {
					System.out
							.println("## com.wct.util.Logger not started, message not sent");
					return;
				}
				initializing = true;
			}

			// make sure only 1 null session person gets here.
			try {
//				System.out.print("#b");
				init_logger(this.mq_url);
//				System.out.print("#1");
				session.getAcknowledgeMode();
//				System.out.print("#2");
			} catch (Exception e) {
				System.out.println("");
				return;
			} finally {
				initializing = false;
			}
//			System.out.print("#3");
		}

		long t1 = new Date().getTime();
//		System.out.print("#4");
		MessageProducer p = null;
		synchronized (producerMap) {
			p = producerMap.get(queue);
//			System.out.print("#5");
			if (p == null) {
//				System.out.print("#6");
				Destination destination = session.createQueue(queue); // wct_log
				p = session.createProducer(destination);
				p.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				producerMap.put(queue, p);
			}
		}

		try {
			TextMessage message = session.createTextMessage(text);
//			System.out.print("#7");
			p.send(message);
//			System.out.print("#8");
		} catch (Exception e) {
//			System.out.print("#9");
			if (e.getMessage().indexOf("closed") >= 0) {
				cleanUp();
//				System.out.print("#10");
			}
			return;
		}
//		System.out.println("## t=" + (new Date().getTime() - t1));
	}

	public void init(ServletConfig config) throws ServletException {
		try {
			String mq_url = config.getInitParameter("mq_url");
			if (mq_url != null && mq_url.length() > 0) {
				logger = this;
				this.init_logger(mq_url);
				System.out.println("#activemq client starts..");
			} else
				System.out
						.println("#activemq client not started : mq_url not found");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void cleanUp() {
		try {
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
			session = null;
			connection = null;
			producerMap = new HashMap<String, MessageProducer>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		logger.cleanUp();
		System.out.println("#activemq client stops..");
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("# in onException : " + ex.getMessage());
	}
}