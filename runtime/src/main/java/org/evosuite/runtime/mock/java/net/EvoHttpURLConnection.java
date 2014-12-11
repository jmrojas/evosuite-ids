package org.evosuite.runtime.mock.java.net;

import org.evosuite.runtime.mock.java.io.MockIOException;
import org.evosuite.runtime.vnet.DNS;
import org.evosuite.runtime.vnet.RemoteFile;
import org.evosuite.runtime.vnet.VirtualNetwork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.security.Permission;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Does not seem there is the need for mocking HttpURLConnection or URLConnection, as those are abstract
 * and not having anything to mock (at least it seems).
 *
 * <p>
 * Actual implementations are not part of the public API, eg see:
 * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7-b147/sun/net/www/protocol/http/HttpURLConnection.java
 */
public class EvoHttpURLConnection extends HttpURLConnection {

    private InputStream stream;

    protected EvoHttpURLConnection(URL u) {
        super(u);
    }


    //-------  abstract methods ----------

    @Override
    public void disconnect() {
        //TODO
    }

    @Override
    public boolean usingProxy() {
        return false;//TODO
    }

    @Override
    public void connect() throws IOException {

        if(super.connected){
            return;
        }

        String resolved = VirtualNetwork.getInstance().dnsResolve(url.getHost());
        if(resolved == null){
            //TODO should rather mock java.net.UnknownHostException
            throw new MockIOException(url.getHost());
        }

        /*
            ideally there are a lot of different types of status that we could mock.
            but in the end, they are not so much used in the SF110
         */

        super.connected = true;

        RemoteFile rf = VirtualNetwork.getInstance().getFile(url);
        if(rf == null){
            super.responseCode = HTTP_NOT_FOUND;
            super.responseMessage = "Not Found";
        } else {
            super.responseCode = HTTP_OK;
            super.responseMessage = "OK";
            stream = rf.getInputStream();
        }
    }


    public InputStream getInputStream() throws IOException {
        connect();
        return stream;
    }

    // ---------------  TODO

    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support output");
    }

    public String toString() {
        return this.getClass().getName() + ":" + url;
    }

    // -------  public methods from HttpURLConnection  ---------

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        if (connected) {
            throw new ProtocolException("Can't reset method: already connected");
        }
        // This restriction will prevent people from using this class to
        // experiment w/ new HTTP methods using java.  But it should
        // be placed for security - the request String could be
        // arbitrarily long.

        /*
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].equals(method)) {
                if (method.equals("TRACE")) {
                    SecurityManager s = System.getSecurityManager();
                    if (s != null) {
                        s.checkPermission(new NetPermission("allowHttpTrace"));
                    }
                }
                this.method = method;
                return;
            }
        }
        */ //TODO
        throw new ProtocolException("Invalid HTTP method: " + method);
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public long getHeaderFieldDate(String name, long Default) {
        return super.getHeaderFieldDate(name,Default);
    }

    @Override
    public Permission getPermission() throws IOException {
        return super.getPermission();
    }

    @Override
    public int getResponseCode() throws IOException {
        return super.getResponseCode();
    }

    @Override
    public String getRequestMethod() {
        return method;
    }


    @Override
    public String getHeaderField(int n) {
        return null;
    }

    @Override
    public String getHeaderFieldKey (int n) {
        return null;
    }

    @Override
    public void setInstanceFollowRedirects(boolean followRedirects) {
        super.setInstanceFollowRedirects(followRedirects);
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return super.getInstanceFollowRedirects();
    }

    @Override
    public void setFixedLengthStreamingMode (int contentLength) {
        super.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setFixedLengthStreamingMode(long contentLength) {
        super.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setChunkedStreamingMode (int chunklen) {
        super.setChunkedStreamingMode(chunklen);
    }


    // ------- public methods from URLConnection  ---------


    @Override
    public void setConnectTimeout(int timeout) {
        super.setConnectTimeout(timeout);
    }

    @Override
    public int getConnectTimeout() {
        return super.getConnectTimeout();
    }

    @Override
    public void setReadTimeout(int timeout) {
        super.setReadTimeout(timeout);
    }

    @Override
    public int getReadTimeout() {
        return super.getReadTimeout();
    }

    @Override
    public URL getURL() {
        return super.getURL();
    }

    @Override
    public int getContentLength() {
        return super.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return super.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return super.getContentType();
    }

    @Override
    public String getContentEncoding() {
        return super.getContentEncoding();
    }

    @Override
    public long getExpiration() {
        return super.getExpiration();
    }

    @Override
    public long getDate() {
        return super.getDate();
    }

    @Override
    public long getLastModified() {
        return super.getLastModified();
    }

    @Override
    public String getHeaderField(String name) {
        return null;
    }


    @Override
    public Map<String,List<String>> getHeaderFields() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public int getHeaderFieldInt(String name, int Default) {
        return super.getHeaderFieldInt(name,Default);
    }

    @Override
    public long getHeaderFieldLong(String name, long Default) {
        return super.getHeaderFieldLong(name,Default);
    }

    @Override
    public Object getContent() throws IOException {
        return super.getContent();
    }

    @Override
    public Object getContent(Class[] classes) throws IOException {
        return super.getContent(classes);
    }

    @Override
    public void setDoInput(boolean doinput) {
        super.setDoInput(doinput);
    }

    @Override
    public boolean getDoInput() {
        return super.getDoInput();
    }


    @Override
    public void setDoOutput(boolean dooutput) {
        super.setDoOutput(dooutput);
    }

    @Override
    public boolean getDoOutput() {
        return super.getDoOutput();
    }

    @Override
    public void setAllowUserInteraction(boolean allowuserinteraction) {
        super.setAllowUserInteraction(allowuserinteraction);
    }

    @Override
    public boolean getAllowUserInteraction() {
        return super.getAllowUserInteraction();
    }


    //TODO
}
