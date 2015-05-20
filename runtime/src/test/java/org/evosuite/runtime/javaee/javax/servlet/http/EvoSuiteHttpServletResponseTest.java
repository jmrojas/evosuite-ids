package org.evosuite.runtime.javaee.javax.servlet.http;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Andrea Arcuri on 20/05/15.
 */
public class EvoSuiteHttpServletResponseTest {

    @Test
    public void testSimpleWrite() throws IOException {
        String a = "<html>";
        String b = "foo";
        String c = "</html>";

        EvoSuiteHttpServletResponse res = new EvoSuiteHttpServletResponse();
        Assert.assertFalse(res.isCommitted());

        PrintWriter out = res.getWriter();
        out.print(a);
        out.print(b);
        out.print(c);

        Assert.assertEquals(EvoSuiteHttpServletResponse.WARN_NO_COMMITTED, res.getBody());

        out.close();

        Assert.assertTrue(res.isCommitted());
        Assert.assertEquals(a+b+c, res.getBody());
    }
}
