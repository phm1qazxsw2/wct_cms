package util;

import org.json.*;
import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;

public class XmlJson {
	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param xml
	 *            xml格式的字符串
	 * @return 成功返回json 格式的字符串;失败反回null
	 */
	@SuppressWarnings("unchecked")
	public String Xml2Json(String xml) 
		throws Exception
	{
		JSONObject obj = Xml2Jobj(xml);
		return obj.toString();
	}
	
	public JSONObject Xml2Jobj(String xml) 
		throws Exception
	{
		JSONObject obj = new JSONObject();
		InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
		SAXBuilder sb = new SAXBuilder(); 
		Document doc = sb.build(is);
		Element root = doc.getRootElement();
		obj.put(root.getName(), iterateElement(root));
		return obj;
	}

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param file
	 *            java.io.File实例是一个有效的xml文件
	 * @return 成功反回json 格式的字符串;失败反回null
	 */
	@SuppressWarnings("unchecked")
	public String Xml2Json(File file) 
		throws Exception
	{
		JSONObject obj = new JSONObject();
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(file);
		Element root = doc.getRootElement();
		obj.put(root.getName(), iterateElement(root));
		return obj.toString();
	}

	/**
	 * 一个迭代方法
	 * 
	 * @param element:
	 *            org.jdom.Element
	 * @return java.util.Map 实例
	 */
	@SuppressWarnings("unchecked")
	Map iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(et.getTextTrim());
				obj.put(et.getName(), list);
			}
		}
		return obj;
	}

	// 测试
	public static void main(String[] args) {
		try {
			System.out.println(new XmlJson().Xml2Json("<MapSet>"
					+ "<MapGroup id='Sheboygan'>" + "<Map>"
					+ "<Type>MapGuideddddddd</Type>"
					+ "<SingleTile>true</SingleTile>" + "<Extension>"
					+ "<ResourceId>ddd</ResourceId>" + "</Extension>" + "</Map>"
					+ "<Map>" + "<Type>ccc</Type>" + "<SingleTile>ggg</SingleTile>"
					+ "<Extension>" + "<ResourceId>aaa</ResourceId>"
					+ "</Extension>" + "</Map>" + "<Extension />" + "</MapGroup>"
					+ "<ddd>" + "33333333" + "</ddd>" + "<ddd>" + "444" + "</ddd>"
					+ "</MapSet>"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
