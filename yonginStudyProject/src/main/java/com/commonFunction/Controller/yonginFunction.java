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
}
