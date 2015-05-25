package org.evosuite.runtime.javaee.javax.servlet;

import org.evosuite.runtime.annotation.Constraints;
import org.evosuite.runtime.annotation.EvoSuiteExclude;
import org.evosuite.runtime.javaee.javax.servlet.http.EvoHttpServletRequest;
import org.evosuite.runtime.javaee.javax.servlet.http.EvoHttpServletResponse;

import javax.servlet.AsyncContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * Class used in the JUnit test cases to access the state of the servlet
 *
 * Created by Andrea Arcuri on 21/05/15.
 */
public class EvoServletState {

    /*
        Note: they need to be static, as called directly from tests
     */

    private static EvoServletConfig config;
    private static EvoHttpServletRequest req;
    private static EvoHttpServletResponse resp;

    /**
     * Usually, this will be the SUT
     */
    private static Servlet servlet;

    @EvoSuiteExclude
    public static void reset(){
        config = null;
        req = null;
        resp = null;
        servlet = null;
    }

    @EvoSuiteExclude
    public static Servlet getServlet() {
        return servlet;
    }

    /*
        Note: the constraints here imply that at most one servlet can be tested in  single test case
     */

    @Constraints(atMostOnce = true, noNullInputs = true)
    public static <T extends Servlet> T initServlet(T servlet) throws IllegalStateException, IllegalArgumentException, ServletException {
        if(servlet == null){
            throw new IllegalArgumentException("Null servlet");
        }
        if(EvoServletState.servlet != null){
            throw new IllegalStateException("Should only be one servlet per test");
        }
        EvoServletState.servlet = servlet;
        servlet.init(getConfiguration());
        return servlet;
    }

    @Constraints(atMostOnce = true, after = "initServlet")
    public static EvoServletConfig getConfiguration() throws IllegalStateException{
        checkInit();
        if(config == null){
            config = new EvoServletConfig();
        }
        return config;
    }

    @Constraints(atMostOnce = true, after = "initServlet")
    public static EvoHttpServletRequest getRequest() throws IllegalStateException{
        checkInit();
        if(req == null){
            req = new EvoHttpServletRequest();
        }
        return req;
    }

    @Constraints(atMostOnce = true, after = "initServlet")
    public static EvoHttpServletResponse getResponse() throws IllegalStateException{
        checkInit();
        if(resp == null){
            resp = new EvoHttpServletResponse();
        }
        return resp;
    }

    @Constraints(atMostOnce = true, after = "initServlet")
    public static AsyncContext getAsyncContext() throws IllegalStateException{
        checkInit();
        if(getRequest().isAsyncStarted()){
            return getRequest().getAsyncContext();
        } else {
            return getRequest().startAsync();
        }
    }

    private static void checkInit() throws IllegalStateException{
        if(servlet == null){
            throw new IllegalStateException("Servlet is not initialized");
        }
    }
}
