package ind.sw.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	public static String map2xml(Map<String, String> map) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		Set<String> set = map.keySet();
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = map.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public static Map<String, String> xml2map(String xmlStr) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element el = doc.getRootElement();
			map = recGetXmlElementValue(el, map);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings({ "unchecked" })
	private static Map<String, String> recGetXmlElementValue(Element ele, Map<String, String> map) {
		List<Element> eleList = ele.elements();
		if (eleList.size() == 0) {
			map.put(ele.getName(), ele.getTextTrim());
			return map;
		} else {
			for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
				Element innerEle = iter.next();
				recGetXmlElementValue(innerEle, map);
			}
			return map;
		}
	}

	public static String post(String adr, String xml) throws Exception {
		StringBuilder rt = new StringBuilder();
		try {
			URL url = new URL(adr);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(xml.getBytes("utf-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				rt.append(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rt.toString();
	}

	public static void main(String[] args) {

	}
}