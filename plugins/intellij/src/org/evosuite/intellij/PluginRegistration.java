package org.evosuite.intellij;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.ide.actions.AboutAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.evosuite.intellij.util.MavenExecutor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the IntelliJ plugin
 * <p/>
 * Created by arcuri on 9/9/14.
 */
public class PluginRegistration implements ProjectComponent { //implements ApplicationComponent {

    private final Project project;

    public PluginRegistration(Project project){
        this.project = project;
    }


    // Returns the component name (any unique string value).
    @NotNull
    public String getComponentName() {
        return "EvoSuite Plugin";
    }


    // If you register the PluginRegistration class in the <application-components> section of
// the plugin.xml file, this method is called on IDEA start-up.
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
        EvoParameters.getInstance().save(project);
    }

    @Override
    public void projectOpened() {

        EvoParameters.getInstance().load(project);

        ActionManager am = ActionManager.getInstance();

        //create the tool window, which will appear in the bottom when an EvoSuite run is started
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow("EvoSuite", false, ToolWindowAnchor.BOTTOM);
        toolWindow.setTitle("EvoSuite Console Output");
        toolWindow.setType(ToolWindowType.DOCKED, null);


        //create a console panel
        ConsoleViewImpl console = (ConsoleViewImpl) TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JComponent consolePanel = console.getComponent();


        IntelliJNotifier notifier = new IntelliJNotifier(project,"EvoSuite Plugin", console);

        //create left-toolbar with stop button
        DefaultActionGroup buttonGroup = new DefaultActionGroup();
        buttonGroup.add(new StopEvoAction(notifier));
        ActionToolbar viewToolbar = am.createActionToolbar("EvoSuite.ConsoleToolbar", buttonGroup, false);
        JComponent toolBarPanel = viewToolbar.getComponent();


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(toolBarPanel, BorderLayout.WEST);
        panel.add(consolePanel,BorderLayout.CENTER);


        //Content content = contentFactory.createContent(consolePanel, "", false);
        Content content = contentFactory.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);



        EvoAction evo = new EvoAction(toolWindow, notifier);

        // Gets an instance of the WindowMenu action group.
        //DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("WindowMenu");
        //this in the file editor, not the left-pane file selection
        //DefaultActionGroup editorM = (DefaultActionGroup) am.getAction("EditorPopupMenu");

        DefaultActionGroup pvM = (DefaultActionGroup) am.getAction("ProjectViewPopupMenu");
        pvM.addSeparator();
        pvM.add(evo);
    }

    @Override
    public void projectClosed() {
        EvoParameters.getInstance().save(project);
    }

}