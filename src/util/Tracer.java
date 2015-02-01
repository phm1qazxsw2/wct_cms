package util;

import java.text.*;
import java.util.*;

public class Tracer

{
	long t1 = 0;
	long t0 = 0;
	static int thread_num = 0;
	static Integer lock = new Integer(1);

	StringBuffer sb = new StringBuffer();
	static java.text.SimpleDateFormat sdf = null;
	public Tracer() {
		synchronized (lock) {
			thread_num ++;
		}
		if (sdf==null)
			sdf = new java.text.SimpleDateFormat("HH:mm:ss");
		t0 = new Date().getTime();
		t1 = t0;
		sb.append("#[").append(sdf.format(new Date())).append("] ");
	}

	public long getDiff() {
		long t2 = new Date().getTime();
		long ret = t2 - t1;
		t1 = t2;
		return ret;
	}

	public void checkpoint(String a) {
		sb.append(a).append("(").append(getDiff()).append(") ");
	}

	public String getTrace() {
		sb.append(" total(").append(new Date().getTime() - t0).append(") threads(").append(thread_num).append(")");
		synchronized (lock) {
			thread_num --;
		}
		return sb.toString();
	}
	
	public long printTimeDiff() {
		long t2 = new Date().getTime();
		long ret = t2 - t1;
		t1 = t2;
		return ret;
	}
}

