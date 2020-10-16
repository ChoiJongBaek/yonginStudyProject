package com.commonFunction.Controller;

import java.util.Calendar;

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
	
	/**
	 * �Է½ð��� ��ȿ ���θ� Ȯ��
	 * @param     sTime �Է½ð�
	 * @return    ��ȿ ����
	 */
	public static boolean validTime(String sTime) {
		String timeStr = validChkTime(sTime);

		Calendar cal;
		boolean ret = false;

		cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(timeStr.substring(2, 4)));

		String HH = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String MM = String.valueOf(cal.get(Calendar.MINUTE));

		String pad2Str = "00";

		String retHH = (pad2Str + HH).substring(HH.length());
		String retMM = (pad2Str + MM).substring(MM.length());

		String retTime = retHH + retMM;

		if (sTime.equals(retTime)) {
			ret = true;
		}

		return ret;
	}
	
	/**
	 * �Էµ� ���� ���ڿ��� Ȯ���ϰ� 8�ڸ��� ����
	 * @param sDate
	 * @return
	 */
	public static String validChkTime(String timeStr) {
		if (timeStr == null || !(timeStr.trim().length() == 4)) {
			throw new IllegalArgumentException("Invalid time format: " + timeStr);
		}
		
		if (timeStr.length() == 5) {
			timeStr = remove(timeStr, ':');
		}

		return timeStr;
	}
}
