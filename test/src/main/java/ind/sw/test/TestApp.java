package ind.sw.test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * Hello world!
 *
 */
public class TestApp {
	public static String ALIPAY_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCf0eQsU7fVD9buKKC5WGeWwv5O/4TeuFAwa36q40Zbuh/SWJL4xgHcpWRh5Avz5NNMBC9ExVUIcUg03SZSJ9vVw5/m3j5kP0Ko5V//WRpx9D2kPFshx3UQWa0rP7PNF42SkJ+usBV/+1pZhgRdAZXCW5Zq5lJtc/Tp/PoZwFGE+WiWuP5UFU+tFJjtHVJeQqK6wb/foyPI8nOIwc1M7iKJ3iTX5c5MgpIXdVtzNoY7oPLjgbvlWffJ0kf7j6qqfKN/CFFHx1XBjVZHC9pOihq4n/iCLCprcSp4PRWFHxD3G3e9ONYKXznncgkB0xtSCbRq0BrF2gAVU8xlt8qaI1I7AgMBAAECggEAHcDsstfvLIpjYDlOGtN7jZTF1Z06dmBoBWutzFC9o4wWKon06r2qURK1iofzNbfPIqLQzgPS3dyBLd5SKQ9knPprhYawpOcmGN2DLr2+76Ih67aU3MgOYjnfxGJ6rd8Jq1SjddkwV4BLBMmvfEU0RVfZvBMfG2NwIDPJuAuW4aNl4SehUY6ZD5KwI4T6FIaJibmAX8qOvFQ6TULGQPqt47vhZYuI9+1+obSdX5JJ2Re/1MpGdJs1f1YLIPaW6EwFIo+adh88h166YPbHqag36xxNiQo+57CdYhJvThp/k9IRmMzpd8qW7EtJ1BznY3gPc0RcIB84pq+qWF/lZpKgQQKBgQDOtt3DXX/a9yfbMGvH1zj31w42HXpPSu578/w28zFNWDlAmUKApAhnVIu4dlfdrCZWMNbtty5VOzjeUA/iWa6q9f5ybVKsc3dQr+fcySe5/SMdAGdR0xzLvorvVe93yLatWmz6xFQuixyQS4agvp83OdAgWs0e/T1EeTCp0THDoQKBgQDF7L/hiNRN/teTlILCieDbP0lTY2K//s2W/cN6cyXprz8bD7lNdaY8/Y1F0wSbGmMyqkWwQz5JJEsuu03upuUQFfVZikpOZhV7SMUY2ew83g+s1gQLb3znzn4l69aUM6rnPR+G0+aFWhA6jip/pUh6pU52lDBCblaBCoGCd33IWwKBgQCZcZTcWKN33s9vHVpkVZasIq8h7G/GPFEbIdmu0IdgaoTVNu7untS+Hzw4CWDf49HxWP+yJa/3BKcKH1QrKXdG4KOoPQ6JhIylEG6Era0XmpheBtarHpLZiu+HCbtgp1UzCVSsMOBsTEc/tVCSMe3uCLQ90ClDP6gjE/LTm73dAQKBgQC8ljm6fvKzomfsQaxaCqAKqAGJCAC2qeVgwki46vCr+gQqF9BbQwdzw7ARYOBZnUn5o44lRd0aOg+85lF6XOGTcyjE+WSV0OUVBCBw7mwPDxNmfsF51+/uu60tGsrpiVWFYZMhl9RlKYyfDojl+SnXWp0plUdqdNHNwKHoG0zpUwKBgQCxYFFohNgogE0xcufsIjvreIZKjwD5ZncCSfn7UmwyKnPI08tBjLIHVMnZSBwouwi2yveWpIAirLBgBFJya9tlcsLNSW4ZRvAr3H1GTNKVQPfUi2QJncQjJGbZ8zqGDe2r195z402Y7PUXbilYooI0qlG85CGM6146PMsK61wqXg==";
	public static String ALIPAY_PKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn9HkLFO31Q/W7iiguVhnlsL+Tv+E3rhQMGt+quNGW7of0liS+MYB3KVkYeQL8+TTTAQvRMVVCHFINN0mUifb1cOf5t4+ZD9CqOVf/1kacfQ9pDxbIcd1EFmtKz+zzReNkpCfrrAVf/taWYYEXQGVwluWauZSbXP06fz6GcBRhPlolrj+VBVPrRSY7R1SXkKiusG/36MjyPJziMHNTO4iid4k1+XOTIKSF3VbczaGO6Dy44G75Vn3ydJH+4+qqnyjfwhRR8dVwY1WRwvaTooauJ/4giwqa3EqeD0VhR8Q9xt3vTjWCl8553IJAdMbUgm0atAaxdoAFVPMZbfKmiNSOwIDAQAB";
	public static String ALIPAY_ALIBABA_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	public static String APPID = "2017052407329503";

	public static void testAS() throws Exception {
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID, ALIPAY_KEY, "json", "utf-8", ALIPAY_PKEY, "RSA");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo("");
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("商户外网可以访问的异步地址");
		AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		System.out.println(response.getBody());// 就是orderString 可以直接给客户端请求，无需再做处理。
	}

	public static void testContxt() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("app_id", "2017052407329503");
		params.put("auth_app_id", "2017052407329503");
		params.put("charset", "utf-8");
		params.put("code", "10000");
		params.put("msg", "Success");
		params.put("out_trade_no", "2017060921001004560210225626");
		params.put("seller_id", "2088122900368412");
		params.put("sign_type", "RSA");
		params.put("timestamp", "2017-06-09 16:31:11");
		params.put("total_amount", "0.01");
		params.put("trade_no", "2017060921001004560210225626");
		boolean flag = false;
		try {
//			String sign = "jW36Y98TMBejY+cIFf0Va/YfRetUtqBgwffxqbknJTudFutjHQ9pwJ0boW1WUH3yJzP+6x/zCk8/ZinZRMhfXBYdgQGDPLnH1+Lu4he770Mf4BinZEERXe5AXqG3KuXlbgiBI8nojNeC1DwMJN+4XoXMzUyqGUZhHZ+gCyLzVDc=";
			String sign = "OhwTZ8+meKJKEn94zQQBZ4AvkBRf375owKJ3nNkodGWU33tkvwQ7s64co8WXqfVfVX18Nw9AiDysVnL4kedD0rYsk4jY1bPtKnM6niALzxB7j1FqHFlEbTdFNHkYzPjV5XnmL/lXSDEoZHyTxszQkHAjfw/o0Eu186+XXuBe+JU=";
			String context = AlipaySignature.getSignCheckContentV1(params);
			System.out.println(context);
			flag = AlipaySignature.rsaCheckContent(context, sign, ALIPAY_ALIBABA_PUBLICKEY, "utf-8");
			System.out.println(flag);
//			context = "{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017052407329503\",\"auth_app_id\":\"2017052407329503\",\"charset\":\"utf-8\",\"timestamp\":\"2017-06-09 16:31:11\",\"total_amount\":\"0.01\",\"trade_no\":\"2017060921001004560210225626\",\"seller_id\":\"2088122900368412\",\"out_trade_no\":\"20821295109051460882\"}";
			context = "{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017052407329503\",\"auth_app_id\":\"2017052407329503\",\"charset\":\"utf-8\",\"timestamp\":\"2017-06-09 18:27:29\",\"total_amount\":\"0.01\",\"trade_no\":\"2017060921001004560210451213\",\"seller_id\":\"2088122900368412\",\"out_trade_no\":\"20828286744837087117\"}";
			flag = AlipaySignature.rsaCheckContent(context, sign, ALIPAY_ALIBABA_PUBLICKEY, "utf-8");
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testAliPaySign() throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID, ALIPAY_KEY, "json", "utf-8", ALIPAY_PKEY, "RSA");
		AlipayHashMap signParam = new AlipayHashMap();
		AlipayHashMap bizCtn = new AlipayHashMap();
		bizCtn.put("subject", "怒发冲冠");
		bizCtn.put("out_trade_no", "11202548149831043351");
		bizCtn.put("total_amount", 360.0d);
		bizCtn.put("product_code", "QUICK_MSECURITY_PAY");
		signParam.put("biz_content", new JSONWriter().write(bizCtn));
		signParam.put("app_id", APPID);
		signParam.put("method", "alipay.trade.app.pay");
		signParam.put("charset", "utf-8");
		signParam.put("timestamp", new Date());
		signParam.put("version", "1.0");
		String alipay_notify_url = "http://127.0.0.1:7070/app_api/vip/mem";
		signParam.put("notify_url", alipay_notify_url);
		signParam.put("sign_type", "RSA");

		System.out.println("oriinal: " + AlipaySignature.getSignContent(signParam));

		String sign = AlipaySignature.rsaSign(signParam, ALIPAY_KEY, "utf-8");
		System.out.println("sign: " + sign);
		signParam.put("sign", sign);
		for (Map.Entry<String, String> e : signParam.entrySet()) {
			signParam.put(e.getKey(), URLEncoder.encode(e.getValue(), "utf-8"));
		}

		String orderReady = AlipaySignature.getSignContent(signParam);
		System.out.println(orderReady);

		String orderFinal = URLEncoder.encode(orderReady, "utf-8");
		System.out.println("orderFinal: " + orderFinal);

		orderReady = URLDecoder.decode(orderFinal, "utf-8");
		System.out.println(orderReady);
		boolean b = AlipaySignature.rsaCheckV2(signParam, ALIPAY_PKEY, "utf-8");
		System.out.println(b);

	}

	private static String byte20() {
		String a = String.valueOf(System.nanoTime());
		String b = String.valueOf(System.nanoTime());
		StringBuilder sb = new StringBuilder(a);
		sb.append(b.substring(b.length() - (20 - a.length()), b.length()));
		return sb.toString();
	}

	public static String invokeWxpaysign() {
		AlipayHashMap wxParam = new AlipayHashMap();
		wxParam.put("appid", "1111011");
		wxParam.put("mch_id", "100000");
		wxParam.put("nonce_str", byte20());
		wxParam.put("body", "酒鬼酒");
		wxParam.put("out_trade_no", "102030292392");
		wxParam.put("total_fee", 2500);
		String wx_notify_url = "/vip/wxpay_notify_url";
		wxParam.put("notify_url", wx_notify_url);
		wxParam.put("spbill_create_ip", "222.222.222.222");
		wxParam.put("trade_type", "APP");
		wxParam.put("sign", wxpaySign(wxParam));

		String xml = null;

		xml = XmlUtil.map2xml(wxParam);

		System.out.println(xml);

		Map<String, String> tmp = XmlUtil.xml2map(xml);

		System.out.println(tmp);
		return xml;
	}

	public static String toHex(byte... bytes) {
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		BigInteger bi = new BigInteger(1, bytes);
		String hex = bi.toString(16);
		int paddingLength = (bytes.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	public static String wxpaySign(Map<String, String> param) {
		// param.put("nonce_str", byte20());
		String sign = null;
		String signReady = AlipaySignature.getSignContent(param);
		signReady = signReady + "&key=" + ALIPAY_PKEY;
		byte[] x = null;
		try {
			x = MessageDigest.getInstance("MD5").digest(signReady.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		sign = toHex(x).toUpperCase();
		return sign;
	}

	public static boolean chkecWxpaysign() {
		return false;
	}

	public static void main(String[] args) throws Exception {
		// String xml = invokeWxpaysign();
		// String x = XmlUtil.post("http://192.168.2.177:7070/app_api/vip/wxpay_notify_url", xml);
		// System.out.println("x="+x);
//		testContxt();
//		testAliPaySign();
		long l = 1529596799000l;
		Date d = new Date();
		d.setTime(l);;
		System.out.println(d);
	}

}
