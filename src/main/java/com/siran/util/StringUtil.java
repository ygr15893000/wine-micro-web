package com.siran.util;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {
	static final DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * 判断字符串是否为空，为空(包括null和空字符串)返回true不为空返回false
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 将字符串转换为int类型的数字，字符串为null、空字符串、不能转换为int类型的字符串，返回1，否则返回相应的数字
	 *
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		if (str != null) {
			str = str.replace("0", "").replace(".", "").length() == 0 ? "0" : str;

			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				System.out.println("here");
				return 1;
			}
		}
		return 1;

	}

	public static int parseIntFormat(String str) {
		if (StringUtils.isNotBlank(str)) {

			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;

	}

	/**
	 * 将字符串转换为int类型的数字，字符串为null、空字符串、不能转换为int类型的字符串，返回1，否则返回相应的数字
	 *
	 * @param str
	 * @return
	 */
	public static float parseFloat(String str) {
		if (str != null) {
			str = str.replace("0", "").replace(".", "").length() == 0 ? "0" : str;

			try {
				return Float.parseFloat(str);
			} catch (Exception e) {
				System.out.println("here");
				return 1;
			}
		}
		return 1;

	}

	/**
	 * 替换字符串
	 *
	 * @param str
	 * @return
	 */
	public static String replaceString(String str) {
		if (str != null) {
			try {
				return str.replaceAll("\\\\", "/");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 截取内容添加"..."
	 *
	 * @param content 内容
	 * @param length  长度
	 * @return
	 * @author zhanglv
	 */
	public static String subContent(String content, int length) {
		content = content == null ? "" : content.trim();
		length = length > content.length() ? content.length() : length;
		if (content.length() != 0 && content.length() > length) {
			content = content.substring(0, length) + "...";
		}
		return content;
	}

	/**
	 * 截取内容添加"..."
	 *
	 * @param content 内容
	 * @return
	 * @author zhanglv
	 */
	public static String subContent(String content) {

		int length = content.length() > 4 ? 4 : 1;
		content = content == null ? "" : content.trim();
		//length = length > content.length() ? content.length() : length;
		if (content.length() != 0 && content.length() > length) {
			content = content.substring(0, length) + "***";
		}

		return content;
	}

	public static String formatCN(String inputMoney) {


		return strRotate(injectUnit(replaceCN(strRotate(inputMoney)))).replace(".", "");
	}

	public static String injectUnit(String source) {
		List<String> unit = new ArrayList<String>();
		unit.add("分");
		unit.add("角");
		unit.add("元");
		unit.add("拾");
		unit.add("佰");
		unit.add("仟");
		unit.add("万");
		unit.add("拾");
		unit.add("佰");
		unit.add("仟");
		unit.add("亿");
		unit.add("拾");

		String result = "";

		if (source.contains(".")) {
			source = source.replace(".", "");
		} else {
			unit = unit.subList(2, unit.size());
		}

		for (int i = 0; i < source.length(); i++) {

			result += unit.get(i) + source.charAt(i);

		}

		return result;
	}

	public static String replaceCN(String source) {

		source = source.replace("0", "零");
		source = source.replace("1", "壹");
		source = source.replace("2", "贰");
		source = source.replace("3", "叁");
		source = source.replace("4", "肆");
		source = source.replace("5", "伍");
		source = source.replace("6", "陆");
		source = source.replace("7", "柒");
		source = source.replace("8", "捌");
		source = source.replace("9", "玖");

		return source;
	}


	public static String strRotate(String source) {
		String result = "";

		for (int i = source.length() - 1; i >= 0; i--) {
			result += source.charAt(i) + "";
		}

		return result;
	}

	public static int getLengthFromAToB(String a, String b, String source) {


		String subStr = source.substring(source.indexOf(a), source.indexOf(b));

		return subStr.length();
	}


	public static Integer ceil(String sourceStr, String divisorStr) {

		Integer source = Integer.parseInt(sourceStr);

		Integer divisor = Integer.parseInt(divisorStr);

		Integer result = (int) Math.ceil(((double) source / divisor));

		System.out.println(result);

		return result;

	}

	/**
	 * 截取List(Map)中指定的内容字段添加"..."
	 *
	 * @param fieldName 字段名
	 * @param list      目标List(Map)
	 * @return
	 * @author zhanglv
	 */
	public static List<Map<String, String>> subContent(
			List<Map<String, String>> list, String fieldName, int length) {
		for (Map<String, String> m : list) {
			m.put(fieldName,
					subContent(
							m.get(fieldName) == null ? "" : m.get(fieldName),
							length));
		}
		return list;
	}

	/**
	 * 获取一副图片
	 *
	 * @param str
	 * @return
	 */
	public static String getOneImage(String str) {
		if (str != null && !str.trim().equals("")) {
			String strs[] = str.split(",");
			for (String s : strs) {
				if (!s.trim().equals("")) {
					return s;
				}
			}
		}
		return null;
	}

	/**
	 * 获取一副图片
	 *
	 * @return
	 */
	public static List getOneImage(List<Map> list, String fieldName) {
		for (Map<String, String> map : list) {
			map.put(fieldName, getOneImage(map.get(fieldName)));
		}
		return list;
	}

	/**
	 * 缩减地址
	 *
	 * @param str
	 * @return
	 */
	public static String shortLocation(String str) {
		if (str == null) {
			return "未知地点";
		}
		str = str.replaceAll("中国.*省", "");
		if (str.lastIndexOf("街") > 0) {
			str = str.substring(0, str.lastIndexOf("街") + 1);
		} else if (str.lastIndexOf("路") > 0) {
			str = str.substring(0, str.lastIndexOf("路") + 1);
		} else if (str.lastIndexOf("巷") > 0) {
			str = str.substring(0, str.lastIndexOf("巷") + 1);
		} else if (str.lastIndexOf("邮政编码") > 0) {
			str = str.substring(0, str.lastIndexOf("邮政编码") + 4);
		}
		return str;
	}


	/**
	 * 缩减地址
	 *
	 * @param list
	 * @return
	 */
	public static List shortLocation(List<Map> list, String fieldName) {
		for (Map<String, String> map : list) {
			map.put(fieldName, shortLocation(map.get(fieldName)));
		}
		return list;
	}

	/*
	 * 将十一位的电话号码18725069460转换为这样的格式1872****460
	 */
	private static String formatNumber(String terminal) {
		return terminal.substring(0, 4) + "****" + terminal.substring(8, 11);
	}

	public static boolean containsFilterWord(String str) {
		return false;
		/*
		 * String[] filterWord = new
		 * String[]{"1001","10010","114","116114","118"
		 * ,"17901","17909","190300"}; for(String s:filterWord){
		 * if(str.contains(s)){ System.out.println(str+"包含有"+s); return true; }
		 * } return false;
		 */
	}

	/**
	 * 过滤标签
	 *
	 * @param content 内容
	 * @return
	 */
	public static String contentFilterTag(String content) {
		String str = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		return str.trim();
	}

	public static String cutHtml(String content, int size) {
		String ret = "";
		if (StringUtils.isBlank(content)) {
			return "";
		}
		ret = content.replaceAll("</?[^>]+>", "");
		ret = StringUtils.left(ret, 2 * size);
		ret = ret.replaceAll("&nbsp;", "");
		ret = ret.replaceAll("　", "");
		// ret = StringEscapeUtils.unescapeHtml(ret);
		ret = StringUtils.left(ret, size);
		// ret = StringEscapeUtils.escapeHtml(ret);
		return ret;
	}

	/*
	 * 将字符串（sfddf，sfd）形式转换为字符串数组（replaceAll("，",",")将中文下的，替换为英文下的，）
	 */
	public static String[] split(String input) {
		return input.replaceAll("，", ",").split(",");
	}

	/*
	 * 将字符串12-45格式的形式转换为字符串￥12-￥45格式
	 */
	public static String replace(String input) {
		String sales[] = input.split("-");
		String result = "";
		for (String tmp : sales) {
			result += "￥" + tmp + "-";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}


	private static String getRate(double amount, double yearRate) {
		String result;
		Double mount = amount * yearRate / 100 / 12;
		result = String.valueOf(df.format(mount));
		return result;
	}

	/*
	 * 将BigDecimal类型的数字，转换为人民币
	 */
	public static String returnMoneyFormat(BigDecimal moneyBigDecimal) {
		String money;
		if (moneyBigDecimal != null) {
			DecimalFormat dfmt = new DecimalFormat("#,##0.00");
			money = "￥" + dfmt.format(moneyBigDecimal);
			return money;
		} else {
			money = "￥0.00";
			return money;
		}
	}

	/*
	 * 将int类型的数字，转换为人民币格式
	 */
	public static String returnMoneyFormat(Integer moneyInteger) {
		String money;
		if (moneyInteger != null) {
			DecimalFormat dfmt = new DecimalFormat("#,##0.00");
			money = "￥" + dfmt.format(moneyInteger);
			return money;
		} else {
			money = "￥0.00";
			return money;
		}
	}

	/*
	 * 将字符串格式的数据，转换为相应的人民币格式显示
	 */
	public static String returnMoneyFormat(String moneyString) {
		String money;
		if (moneyString != null && moneyString.trim().length() > 0) {
			Double doubleMoney = Double.parseDouble(moneyString);
			DecimalFormat dfmt = new DecimalFormat("#,##0.00");
			money = "￥" + dfmt.format(doubleMoney);
			return money;
		} else {
			money = "￥0.00";
			return money;
		}

	}

	public static String returnMoney(String moneyString) {
		String money;
		if (moneyString != null && moneyString.trim().length() > 0) {
			Double doubleMoney = Double.parseDouble(moneyString);
			DecimalFormat dfmt = new DecimalFormat("#,##0.00");
			money = dfmt.format(doubleMoney);
			return money;
		} else {
			money = "0.00";
			return money;
		}

	}

	/*
	 * 转换名字格式为（李***）形式
	 */
	public static String returnNameFormat(String name) {
		String ret = "";
		if (name != null && name.trim().length() > 1) {
			ret = name.substring(0, 1) + "****";
		} else if (name != null) {
			ret = name;
		}
		return ret;
	}

	/*
	 * public static void main(String[] args) { String i = "";
	 * System.out.println(StringUtil.returnMoneyFormat(i)); }
	 */

	/*
	 * 转换日期格式
	 */
	public static String returnStringToTime(String str) {
		String result;
		if (str != null && !"".equals(str.trim())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				Date date = sdf.parse(str);
				result = sdf1.format(date);
				return result;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据当前日期输出文字
	 *
	 * @return
	 */
	public static String returnStringToTime() {
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		Calendar rightNow = Calendar.getInstance();
		int time = rightNow.get(Calendar.HOUR_OF_DAY);
		/*int time = date.getHours();*/
		try {
			// int time = date.getHours();
			// Scanner input = new Scanner(System.in);
			// int time = input.nextInt();
			if (time >= 0 && time <= 5) {
				sb.append("半夜了,注意身体早点休息哦！");
				return sb.toString();
			}
			if (time >= 6 && time < 12) {
				sb.append("早上好！");
				return sb.toString();
			}
			if (time == 12) {
				sb.append("中午好！");
				return sb.toString();
			}
			if (time >= 13 && time <= 18) {
				sb.append("下午好！");
				return sb.toString();
			}
			if (time >= 19 && time <= 21) {
				sb.append("晚上好！");
				return sb.toString();
			}
			if (time >= 22 && time <= 23) {
				sb.append("夜深了,注意身体哦！");
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String reverse(String s) {
		int length = s.length();
		String reverse = "";
		for (int i = 0; i < length; i++)
			reverse = s.charAt(i) + reverse;
		return reverse;
	}

	public static char[] returnStringToChar(String string) {
		if (string != null && string.trim() != "") {
			char[] rets = string.toCharArray();
			if (rets.length < 6) {
				for (int i = rets.length; i < 6; i++) {
					string = '0' + string;
				}
			}
			return reverse(string).toCharArray();
		}
		return "000000".toCharArray();
	}


	public String decode(String request) {

		try {
			return new String(request.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String cancerTail(String orgString) {
		if (orgString != null && !"".equals(orgString)) {
			return orgString.substring(0, orgString.lastIndexOf("."));
		}
		return "";
	}

	public static String AddToString(String stringA, String stringB) {
		Double a = 0.0;
		Double b = 0.0;
		if (stringA != null && !"".equals(stringA)) {
			a = Double.parseDouble(stringA);
		}
		if (stringB != null && !"".equals(stringB)) {
			b = Double.parseDouble(stringB);
		}
		double ret = a + b;
		return Double.toString(ret);
	}

	public static String addFourString(String stringA, String stringB,
									   String stringC, String stringD) {
		Double a = 0.0;
		Double b = 0.0;
		Double c = 0.0;
		Double d = 0.0;
		if (stringA != null && !"".equals(stringA)) {
			a = Double.parseDouble(stringA);
		}
		if (stringB != null && !"".equals(stringB)) {
			b = Double.parseDouble(stringB);
		}
		if (stringC != null && !"".equals(stringC)) {
			c = Double.parseDouble(stringC);
		}
		if (stringD != null && !"".equals(stringD)) {
			d = Double.parseDouble(stringD);
		}
		double ret = a + b + c + d;
		return Double.toString(ret);
	}

	public static String subTwoString(String stringA, String stringB) {
		Double a = 0.0;
		Double b = 0.0;

		if (stringA != null && !"".equals(stringA)) {
			a = Double.parseDouble(stringA);
		}
		if (stringB != null && !"".equals(stringB)) {
			b = Double.parseDouble(stringB);
		}

		double ret = a - b;
		System.out.println("+++++++减去茶资产+++++++++" + stringA + ":::" + stringB + "------" + Double.toString(ret));
		return Double.toString(ret);
	}

	/**
	 * 将电话号码转换为相应格式
	 *
	 * @param orgPhone
	 * @return
	 */
	public static String returnPhoneFormat(String orgPhone) {
		if (orgPhone != null && !"".equals(orgPhone.trim())) {
			if (orgPhone.length() == 11) {
				String retPhone = orgPhone.substring(0, 3) + "****" + orgPhone.substring(7, 11);
				return retPhone;
			} else {
				return orgPhone;
			}

		}
		return null;
	}

	public static String returnEmailFormat(String orgEmail) {
		if (orgEmail != null && !"".equals(orgEmail.trim())) {
			String retPhone = orgEmail.substring(0, 2) + "****" + orgEmail.substring(orgEmail.indexOf("@"));
			return retPhone;
		}
		return null;
	}

	/**
	 * 计算投标额
	 */
	public static String calculateInvestAmount(String singleInvest, String times) {
		if (singleInvest != null && times != null && !"".equals(singleInvest.trim()) && !"".equals(times.trim())) {
			double ret = Double.valueOf(singleInvest) * Double.valueOf(times);
			return String.valueOf(ret);
		}
		return null;
	}

	public static void main(String[] args) {
		//calculateInvestAmount("5000","3");
//		System.out.println(getListByStr("无量古韵壹号C包,5年不回C,1,无量古韵壹号C包,1年不回购C,1"));

//		System.out.println(parseInt("-1.00") + "====" + Float.parseFloat("0.00"));
//		System.out.println(getJsonValueByKey("TransAmt1", "{\"BgRetUrl\":\"http://www.yjs21.com/huifuBgRetServlet\",\"ChkValue\":\"982BB71E594A529B9DDDC8F31EE611DF45C9A52867F9912C780E85A3EE6840E30802575394F173C7C7B4CE93CA293C054876E0238C79F744FB02E8963E15757178143F0279B7FEADB1E315CA557A867BC212418C4119DF8A980452EDA423AF447B32886D85EAD3AC3CBC0B51B1928E69BD617E63971223035DEC8E93FB0607F8\",\"CmdId\":\"Cash\",\"FeeAcctId\":\"MDT000001\",\"FeeAmt\":\"2.00\",\"FeeCustId\":\"6000060045416503\",\"MerCustId\":\"6000060044399017\",\"MerPriv\":\"{\\\"requestId\\\":\\\"20150708114452052\\\",\\\"userId\\\":\\\"2291\\\"}\",\"OpenAcctId\":\"6212262517000363243\",\"OpenBankId\":\"ICBC\",\"OrdId\":\"20150708114452052\",\"RealTransAmt\":\"1748.00\",\"RespCode\":\"000\",\"RespDesc\":\"成功\",\"RespExt\":\"[{\\\"FeeObjFlag\\\":\\\"U\\\",\\\"CashChl\\\":\\\"GENERAL\\\"}]\",\"RetUrl\":\"http://www.yjs21.com/huifuRetServlet\",\"ServFee\":\"\",\"ServFeeAcctId\":\"MDT000001\",\"TransAmt\":\"1750.00\",\"UsrCustId\":\"6000060045416503\"}"));


		formatIdCard("532322198510130718");
		System.out.println(formatIdCard("532322198510130718"));
		System.out.println(formatIdCard("532322670401718"));
	}

	public static List<List<String>> getListByStr(String str) {

		List<List<String>> result = new ArrayList<List<String>>();
		String tmpStr[] = str.split(",");
		if (str.contains(",")) {

			List<String> tmpList = new ArrayList<String>();
			int i = 0;
			for (String tmp : tmpStr) {
				tmpList.add(tmp);
				if ((i + 1) % 6 == 0 && i != 0) {
					result.add(tmpList);
					tmpList = new ArrayList<String>();
				}
				i++;
			}

			return result;
		} else {
			return null;
		}


	}

	public static String formatcardNo(String cardNo) {
		return cardNo.substring(0, 4) + "************" + cardNo.substring(cardNo.length() - 4, cardNo.length());
	}

	/*
	 * 将http://www.yjs21.com//financeDetail.do字符串替换为project_detail.html
	 */
	public static String returnStrUrlFormat(String url) {
		String newUrl;
		if (url != null) {
			newUrl = url.replace("220.165.8.32", "www.yjs21.com");
			newUrl = newUrl.replace("financeDetail.do", "project_detail.html");
			return newUrl;
		} else {
			return "";
		}
	}



	/**
	 * 获取key参数中文名称
	 * 汇付相关专用
	 *
	 * @param key 参数中文名称
	 * @return
	 */
	public static String getValueByKey(String key) {
		if ("MerCustId".equalsIgnoreCase(key)) {
			return "商户客户号";
		} else if ("UsrCustId".equalsIgnoreCase(key)) {
			return "用户客户号";
		} else if ("OrdId".equalsIgnoreCase(key)) {
			return "订单号";

		} else if ("OrdDate".equalsIgnoreCase(key)) {
			return "订单日期";

		} else if ("GateBusiId".equalsIgnoreCase(key)) {
			return "支付网关业务代号";

		} else if ("OpenBankId".equalsIgnoreCase(key)) {
			return "开户银行代号";

		} else if ("TransAmt".equalsIgnoreCase(key)) {
			return "交易金额";

		} else if ("CardId".equalsIgnoreCase(key)) {
			return "银行卡号";

		} else if ("FeeAmt".equalsIgnoreCase(key)) {
			return "手续费金额";

		} else if ("TrxId".equalsIgnoreCase(key)) {
			return "交易唯一标识";

		} else if ("RespCode".equalsIgnoreCase(key)) {
			return "应答返回码";

		} else if ("RespDesc".equalsIgnoreCase(key)) {
			return "应答描述";

		} else if ("IsFreeze".equalsIgnoreCase(key)) {
			return "是否冻结";

		} else if ("FreezeOrdId".equalsIgnoreCase(key)) {
			return "冻结订单号";

		} else if ("FreezeTrxId".equalsIgnoreCase(key)) {
			return "冻结标识";

		} else if ("AuditFlag".equalsIgnoreCase(key)) {
			return "复核标识";

		} else if ("ProId".equalsIgnoreCase(key)) {
			return "标的ID";

		} else if ("BorrCustId".equalsIgnoreCase(key)) {
			return "借款人 ID";

		}

		return key;

	}


	public static String formatIdCard(String idCard) {
		if (StringUtils.isBlank(idCard)) return "****";
		if (idCard.length() == 15) {
			return idCard.substring(0, 3) + "************";
		} else {
			return idCard.substring(0, 3) + "************" + idCard.substring(15);
		}
	}

	public static String formatUsername(String UserName) {
		if (UserName.length() <= 3) {
			return UserName.substring(0, 1) + "***";
		} else {
			return UserName.substring(0, 3) + "***";
		}
	}

	public static int length(String obj) {
		int leng = obj.length();
		return leng;
	}

	public static String goToString(String object) {
		String newView;
		if (object != null) {
			newView = String.valueOf(object);
			return newView;
		} else {
			return "";
		}
	}

	public static String getValue2(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			value = new String(value.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static int substr(String str, int num) {
		int str1 = Integer.parseInt(str);
		int result = str1 * num;
		return result;
	}
	public static String change(String s) {
	 final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
	 final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
	 final double MAX_VALUE = 9999999999999.99D;
		double v = Double.parseDouble(s);
		if (v < 0 || v > MAX_VALUE){
			return "参数非法!";
		}
		long l = Math.round(v * 100);
		if (l == 0){
			return "零元整";
		}
		String strValue = l + "";
		// i用来控制数
		int i = 0;
		// j用来控制单位
		int j = UNIT.length() - strValue.length();
		String rs = "";
		boolean isZero = false;
		for (; i < strValue.length(); i++, j++) {
			char ch = strValue.charAt(i);
			if (ch == '0') {
				isZero = true;
				if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
					rs = rs + UNIT.charAt(j);
					isZero = false;
				}
			} else {
				if (isZero) {
					rs = rs + "零";
					isZero = false;
				}
				rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
			}
		}
		if (!rs.endsWith("分")) {
			rs = rs + "整";
		}
		rs = rs.replaceAll("亿万", "亿");
		return rs;
	}


}
