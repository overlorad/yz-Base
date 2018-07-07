package com.yzfar.www.base.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: cp
 * @date: 2018年5月28日 下午4:57:34
 */
public final class IpUtil {

	public static String[] getLocalHostNames() {
		final Set<String> hostNames = new HashSet<String>();
		hostNames.add("localhost");
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			for (final Enumeration<NetworkInterface> ifaces = networkInterfaces; ifaces.hasMoreElements();) {
				final NetworkInterface iface = ifaces.nextElement();
				InetAddress ia = null;
				for (final Enumeration<InetAddress> ips = iface.getInetAddresses(); ips.hasMoreElements();) {
					ia = ips.nextElement();
					hostNames.add(ia.getCanonicalHostName());
					hostNames.add(ipToString(ia.getAddress()));
				}
			}
		} catch (final SocketException e) {
			throw new RuntimeException("unable to retrieve host names of localhost");
		}
		return hostNames.toArray(new String[hostNames.size()]);
	}

	private static String ipToString(final byte[] bytes) {
		final StringBuffer addrStr = new StringBuffer();
		for (int cnt = 0; cnt < bytes.length; cnt++) {
			final int uByte = bytes[cnt] < 0 ? bytes[cnt] + 256 : bytes[cnt];
			addrStr.append(uByte);
			if (cnt < 3)
				addrStr.append('.');
		}
		return addrStr.toString();
	}

	/**
	 * long转换IP
	 */
	public static String longToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);

	}

	/**
	 * IP转换long
	 */
	public static Long ipToLong(String ipAddress) {
		String[] ipAddressInArray = ipAddress.split("\\.");
		if (ipAddressInArray == null || ipAddressInArray.length != 4) {
			return -1l;
		}
		long result = 0;
		for (int i = 3; i >= 0; i--) {
			long ip = Long.parseLong(ipAddressInArray[3 - i]);
			result |= ip << (i * 8);
		}
		return result;
	}

}
