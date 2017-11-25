package br.com.lucy.utils;

import com.google.gson.Gson;

import br.com.lucy.security.Security;

public class ServiceUtil {

	public static String toJson(Object to) {
		return Security.encrypt(new Gson().toJson(to));
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		return new Gson().fromJson(Security.decrypt(json), clazz);
	}

}
