/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ma.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.evosuite.ma.Editor;

import jsyntaxpane.DefaultSyntaxKit;

/**
 * @author Yury Pavlov
 */
public class WideTestEditorGUI implements ActionListener, TestEditorGUI {

	public final JFrame mainFrame = new JFrame("MA Editor");

	private final JButton btnParseTestCaseButton = new JButton("Parse");

	private final TitledBorder editorTitledBorder = new TitledBorder(null, "Test Editor", TitledBorder.LEADING,
			TitledBorder.TOP, null, null);

	private final JPanel testPanel = new JPanel();

	private JEditorPane origTestEditorPane;

	private JEditorPane esTestEditorPane;

	private Editor editor;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createMainWindow(final Editor editor) {
		this.editor = editor;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		mainFrame.setAlwaysOnTop(true);
		mainFrame.setLocation(new Point(dim.width - 585, dim.height - 600));
		mainFrame.setSize(new Dimension(1200, 600));

		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 592, 0 };
		gridBagLayout.rowHeights = new int[] { 320, 100, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		mainFrame.getContentPane().setLayout(gridBagLayout);

		testPanel.setBorder(editorTitledBorder);
		GridBagConstraints gbc_testPanel = new GridBagConstraints();
		gbc_testPanel.fill = GridBagConstraints.BOTH;
		gbc_testPanel.insets = new Insets(0, 0, 5, 0);
		gbc_testPanel.gridx = 0;
		gbc_testPanel.gridy = 0;
		mainFrame.getContentPane().add(testPanel, gbc_testPanel);
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.X_AXIS));

		origTestEditorPane = new JEditorPane();
		JScrollPane origTestScrollPane = new JScrollPane(origTestEditorPane);
		DefaultSyntaxKit.initKit();
		origTestEditorPane.setContentType("text/java");
		testPanel.add(origTestScrollPane);
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		updateTitle();

		origTestEditorPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				setTestCaseChanged();
			}
		});

		esTestEditorPane = new JEditorPane();
		esTestEditorPane.setEditable(false);
		JScrollPane esTestScrollPane = new JScrollPane(esTestEditorPane);
		DefaultSyntaxKit.initKit();
		esTestEditorPane.setContentType("text/java");
		testPanel.add(esTestScrollPane);
		esTestEditorPane.setText(editor.getCurrESTCCode());
		updateTitle();

		esTestEditorPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				setTestCaseChanged();
			}
		});

		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(null);
		controlPanel.setSize(new Dimension(40, 100));
		controlPanel.setLayout(null);
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 0;
		gbc_controlPanel.gridy = 1;
		mainFrame.getContentPane().add(controlPanel, gbc_controlPanel);

		JButton btnPrevTestButton = new JButton("Prev test");
		btnPrevTestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prevTest();
			}
		});

		btnPrevTestButton.setMinimumSize(new Dimension(120, 25));
		btnPrevTestButton.setMaximumSize(new Dimension(120, 25));
		btnPrevTestButton.setPreferredSize(new Dimension(120, 25));
		btnPrevTestButton.setBounds(12, 12, 119, 25);
		controlPanel.add(btnPrevTestButton);

		JButton btnNextTestButton = new JButton("Next test");
		btnNextTestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nextTest();
			}
		});
		btnNextTestButton.setBounds(143, 12, 119, 25);
		controlPanel.add(btnNextTestButton);

		JButton btnDeleteTest = new JButton("Del test");
		btnDeleteTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteTest();
			}
		});
		btnDeleteTest.setBounds(690, 12, 90, 25);
		controlPanel.add(btnDeleteTest);

		JButton btnNewTestButton = new JButton("New test");
		btnNewTestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newTest();
			}
		});
		btnNewTestButton.setBounds(274, 12, 119, 25);
		controlPanel.add(btnNewTestButton);

		JButton btnCloneTestButton = new JButton("Clone test");
		btnCloneTestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cloneTest();
			}
		});
		btnCloneTestButton.setBounds(274, 43, 119, 25);
		controlPanel.add(btnCloneTestButton);

		btnParseTestCaseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parseTest();
			}
		});
		btnParseTestCaseButton.setBounds(405, 12, 119, 25);
		controlPanel.add(btnParseTestCaseButton);

		JButton btnQuitButton = new JButton("Quit");
		btnQuitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				quit();
			}
		});
		btnQuitButton.setBounds(405, 43, 119, 25);
		controlPanel.add(btnQuitButton);

		JButton btnUndo = new JButton("UnDo");
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				unDo();
			}
		});
		btnUndo.setBounds(588, 43, 90, 25);
		controlPanel.add(btnUndo);

		JButton btnRedo = new JButton("ReDo");
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reDo();
			}
		});
		btnRedo.setBounds(690, 43, 90, 25);
		controlPanel.add(btnRedo);

		JButton btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reset();
			}
		});
		btnReset.setBounds(588, 12, 90, 25);
		controlPanel.add(btnReset);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadFile();
			}
		});
		btnLoad.setBounds(14, 43, 117, 25);
		controlPanel.add(btnLoad);

		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);

		JMenu mnTestCaseEditor = new JMenu("TestSuite");
		mnTestCaseEditor.setMnemonic(KeyEvent.VK_T);
		menuBar.add(mnTestCaseEditor);

		JMenuItem mntmPrevTest = new JMenuItem("Prev test", KeyEvent.VK_O);
		mntmPrevTest.addActionListener(this);
		KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
		mntmPrevTest.setAccelerator(ctrlOKeyStroke);
		mnTestCaseEditor.add(mntmPrevTest);

		JMenuItem mntmNextTest = new JMenuItem("Next test", KeyEvent.VK_P);
		mntmNextTest.addActionListener(this);
		KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
		mntmNextTest.setAccelerator(ctrlPKeyStroke);
		mnTestCaseEditor.add(mntmNextTest);

		mnTestCaseEditor.addSeparator();

		JMenuItem mntmNewTest = new JMenuItem("New test", KeyEvent.VK_N);
		mntmNewTest.addActionListener(this);
		KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
		mntmNewTest.setAccelerator(ctrlNKeyStroke);
		mnTestCaseEditor.add(mntmNewTest);

		JMenuItem mntmCloneTest = new JMenuItem("Clone test", KeyEvent.VK_L);
		mntmCloneTest.addActionListener(this);
		KeyStroke ctrlLKeyStroke = KeyStroke.getKeyStroke("control L");
		mntmCloneTest.setAccelerator(ctrlLKeyStroke);
		mnTestCaseEditor.add(mntmCloneTest);

		JMenuItem mntmParseTest = new JMenuItem("Parse", KeyEvent.VK_S);
		mntmParseTest.addActionListener(this);
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		mntmParseTest.setAccelerator(ctrlSKeyStroke);
		mnTestCaseEditor.add(mntmParseTest);

		mnTestCaseEditor.addSeparator();

		JMenuItem mntmQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
		mntmQuit.addActionListener(this);
		KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
		mntmQuit.setAccelerator(ctrlQKeyStroke);
		mnTestCaseEditor.add(mntmQuit);

		JMenu mnEditor = new JMenu("Editor");
		mnEditor.setMnemonic(KeyEvent.VK_E);
		menuBar.add(mnEditor);

		JMenuItem mntmCopy = new JMenuItem("Copy", KeyEvent.VK_C);
		mntmCopy.addActionListener(this);
		KeyStroke ctrlCKeyStroke = KeyStroke.getKeyStroke("control C");
		mntmCopy.setAccelerator(ctrlCKeyStroke);
		mnEditor.add(mntmCopy);

		JMenuItem mntmCut = new JMenuItem("Cut", KeyEvent.VK_X);
		mntmCut.addActionListener(this);
		KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
		mntmCut.setAccelerator(ctrlXKeyStroke);
		mnEditor.add(mntmCut);

		JMenuItem mntmPaste = new JMenuItem("Paste", KeyEvent.VK_V);
		mntmPaste.addActionListener(this);
		KeyStroke ctrlVKeyStroke = KeyStroke.getKeyStroke("control V");
		mntmPaste.setAccelerator(ctrlVKeyStroke);
		mnEditor.add(mntmPaste);

		mnEditor.addSeparator();

		JMenuItem mntmUnDo = new JMenuItem("Undo", KeyEvent.VK_Z);
		mntmUnDo.addActionListener(this);
		KeyStroke ctrlZKeyStroke = KeyStroke.getKeyStroke("control Z");
		mntmUnDo.setAccelerator(ctrlZKeyStroke);
		mnEditor.add(mntmUnDo);

		JMenuItem mntmReDo = new JMenuItem("Redo", KeyEvent.VK_Y);
		mntmReDo.addActionListener(this);
		KeyStroke ctrlYKeyStroke = KeyStroke.getKeyStroke("control Y");
		mntmReDo.setAccelerator(ctrlYKeyStroke);
		mnEditor.add(mntmReDo);

		mnEditor.addSeparator();

		JMenuItem mntmSett = new JMenuItem("Settings", KeyEvent.VK_B);
		mntmSett.addActionListener(this);
		KeyStroke ctrlBKeyStroke = KeyStroke.getKeyStroke("control B");
		mntmSett.setAccelerator(ctrlBKeyStroke);
		mnEditor.add(mntmSett);

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				quit();
			}
		});

		mainFrame.setVisible(true);
	}

	private void setTestCaseChanged() {
		btnParseTestCaseButton.setText("Parse*");
	}

	private void setTestCaseUnchanged() {
		btnParseTestCaseButton.setText("Parse");
	}

	private void updateTitle() {
		editorTitledBorder.setTitle("Test Editor     " + (editor.getNumOfCurrTest() + 1) + " / "
				+ editor.getNumOfTestCases() + "     Coverage: " + editor.getSuiteCoveratgeVal() + "%");
		testPanel.repaint();
	}

	private void prevTest() {
		editor.prevTCT();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void nextTest() {
		editor.nextTCT();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void deleteTest() {
		editor.delCurrTCT();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void newTest() {
		editor.createNewTCT();
		origTestEditorPane.setText("");
		esTestEditorPane.setText("");
		setTestCaseChanged();
		updateTitle();
	}

	private void cloneTest() {
		editor.createNewTCT();
		setTestCaseChanged();
		updateTitle();
	}

	private void parseTest() {
		if (editor.parseTest(origTestEditorPane.getText())) {
			editor.sguiSC.printSourceCode();
			origTestEditorPane.setText(editor.getCurrOrigTCCode());
			esTestEditorPane.setText(editor.getCurrESTCCode());
			setTestCaseUnchanged();
			updateTitle();
		}
	}
	
	private void loadFile() {
		editor.readFromFile();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void unDo() {
		editor.unDo();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void reDo() {
		editor.reDo();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void reset() {
		editor.reset();
		editor.sguiSC.printSourceCode();
		origTestEditorPane.setText(editor.getCurrOrigTCCode());
		esTestEditorPane.setText(editor.getCurrESTCCode());
		setTestCaseUnchanged();
		updateTitle();
	}

	private void quit() {
		editor.sguiSC.close();
		mainFrame.setVisible(false);
		synchronized (editor.lock) {
			editor.lock.notifyAll();
		}
		mainFrame.dispose();
	}

	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getActionCommand().equals("Prev test")) {
			prevTest();
		} else if (actionEvent.getActionCommand().equals("Next test")) {
			nextTest();
		} else if (actionEvent.getActionCommand().equals("Delete test")) {
			deleteTest();
		} else if (actionEvent.getActionCommand().equals("New test")) {
			newTest();
		} else if (actionEvent.getActionCommand().equals("Clone test")) {
			cloneTest();
		} else if (actionEvent.getActionCommand().equals("Parse")) {
			parseTest();
		} else if (actionEvent.getActionCommand().equals("Quit")) {
			quit();
		} else if (actionEvent.getActionCommand().equals("Settings")) {
			new SettingsGUI(mainFrame);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisb.cs.st.evosuite.ma.gui.TestEditorGUI#getMainFrame()
	 */
	@Override
	public JFrame getMainFrame() {
		return mainFrame;
	}
}
