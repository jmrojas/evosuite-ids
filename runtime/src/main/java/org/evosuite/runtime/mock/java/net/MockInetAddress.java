package org.evosuite.runtime.mock.java.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.List;

import org.evosuite.runtime.mock.StaticReplacementMock;
import org.evosuite.runtime.vnet.EvoIPAddressUtil;
import org.evosuite.runtime.vnet.NetworkInterfaceState;
import org.evosuite.runtime.vnet.VirtualNetwork;

/**
 * We need to mock this class mainly to handle hostnames resolutions,
 * which usually will be done through DNS and host files 
 * 
 */
public class MockInetAddress implements StaticReplacementMock{

	@Override
	public String getMockedClassName() {
		return InetAddress.class.getName();
	} 

	//-----  public instance methods -----------------
	/*
	 * Note: as we create instances with Inet4AddressUtil,
	 * here we do not need to mock these methods
	 */

	public static boolean isMulticastAddress(InetAddress addr){
		return addr.isMulticastAddress();
	}

	public static boolean isAnyLocalAddress(InetAddress addr){
		return addr.isAnyLocalAddress();
	}

	public static boolean isLoopbackAddress(InetAddress addr){
		return addr.isLoopbackAddress();
	}

	public static boolean isLinkLocalAddress(InetAddress addr){
		return addr.isLinkLocalAddress();
	}

	public static boolean isSiteLocalAddress(InetAddress addr){
		return addr.isSiteLocalAddress();
	}

	public static boolean isMCGlobal(InetAddress addr){
		return addr.isMCGlobal();
	}

	public static boolean isMCNodeLocal(InetAddress addr){
		return addr.isMCNodeLocal();
	}

	public static boolean isMCLinkLocal(InetAddress addr){
		return addr.isMCLinkLocal();
	}

	public static boolean isMCSiteLocal(InetAddress addr){
		return addr.isMCSiteLocal();
	}

	public static boolean isMCOrgLocal(InetAddress addr){
		return addr.isMCOrgLocal();
	}


	public static byte[] getAddress(InetAddress addr) {
		return addr.getAddress();
	}

	public static String getHostAddress(InetAddress addr) {
		return addr.getHostAddress();
	}

	public static int hashCode(InetAddress addr) {
		return addr.hashCode(); 
	}

	public static boolean equals(InetAddress addr, Object obj) {
		return addr.equals(obj); 
	}


	public static String toString(InetAddress addr) {
		return addr.toString(); 
	}


	// ----- public instance methods depending on virtual network -----

	public static boolean isReachable(InetAddress addr, int timeout) throws IOException{
		return isReachable(addr, null, 0 , timeout);
	}

	public static boolean isReachable(InetAddress addr, NetworkInterface netif, int ttl,
			int timeout) throws IOException {
		//TODO
		return false;
	}

	public static String getHostName(InetAddress addr) {
		//TODO
		return null;
	}

	public static String getCanonicalHostName(InetAddress addr) {
		//TODO
		return null;
	}
	//------ static methods in mocked ---------

	public static InetAddress getByAddress(byte[] addr)
			throws UnknownHostException {
		return null; //TODO
	}

	public static InetAddress getByAddress(String host, byte[] addr)
			throws UnknownHostException {
		return null; //TODO
	}

	public static InetAddress getByName(String host)
			throws UnknownHostException{
		return getAllByName(host)[0]; 
	}

	public static InetAddress[] getAllByName(String host)
			throws UnknownHostException{

		//if no specified host, return loopback
		if (host == null || host.length() == 0) {
			InetAddress[] ret = new InetAddress[1];
			ret[0] = getLoopbackAddress();
			return ret;
		}

		// if host is an IPv4 address, we won't do further lookup
		if (Character.digit(host.charAt(0), 16) != -1) {

			// see if it is IPv4 address
			byte[] addr = EvoIPAddressUtil.textToNumericFormatV4(host); 

			if(addr != null && addr.length == Inet4AddressUtil.INADDRSZ) {
				InetAddress[] ret = new InetAddress[1];
				ret[0] = Inet4AddressUtil.createNewInstance(null, addr); 
				return ret;
			}
		} 

		String resolved = VirtualNetwork.getInstance().dnsResolve(host);
		if(resolved == null){
			throw new UnknownHostException("Cannot resolve: "+resolved);
		}
		
		byte[] addr = EvoIPAddressUtil.textToNumericFormatV4(resolved);
		InetAddress[] ret = new InetAddress[1];
		ret[0] = Inet4AddressUtil.createNewInstance(host, addr); 
		return ret;		
	}


	public static InetAddress getLoopbackAddress() {

		List<NetworkInterfaceState> list = 
				VirtualNetwork.getInstance().getAllNetworkInterfaceStates();

		for(NetworkInterfaceState nis : list){
			if(!nis.isLoopback()){
				continue;
			}

			List<InetAddress> addresses = nis.getLocalAddresses();
			if(addresses==null || addresses.isEmpty()){
				continue;
			}

			return addresses.get(0);
		}

		return null; //nothing  found
	}

	public static InetAddress getLocalHost() throws UnknownHostException {
		/* 
		 * for simplicity, just return the loopback address.
		 * TODO: this ll need to be changed if we mock the name
		 * of the machine
		 */ 
		return getLoopbackAddress(); 
	}

}	
