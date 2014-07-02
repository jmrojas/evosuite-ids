package org.evosuite.runtime.mock.java.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketImpl;

public abstract class MockSocketImpl extends SocketImpl{

    Socket socket = null;
    ServerSocket serverSocket = null;

    /*
    protected FileDescriptor fd;
    protected InetAddress address;
    protected int port;
    protected int localport;
     */
    
    /*
     * Abstract methods from superclass.
     * Still need to be declared here, because they might be
     * called by other net classes in same package
     */
    protected abstract void create(boolean stream) throws IOException;
    protected abstract void connect(String host, int port) throws IOException;
    protected abstract void connect(InetAddress address, int port) throws IOException;
    protected abstract void connect(SocketAddress address, int timeout) throws IOException;
    protected abstract void bind(InetAddress host, int port) throws IOException;
    protected abstract void listen(int backlog) throws IOException;
    protected abstract void accept(SocketImpl s) throws IOException;
    protected abstract InputStream getInputStream() throws IOException;
    protected abstract OutputStream getOutputStream() throws IOException;
    protected abstract int available() throws IOException;
    protected abstract void close() throws IOException;
    protected abstract void sendUrgentData (int data) throws IOException;
     

    @Override
    protected void shutdownInput() throws IOException {
      //throw new IOException("Method not implemented!");
    		super.shutdownInput();
    }

    @Override
    protected void shutdownOutput() throws IOException {
      //throw new IOException("Method not implemented!");
    		super.shutdownOutput();
    }

    @Override
    protected FileDescriptor getFileDescriptor() {
        //return fd;
    		return super.getFileDescriptor();
    }

    @Override
    protected InetAddress getInetAddress() {
        //return address;
    		return super.getInetAddress();
    }

    @Override
    protected int getPort() {
        //return port;
    		return super.getPort();
    }

    @Override
    protected int getLocalPort() {
        //return localport;
    		return super.getLocalPort();
    }

    @Override
    protected boolean supportsUrgentData () {
        //return false; // must be overridden in sub-class
    		return super.supportsUrgentData();
    }

    @Override
    protected void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth){
        super.setPerformancePreferences(connectionTime, latency, bandwidth);
    }
    
    @Override
    public String toString() {
    		return super.toString();
        //return "Socket[addr=" + getInetAddress() +
          //  ",port=" + getPort() + ",localport=" + getLocalPort()  + "]";
    }

    
    
    /*
     *  Following are methods that are package level 
     *  in SocketImpl, and as such they cannot be overridden
     *  
     *  TODO need to check ALL of their callers in java.net.*
     */
    
    
    void setSocket(Socket soc) {
        this.socket = soc;
    }

    Socket getSocket() {
        return socket;
    }

    void setServerSocket(ServerSocket soc) {
        this.serverSocket = soc;
    }

    ServerSocket getServerSocket() {
        return serverSocket;
    }


    void reset() throws IOException {
        address = null;
        port = 0;
        localport = 0;
    }

	
}
