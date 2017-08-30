package com.siran.util;

public class NumericUtil {

	public static boolean isNumeric(String num) {
		   try {   
		    Double.parseDouble(num);
		    return true;
		   } catch (NumberFormatException e) {
		    return false;
		   }
		}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
