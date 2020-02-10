package com.tibco.cep.runtime.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import sun.net.util.IPAddressUtil;

/*
* Author: Ashwin Jayaprakash Date: Feb 26, 2009 Time: 11:52:41 AM
*/
public class ProcessInfo {
    public static String getProcessIdentifier() {
        try {
            RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

            if (bean != null) {
                return bean.getName();
            }
        }
        catch (Exception e) {
            //Ignore.
        }

        return "<Process id N/A>";
    }

    public static String getHostAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();

        return address.getHostAddress();
    }

    public static String getFQProcessIdentifier(String pid, String hostIP) throws UnknownHostException {
        final String simpleHostName = pid.split("@")[1];
        String fqdn = ensureFQDN(simpleHostName, hostIP) ;

        return pid.replaceFirst(simpleHostName,fqdn);
    }

    /**
     *  Returns  The Fully Qualified Domain Name (FQDN) for the given IP address
     *
     *  @param IP IP address to be resolved to a FQDN
     *  @return The FQDN for the given IP address
     *  @throws UnknownHostException If the IP address of the host could not be determined
     * */
	public static String getFQDN(String IP) throws UnknownHostException {
		if (IPAddressUtil.isIPv6LiteralAddress(IP)) {
			byte[] byteIP = IPAddressUtil.textToNumericFormatV6(IP);
			return Inet6Address.getByAddress(byteIP).getCanonicalHostName();
		} else {
			String[] IPSplit = IP.split("\\.");
			byte[] byteIP = new byte[IPSplit.length];

			for (int i = 0; i < IPSplit.length; i++) {
				byteIP[i] = (byte) Integer.parseInt(IPSplit[i]);
			}
			return InetAddress.getByAddress(byteIP).getCanonicalHostName();
		}

	}


    /**
     *  Returns the Fully Qualified Domain Name (FQDN) for the simple host name provided
     *  if the FQDN resolved for this IP address contains the simple host name provided in the
     *  first parameter. Otherwise it throws UnknownHostException.
     *
     *  @param hostName simple host name whose FQDN is to be obtained
     *  @param IP IP address used to resolve the FQDN
     *  @return The FQDN for the simple host name provided
     *  @throws UnknownHostException If the IP address provided in the second parameter could not be determined, or if the FQDN resolved for
     *  this IP address does not match the simple host name provided in the first parameter
     * */
	public static String ensureFQDN(String hostName, String IP) throws UnknownHostException {
		// Convert IP into dot-decimal notation
		if (IP.equalsIgnoreCase("localhost"))
			IP = InetAddress.getLocalHost().getHostAddress();
		else {
			final String ipPattern =
					"\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

			// Invalid IP
			if (!IP.matches(ipPattern) && !IPAddressUtil.isIPv6LiteralAddress(IP)) {
				throw new NumberFormatException("Invalid IP address: " + IP);
			}
		}

		final String fqdn = getFQDN(IP);

		if (hostName.equals(IP) || fqdn.toLowerCase().startsWith(hostName.toLowerCase()))
			return fqdn;
		else
			return IP;
	}
}
