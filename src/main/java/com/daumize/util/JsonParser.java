package com.daumize.util;

import com.google.gson.Gson;

/**
 * 
 * @author mrsagar
 *
 */
public class JsonParser {

	/**
	 * this method is used for converting Object to JSON String
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}
}
