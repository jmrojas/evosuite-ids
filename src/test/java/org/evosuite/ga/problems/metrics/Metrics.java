package org.evosuite.ga.problems.metrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.evosuite.Properties;

public abstract class Metrics
{
    public static double[][] readFront(String problemName)
        throws IOException
    {
        double[][] front = new double[Properties.POPULATION][2];
        int index = 0;

        BufferedReader br = new BufferedReader(new FileReader(ClassLoader.getSystemResource(problemName).getPath()));
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            String[] split = sCurrentLine.split(",");
            front[index][0] = Double.valueOf(split[0]);
            front[index][1] = Double.valueOf(split[1]);

            index++;
        }
        br.close();

        return front;
    }

    protected double euclideanDistance(double[] a, double[] b)
    {
        double distance = 0.0;
        for (int i = 0; i < a.length; i++)
            distance += Math.pow(a[i] - b[i], 2.0);

        return Math.sqrt(distance);
    }
}
