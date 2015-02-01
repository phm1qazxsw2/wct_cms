package dbo;

public class Security
{
	public static String checkSingleQuote(String word)
		throws Exception
	{
		String tmp = word.replace("''", ""); // it's ok to have '', but not ' for database
		if (tmp.indexOf("'")>=0)
			throw new Exception("Invalid input:" + word);
		return word;
	}
	
	public static String checkParenthesis(String str)
		throws Exception
	{
		if (str.indexOf(")")>=0)
			throw new Exception("Invalid input:" + str);
		return str;
	}
}
