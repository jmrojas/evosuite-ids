package org.evosuite.intellij;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.evosuite.intellij.util.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.text.NumberFormat;

public class EvoStartDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField memoryField;
    private JFormattedTextField coreField;
    private JFormattedTextField timeField;
    private JTextField folderField;
    private JTextField mavenField;
    private JTextField javaHomeField;
    private JButton selectMavenButton;
    private JButton selectJavaHomeButton;
    private JTextField evosuiteLocationTesxField;
    private JButton evosuiteSelectionButton;
    private JRadioButton mavenRadioButton;
    private JRadioButton evosuiteRadioButton;

    private volatile boolean wasOK = false;
    private volatile EvoParameters params;
    private volatile Project project;

    public void initFields(Project project, EvoParameters params){
        this.project = project;
        this.params = params;

        coreField.setValue(params.getCores());
        memoryField.setValue(params.getMemory());
        timeField.setValue(params.getTime());

        folderField.setText(params.getFolder());
        mavenField.setText(params.getMvnLocation());
        evosuiteLocationTesxField.setText(params.getEvosuiteJarLocation());
        javaHomeField.setText(params.getJavaHome());

        //TODO sounds useful
        //ProjectJdkTable.getInstance().getAllJdks();

        if(! Utils.isMavenProject(project)){
            //disable Maven options
            selectMavenButton.setEnabled(false);
            mavenRadioButton.setEnabled(false);
            params.setExecutionMode(EvoParameters.EXECUTION_MODE_JAR);
        }

        if(params.usesMaven()){
            mavenRadioButton.setSelected(true);
        } else {
            evosuiteRadioButton.setSelected(true);
        }
        checkExecution();
    }


    public EvoStartDialog() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        selectMavenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onSelectMvn();
            }
        });
        selectJavaHomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onSelectJavaHome();
            }
        });
        mavenRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                checkExecution();
            }
        });
        evosuiteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                checkExecution();
            }
        });
        evosuiteSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSelectEvosuite();
            }
        });
    }

    private void onSelectEvosuite() {
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return checkIfValidEvoSuiteJar(file);
            }

            @Override
            public String getDescription() {
                return "EvoSuite executable jar";
            }
        });

        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            params.setEvosuiteJarLocation(path);
            evosuiteLocationTesxField.setText(path);
        }
    }

    private void checkExecution(){
        if(mavenRadioButton.isSelected()){
            params.setExecutionMode(EvoParameters.EXECUTION_MODE_MVN);
        } else if(evosuiteRadioButton.isSelected()){
            params.setExecutionMode(EvoParameters.EXECUTION_MODE_JAR);
        }

        evosuiteLocationTesxField.setEnabled(evosuiteRadioButton.isSelected());
        evosuiteSelectionButton.setEnabled(evosuiteRadioButton.isSelected());
        mavenField.setEnabled(mavenRadioButton.isSelected());
        selectMavenButton.setEnabled(mavenRadioButton.isSelected());
    }

    private void onSelectMvn(){
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return checkIfValidMaven(file);
            }

            @Override
            public String getDescription() {
                return "Maven executable";
            }
        });

        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            params.setMvnLocation(path);
            mavenField.setText(path);
        }
    }

    private void onSelectJavaHome(){
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return checkIfValidJavaHome(file);
            }

            @Override
            public String getDescription() {
                return "Java Home (containing bin/javac)";
            }
        });
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();

            if(! checkIfValidJavaHome(new File(path))){
                Messages.showMessageDialog(project, "Invalid JDK home: choose a correct one that contains bin/javac",
                        "EvoSuite Plugin", Messages.getErrorIcon());
                return;
            }

            params.setJavaHome(path);
            javaHomeField.setText(path);
        }
    }

    private boolean checkIfValidJavaHome(File file){
        if(file == null || !file.exists() || !file.isDirectory()){
            return false;
        }

        String javac = Utils.isWindows() ? "javac.exe" : "javac";
        File jf = new File(new File(file,"bin") , javac);
        return jf.exists();
    }

    private boolean checkIfValidEvoSuiteJar(File file){
        if(file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        String name = file.getName().toLowerCase();
        return name.startsWith("evosuite") && name.endsWith(".jar");
    }

    private boolean checkIfValidMaven(File file){
        if(file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        String name = Utils.isWindows() ? "mvn.bat" : "mvn";
        return file.getName().toLowerCase().equals(name);
    }


    private void onOK() {
// add your code here

        dispose();

        //int cores = Integer.parseInt(coreField.getText());
        //int memory = Integer.parseInt(memoryField.getText());
        //int time = Integer.parseInt(timeField.getText());
        int cores = ((Number)coreField.getValue()).intValue();
        int memory = ((Number)memoryField.getValue()).intValue();
        int time = ((Number)timeField.getValue()).intValue();
        String dir = folderField.getText();
        String mvn = mavenField.getText();
        String javaHome = javaHomeField.getText();
        String evosuiteJar = evosuiteLocationTesxField.getText();

        String title = "EvoSuite Plugin";


        if (cores < 1 || memory < 1 || time < 1) {
            Messages.showMessageDialog(project, "Parameters need positive values",
                    title, Messages.getErrorIcon());
            return;
        }

        if(params.usesMaven() && !checkIfValidMaven(new File(mvn))){
            Messages.showMessageDialog(project, "Invalid Maven executable: choose a correct one",
                    title, Messages.getErrorIcon());
            return;
        }

        if(!params.usesMaven() && !checkIfValidEvoSuiteJar(new File(evosuiteJar))){
            Messages.showMessageDialog(project, "Invalid EvoSuite executable jar: choose a correct evosuite*.jar one",
                    title, Messages.getErrorIcon());
            return;
        }

        if(! checkIfValidJavaHome(new File(javaHome))){
            Messages.showMessageDialog(project, "Invalid JDK home: choose a correct one that contains bin/javac",
                    title, Messages.getErrorIcon());
            return;
        }

        params.setCores(cores);
        params.setMemory(memory);
        params.setTime(time);
        params.setFolder(dir);
        params.setMvnLocation(mvn);
        params.setEvosuiteJarLocation(evosuiteJar);
        params.setJavaHome(javaHome);

        wasOK = true;
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EvoStartDialog dialog = new EvoStartDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setParseIntegerOnly(true);
        coreField = new JFormattedTextField(nf);
        memoryField = new JFormattedTextField(nf);
        timeField = new JFormattedTextField(nf);
    }

    public boolean isWasOK() {
        return wasOK;
    }
}
