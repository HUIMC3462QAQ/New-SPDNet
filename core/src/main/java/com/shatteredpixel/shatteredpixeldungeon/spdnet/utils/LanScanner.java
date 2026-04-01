package com.shatteredpixel.shatteredpixeldungeon.spdnet.utils;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.NetSettings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

/**
 * 局域网扫描工具类
 * 用于扫描本地网络中的SPDNet服务器
 */
public class LanScanner {

	public static final int DEFAULT_PORT = 65535;
	public static final int TIMEOUT_MS = 800;
	public static final int THREAD_POOL_SIZE = 30;

	/**
	 * 扫描结果回调接口
	 */
	public interface ScanCallback {
		void onServerFound(String serverUrl);
		void onScanComplete(List<String> servers);
		void onProgress(int scanned, int total);
	}

	/**
	 * 扫描局域网中的服务器
	 * @param port 服务器端口，默认65535
	 * @param callback 回调接口
	 */
	public static void scan(int port, ScanCallback callback) {
		String localIP = getLocalIPAddress();
		if (localIP == null) {
			callback.onScanComplete(new ArrayList<>());
			return;
		}

		String subnet = getSubnet(localIP);
		if (subnet == null) {
			callback.onScanComplete(new ArrayList<>());
			return;
		}

		List<String> servers = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		CountDownLatch latch = new CountDownLatch(254);

		for (int i = 1; i <= 254; i++) {
			final String targetIP = subnet + "." + i;
			executor.submit(() -> {
				try {
					if (isServerReachable(targetIP, port)) {
						String serverUrl = "http://" + targetIP + ":" + port + "/spdnet";
						servers.add(serverUrl);
						callback.onServerFound(serverUrl);
					}
				} catch (Exception e) {
					// 忽略单个IP的扫描错误
				} finally {
					latch.countDown();
					callback.onProgress(254 - (int) latch.getCount(), 254);
				}
			});
		}

		executor.shutdown();
		try {
			latch.await(30, TimeUnit.SECONDS);
			executor.shutdownNow();
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

		callback.onScanComplete(servers);
	}

	/**
	 * 快速扫描 - 只扫描常见IP段
	 */
	public static void quickScan(int port, ScanCallback callback) {
		String localIP = getLocalIPAddress();
		if (localIP == null) {
			callback.onScanComplete(new ArrayList<>());
			return;
		}

		String subnet = getSubnet(localIP);
		if (subnet == null) {
			callback.onScanComplete(new ArrayList<>());
			return;
		}

		// 优先扫描常见的网关和主机地址
		int[] quickScanIPs = {1, 2, 100, 101, 254, 128, 192, 200, 201, 1, 10, 20, 30, 40, 50};

		List<String> servers = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		CountDownLatch latch = new CountDownLatch(quickScanIPs.length);

		for (int ip : quickScanIPs) {
			final String targetIP = subnet + "." + ip;
			executor.submit(() -> {
				try {
					if (isServerReachable(targetIP, port)) {
						String serverUrl = "http://" + targetIP + ":" + port + "/spdnet";
						servers.add(serverUrl);
						callback.onServerFound(serverUrl);
					}
				} catch (Exception e) {
					// 忽略单个IP的扫描错误
				} finally {
					latch.countDown();
				}
			});
		}

		executor.shutdown();
		try {
			latch.await(10, TimeUnit.SECONDS);
			executor.shutdownNow();
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

		callback.onScanComplete(servers);
	}

	/**
	 * 检测服务器是否可达
	 */
	private static boolean isServerReachable(String ip, int port) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), TIMEOUT_MS);
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 获取本机IP地址
	 */
	public static String getLocalIPAddress() {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress();
						// 检查是否是IPv4地址
						boolean isIPv4 = sAddr.indexOf(':') < 0;
						if (isIPv4 && !sAddr.startsWith("169.254")) {
							return sAddr;
						}
					}
				}
			}
		} catch (Exception e) {
			// 忽略错误
		}
		return null;
	}

	/**
	 * 获取子网前缀
	 */
	private static String getSubnet(String ip) {
		int lastDot = ip.lastIndexOf('.');
		if (lastDot > 0) {
			return ip.substring(0, lastDot);
		}
		return null;
	}

	/**
	 * 获取当前保存的服务器URL
	 */
	public static String getSavedServerUrl() {
		String savedUrl = NetSettings.getServerUrl();
		String savedPort = NetSettings.getServerPort();

		if (!savedUrl.isEmpty()) {
			if (!savedPort.isEmpty() && !savedPort.equals("65535")) {
				return "http://" + savedUrl + ":" + savedPort + "/spdnet";
			}
			return "http://" + savedUrl + ":65535/spdnet";
		}
		return null;
	}
}
