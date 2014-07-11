package org.evosuite.runtime.mock.java.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.evosuite.runtime.vnet.VirtualNetwork;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;

public class ServerSocketTest {

	@Before
	public void init(){
		VirtualNetwork.getInstance().reset();
	}
	
	@Test
	public void testNotBound() throws IOException{
		MockServerSocket server = new MockServerSocket();
		try{
			server.accept();
			Assert.fail();
		} catch(Exception e){
			//expected, because not bound
		}
		
		int port = 42;		
		server.bind(new InetSocketAddress(port));
		Assert.assertTrue(server.isBound());
		
		try{
			server.accept();
			Assert.fail();
		} catch(Exception e){
			//expected. as there is no simulated inbound connection, the virtual network
			//should throw an IOE rather than blocking the test case
		}
		
		server.close();
	}
	
	@Test
	public void testCollidingBinding() throws IOException{
		MockServerSocket first = new MockServerSocket();
		int port = 42;		
		first.bind(new InetSocketAddress(port));
		
		MockServerSocket second = new MockServerSocket();
		try{
			second.bind(new InetSocketAddress(port));
			Assert.fail();
		} catch(IOException e){
			//expected, as binding on same port/interface
		}
		
		//binding on different port should work
		second.bind(new InetSocketAddress(port+1));
		
		first.close();
		second.close();
	}
	
	
	@Test
	public void testIncomingConnection() throws IOException{
		//first bind a listening server
		MockServerSocket server = new MockServerSocket();
		String localAddress = "127.0.0.1";
		int localPort = 42;
		server.bind(new InetSocketAddress(localAddress,localPort));
		
		//before accepting a connection, do register an incoming one
		String remoteAddress = "127.0.0.2";
		int remotePort = 1234;
		VirtualNetwork.getInstance().registerIncomingTcpConnection(remoteAddress, remotePort, localAddress, localPort);
		
		Socket socket = server.accept();
		Assert.assertNotNull(socket);
		Assert.assertEquals(remoteAddress,socket.getInetAddress().getHostAddress());
		Assert.assertEquals(remotePort, socket.getPort());
		Assert.assertEquals(localAddress, socket.getLocalAddress().getHostAddress());
		Assert.assertEquals(localPort, socket.getLocalPort());
		
		server.close();
		socket.close();
	}
	
	@Test 
	public void testReceiveMessage(){
		//TODO
	}
}
