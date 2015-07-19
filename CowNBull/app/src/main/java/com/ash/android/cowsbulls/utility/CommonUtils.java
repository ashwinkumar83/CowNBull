package com.ash.android.cowsbulls.utility;

public class CommonUtils {
	 public static boolean isAlpha(String name) {
	        char[] chars = name.toCharArray();

	        for (char c : chars) {
	            if(!Character.isLetter(c)) {
	                return false;
	            }
	        }

	        return true;
	    }
}
