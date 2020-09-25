package com.commonFunction.Controller;

public class yonginFunction {
	
	/**
	 * �ش� ���ڿ��� ����ִ��� Ȯ��
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * �ش� ���ڿ����� ������ ���ڸ� ���� �� ��ȯ
	 * @param str		���� ���ڿ�
	 * @param remove	������ ����(' , - , : ��) 
	 * @return
	 */
	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}
	
	/**
	 * 8���ڷ� �� 'YYYYMMDD' ���ڿ��� 'YYYY-MM-DD'�� ��ȯ�Ѵ�.
	 * @param date
	 * @return
	 */
	public static String addMinusChar(String date) {
		if (date.length() == 8) {
			return date.substring(0, 4).concat("-").concat(date.substring(4, 6)).concat("-").concat(date.substring(6, 8));
		} else {
			return "";
		}
	}
	
	/**
	 * 4���ڷ� �� 'HHMM' ���ڿ��� 'HH:MM'���� ��ȯ�Ѵ�.
	 * @param time
	 * @return
	 */
	public static String addColonChar(String time) {
		if (time != null) {
			return time.substring(0, 2).concat(":").concat(time.substring(2,4));
		} else {
			return "";
		}
	}
	
	/** 
	 * �� �� Ȯ��
	 * @param src
	 * @return
	 */
	public static String nullConvert(String src) {

		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return "";
		} else {
			return src.trim();
		}
	}
}
