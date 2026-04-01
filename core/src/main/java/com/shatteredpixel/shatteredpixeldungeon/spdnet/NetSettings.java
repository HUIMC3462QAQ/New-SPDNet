package com.shatteredpixel.shatteredpixeldungeon.spdnet;

import static com.watabou.utils.GameSettings.getString;
import static com.watabou.utils.GameSettings.put;

/**
 * 用来存储某些长期保存的变量
 */
public class NetSettings {
	public static final String KEY_AUTH_NAME = "net_auth_name";
	public static final String KEY_AUTH_PASSWORD = "net_auth_password";
	public static final String KEY_SERVER_URL = "server_url";
	public static final String KEY_SERVER_PORT = "server_port";

	public static void setName(String value) {
		put(KEY_AUTH_NAME, value);
	}

	public static String getName() {
		return getString(KEY_AUTH_NAME, "");
	}

	public static void setPassword(String value) {
		put(KEY_AUTH_PASSWORD, value);
	}

	public static String getPassword() {
		return getString(KEY_AUTH_PASSWORD, "");
	}

	public static boolean hasCredentials() {
		return !getName().isEmpty() && !getPassword().isEmpty();
	}

	public static void clearCredentials() {
		put(KEY_AUTH_NAME, "");
		put(KEY_AUTH_PASSWORD, "");
	}

	public static void setServerUrl(String value) {
		put(KEY_SERVER_URL, value);
	}

	public static String getServerUrl() {
		return getString(KEY_SERVER_URL, "");
	}

	public static void setServerPort(String value) {
		put(KEY_SERVER_PORT, value);
	}

	public static String getServerPort() {
		return getString(KEY_SERVER_PORT, "65535");
	}

	public static boolean hasCustomServer() {
		return !getServerUrl().isEmpty();
	}
}
