package org.evosuite.runtime.vnet;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton class used to simulate a virtual network.
 * This is useful to test classes that use UDP/TCP connections
 * 
 * @author arcuri
 *
 */
public class VirtualNetwork {

	public enum ConnectionType {UDP,TCP};
	
	/**
	 * Singleton instance
	 */
	private static final VirtualNetwork instance = new VirtualNetwork();

	/**
	 * When we simulate a remote incoming connection, we still need a remote port.
	 * Note: in theory we could have the same port if we simulate several different
	 * remote hosts. But, for unit testing purposes, it is likely an unnecessary
	 * overhead/complication
	 */
	private static final int START_OF_REMOTE_EPHEMERAL_PORTS = 40000; 
	
	/**
	 * Set of listening ports locally opened by the SUTs.
	 * Eg, when the SUTs work as a server, we keep track
	 * of which local ports/interfaces they listen to.
	 */
	private final Set<EndPointInfo> localListeningPorts;
	
	/**
	 * Buffer of incoming connections.
	 * 
	 * <p>
	 * Key -> local address/port
	 * <p>
	 * Value -> queue of foreign addresses/ports waiting to connect to the given local address (key)
	 */
	private final Map<EndPointInfo,Queue<NativeTcp>> incomingConnections;
	
	/**
	 * Keep track of all TCP connections opened during the tests.
	 * This is for example useful to check what data the SUT sent.
	 */
	private final Set<NativeTcp> openedTcpConnections;
	
	/**
	 * Current remote port number that can be opened
	 */
	private final AtomicInteger remotePortIndex;
	
	/**
	 * private, singleton constructor
	 */
	private VirtualNetwork(){
        localListeningPorts = new CopyOnWriteArraySet<>();
        incomingConnections = new ConcurrentHashMap<>();
        openedTcpConnections = new CopyOnWriteArraySet<>();
        remotePortIndex = new AtomicInteger(START_OF_REMOTE_EPHEMERAL_PORTS);
	}
	
	public static VirtualNetwork getInstance(){
		return instance;
	}
	
	//------------------------------------------

	public void reset(){
		localListeningPorts.clear();
		incomingConnections.clear();
		//TODO handle openedTcpConnections
		remotePortIndex.set(START_OF_REMOTE_EPHEMERAL_PORTS);
	}
	
	/**
	 * Create new port to open on remote host
	 * 
	 * @return a integer representing a port number on remote host
	 */
	public int getNewRemoteEphemeralPort(){
		return remotePortIndex.getAndIncrement();
	}
	
	/**
	 * Simulate an incoming connection. The connection is put on a buffer till
	 * the SUT open a listening port
	 * 
	 * @param originAddr
	 * @param originPort
	 * @param destAddr
	 * @param destPort
	 */
	public synchronized NativeTcp registerIncomingTcpConnection(
			String originAddr, int originPort,
			String destAddr, int destPort){
		
		EndPointInfo origin = new EndPointInfo(originAddr,originPort,ConnectionType.TCP);
		EndPointInfo dest = new EndPointInfo(destAddr,destPort,ConnectionType.TCP);
		
		Queue<NativeTcp> queue = incomingConnections.get(dest);
		if(queue == null){
			queue = new ConcurrentLinkedQueue<>();
			incomingConnections.put(dest, queue);
		}
		
		NativeTcp connection = new NativeTcp(dest,origin);		
		queue.add(connection);
		return connection;
	}

	/**
	 * Return a TCP connection for the given local address if there is any inbound remote connection to it
	 * 
	 * @param localAddress
	 * @param localPort
	 * @return  {@code null} if the test case has not set up it an incoming TCP connection
	 */
	public synchronized NativeTcp pullTcpConnection(String localAddress, int localPort){
		EndPointInfo local = new EndPointInfo(localAddress,localPort,ConnectionType.TCP);
		Queue<NativeTcp> queue = incomingConnections.get(local);
		if(queue == null || queue.isEmpty()){
			return null;
		}
		
		NativeTcp connection = queue.poll();
		openedTcpConnections.add(connection);
		
		return connection; 
	}
	
	
	/**
	 * 
	 * @param addr
	 * @return {@code false} if it was not possible to open the listening port
	 */
	public synchronized boolean  openTcpServer(String addr, int port){
		EndPointInfo info = new EndPointInfo(addr,port,ConnectionType.TCP);
		
		if(localListeningPorts.contains(info)){
			//there is already an existing opened port
			return false;
		}
		
		if(! isValidLocalServer(info)){
			return false;
		}
		
		localListeningPorts.add(info);
		
		return true;
	}
	
	
	//------------------------------------------

	private boolean isValidLocalServer(EndPointInfo info){
		return true; //TODO
	}
	
	//------------------------------------------
}
