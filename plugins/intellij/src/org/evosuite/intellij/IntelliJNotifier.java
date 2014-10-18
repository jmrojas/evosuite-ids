package org.evosuite.intellij;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.evosuite.intellij.util.AsyncGUINotifier;

import javax.swing.*;

/**
 * Created by arcuri on 10/2/14.
 */
public class IntelliJNotifier implements AsyncGUINotifier {

    private final String title;
    private final Project project;
    private final ConsoleViewImpl console;

    public IntelliJNotifier(Project project, String title, ConsoleViewImpl console) {
        this.project = project;
        this.title = title;
        this.console = console;
    }

    @Override
    public void success(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                Messages.showMessageDialog(project, message, title, Messages.getInformationIcon());
            }
        });
    }

    @Override
    public void failed(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                Messages.showMessageDialog(project, message, title, Messages.getWarningIcon());
            }
        });
    }

    @Override
    public void attachProcess(Process process) {
        OSProcessHandler handler = new OSProcessHandler(process, null);
        console.attachToProcess(handler);
        handler.startNotify();
    }
}
