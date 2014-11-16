package org.evosuite.statistics;

import com.examples.with.different.packagename.Compositional;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.statistics.backend.DebugStatisticsBackend;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


public class TimelineForCombinedFitness1Test extends SystemTest {

    @Test
    public void testTimelineForCombinedFitness() {
        EvoSuite evosuite = new EvoSuite();

        String targetClass = Compositional.class.getCanonicalName();

        Properties.TARGET_CLASS = targetClass;
        Properties.COMPOSITIONAL_FITNESS = true;
        Properties.CRITERION = new Properties.Criterion[4];
        Properties.CRITERION[0] = Properties.Criterion.ONLYBRANCH;
        Properties.CRITERION[1] = Properties.Criterion.METHODNOEXCEPTION;
        Properties.CRITERION[2] = Properties.Criterion.METHOD;
        Properties.CRITERION[3] = Properties.Criterion.OUTPUT;


        StringBuilder s = new StringBuilder();
        s.append(RuntimeVariable.CoverageTimeline); s.append(",");
        s.append(RuntimeVariable.OnlyBranchCoverageTimeline); s.append(",");
        s.append(RuntimeVariable.MethodCoverageTimeline); s.append(",");
        s.append(RuntimeVariable.MethodNoExceptionCoverageTimeline); s.append(",");
        s.append(RuntimeVariable.OutputCoverageTimeline);
        Properties.OUTPUT_VARIABLES = s.toString();

        String[] command = new String[] { "-generateSuite", "-class", targetClass };

        evosuite.parseCommandLine(command);

        Map<String, OutputVariable<?>> map = DebugStatisticsBackend.getLatestWritten();
        Assert.assertNotNull(map);

        String strVar1 = RuntimeVariable.MethodCoverageTimeline.toString();
        OutputVariable method = getLastTimelineVariable(map, strVar1);
        Assert.assertNotNull(method);
        Assert.assertEquals("Incorrect last timeline value for " + strVar1, 1.0, method.getValue());

        String strVar2 = RuntimeVariable.MethodNoExceptionCoverageTimeline.toString();
        OutputVariable methodNE = getLastTimelineVariable(map, strVar2);
        Assert.assertNotNull(methodNE);
        Assert.assertEquals("Incorrect last timeline value for " + strVar2, 1.0, methodNE.getValue());

        String strVar3 = RuntimeVariable.OutputCoverageTimeline.toString();
        OutputVariable output = getLastTimelineVariable(map, strVar3);
        Assert.assertNotNull(output);
        Assert.assertEquals("Incorrect last timeline value for " + strVar3, 1.0, output.getValue());
    }

    private OutputVariable getLastTimelineVariable(Map<String, OutputVariable<?>> map, String name) {
        OutputVariable timelineVar = null;
        int max = -1;
        for (Map.Entry<String, OutputVariable<?>> e : map.entrySet()) {
            if (e.getKey().startsWith(name)) {
                int index = Integer.parseInt( (e.getKey().split("_T"))[1] );
                if (index > max) {
                    max = index;
                    timelineVar = e.getValue();
                }
            }
        }
        return timelineVar;
    }
}
