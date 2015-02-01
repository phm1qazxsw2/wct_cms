package dbo;

import java.sql.*;

public class ServerTool
{
    public static boolean hasAnyResult(ResultSet rs)
    {
        try {
            if (!rs.isAfterLast() && !rs.isBeforeFirst())
                 return false;
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String escapeStringNoTrim(String s)
    {
        if (s==null)
            return "";

        // strip the double quotes
        if ((s.length()>2) && (s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'))
            s = s.substring(1, s.length()-1);

        StringBuffer sb = new StringBuffer();
        int p = 0;
        for (int i=0, len=s.length(); i<len; i++)
        {
            char c = s.charAt(i);  
            if (c=='(')
            	c = '（';
            if (c==')')
            	c = '）';
 
            if (c=='\'') {
                sb.append('\'');
            }
            else if (c=='\\') {
                sb.append('\\');
            }
            else if (c=='(') {
            	p ++;
            }
            else if (c==')') {
            	p --;
            }
            sb.append(c);
        }
        if (p!=0)
        	throw new RuntimeException("Security exception: unmatching () pair p=" + p);
        return sb.toString();
    }
    
    public static String escapeString(String s)
    {
        if (s==null)
            return "";

        // strip the double quotes
        if ((s.length()>2) && (s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'))
            s = s.substring(1, s.length()-1);

        StringBuffer sb = new StringBuffer();
        int p = 0;
        for (int i=0, len=s.length(); i<len; i++)
        {
            char c = s.charAt(i);  
            if (c=='(')
            	c = '（';
            if (c==')')
            	c = '）';
  
            if (c=='\'') {
                sb.append('\'');
            }
            else if (c=='\\') {
                sb.append('\\');
            }
            else if (c=='(') {
            	p ++;
            }
            else if (c==')') {
            	p --;
            }
            sb.append(c);
        }
        if (p!=0)
        	throw new RuntimeException("Security exception: unmatching () pair p=" + p);
        return sb.toString().trim();
    }
}