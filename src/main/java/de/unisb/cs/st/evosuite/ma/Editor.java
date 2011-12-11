package de.unisb.cs.st.evosuite.ma;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.TestSuiteGenerator;
import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;
import de.unisb.cs.st.evosuite.ma.gui.SourceCodeGUI;
import de.unisb.cs.st.evosuite.ma.gui.TestEditorGUI;
import de.unisb.cs.st.evosuite.ma.parser.SEParser;
import de.unisb.cs.st.evosuite.testcase.DefaultTestCase;
import de.unisb.cs.st.evosuite.testcase.ExecutionTrace;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.evosuite.testsuite.SearchStatistics;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteChromosome;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteMinimizer;
import de.unisb.cs.st.evosuite.utils.HtmlAnalyzer;
import de.unisb.cs.st.evosuite.utils.Utils;

/**
 * @author Yury Pavlov
 * 
 */
public class Editor implements UserFeedback {

	public final Object lock = new Object();

	private final SearchStatistics statistics = SearchStatistics.getInstance();

	private final Set<Integer> suiteCoveredLines = new HashSet<Integer>();

	private ArrayList<TCTuple> tcTuples = new ArrayList<TCTuple>();

	private final TestSuiteChromosome testSuiteChr;

	private final GeneticAlgorithm gaInstance;

	private Iterable<String> sourceCode;

	public final TestEditorGUI sguiTE = new TestEditorGUI();

	public final SourceCodeGUI sguiSC = new SourceCodeGUI();

	// private TestParser testParser;

	private TCTuple currTCTuple;

	private final SEParser sep = new SEParser(this);

	private final Transactions transactions;

	private int prevSuiteCoverage;

	/**
	 * Create instance of manual editor.
	 * 
	 * @param sa
	 *            - SearchAlgorihm as parameter
	 */
	public Editor(GeneticAlgorithm ga) {
		gaInstance = ga;
		ga.pauseGlobalTimeStoppingCondition();
		testSuiteChr = (TestSuiteChromosome) gaInstance.getBestIndividual();

		TestSuiteMinimizer minimizer = new TestSuiteMinimizer(
				TestSuiteGenerator.getFitnessFactory());
		minimizer.minimize(testSuiteChr);

		List<TestCase> tests = testSuiteChr.getTests();
		HtmlAnalyzer html_analyzer = new HtmlAnalyzer();
		sourceCode = html_analyzer.getClassContent(Properties.TARGET_CLASS);

		Set<Integer> testCaseCoverega;
		for (TestCase testCase : tests) {
			testCaseCoverega = retrieveCoverage(testCase);
			tcTuples.add(new TCTuple(testCase, testCaseCoverega));
			suiteCoveredLines.addAll(testCaseCoverega);
		}

		nextTest();
		sguiSC.createWindow(this);
		sguiTE.createMainWindow(this);
		transactions = new Transactions(tcTuples, currTCTuple);
		// testParser = new TestParser(this);

		// see message from html_analyzer.getClassContent(...) to check this
		if (sourceCode.toString().equals(
				"[No source found for " + Properties.TARGET_CLASS + "]")) {
			File srcFile = chooseTargetFile(Properties.TARGET_CLASS);
			sourceCode = Utils.readFile(srcFile);
		}

		synchronized (lock) {
			while (sguiTE.mainFrame.isVisible())
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		// when work is done reset time
		ga.resumeGlobalTimeStoppingCondition();
	}

	/**
	 * Pars a testCase from Editor to EvoSuite's instructions and insert in
	 * EvoSuite's population. Create coverage for the new TestCase.
	 * 
	 * @param testSource
	 * @throws IOException
	 */
	public boolean saveTest(String testCode) {
		TestCase currentTestCase = currTCTuple.getTestCase();

		try {
			TestCase newTestCase;
			// newTestCase = testParser.parseTest(testCode);
			newTestCase = sep.parseTest(testCode);

			if (newTestCase != null) {
				// EvoSuite stuff
				TestCaseExecutor executor = TestCaseExecutor.getInstance();
				executor.execute(newTestCase);

				// If we change already existed testCase, remove old version
				testSuiteChr.deleteTest(currentTestCase);
				tcTuples.remove(currTCTuple);
				testSuiteChr.addTest(newTestCase);

				// MA stuff
				Set<Integer> testCaseCoverega = retrieveCoverage(newTestCase);
				suiteCoveredLines.addAll(testCaseCoverega);
				TCTuple newTestCaseTuple = new TCTuple(newTestCase,
						testCaseCoverega);
				currTCTuple = newTestCaseTuple;
				// testParser = new TestParser(this);
				tcTuples.add(newTestCaseTuple);
				updateCoverage();
				writeTransaction();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			showParseException(e.getMessage());
		}
		return false;
	}

	/**
	 * Return the number of TestCases in suite.
	 * 
	 * @return int
	 */
	public int getNumOfTestCases() {
		return tcTuples.size();
	}

	/**
	 * Return displacement currentTestCase in Suite.
	 * 
	 * @return int
	 */
	public int getNumOfCurrTest() {
		return tcTuples.indexOf(currTCTuple);
	}

	/**
	 * Return current testCase in editor (and GUI).
	 * 
	 * @return TestCaseTupel
	 */
	public TCTuple getCurrTCTup() {
		return currTCTuple;
	}

	/**
	 * 
	 */
	public String getCurrTCCode() {
		return currTCTuple.getTestCase().toCode();
	}

	/**
	 * Set currentTestCase to the next testCase.
	 */
	public void nextTest() {
		if (currTCTuple == null && tcTuples.size() > 0) {
			currTCTuple = tcTuples.get(0);
		} else if (currTCTuple != null && tcTuples.size() > 0) {

			int j = 0;
			for (int i = 0; i < tcTuples.size(); i++) {
				if (currTCTuple == tcTuples.get(i)) {
					if (i == tcTuples.size() - 1) {
						j = 0;
					} else {
						j = i + 1;
					}
				}
			}
			currTCTuple = tcTuples.get(j);
		} else {
			createNewTestCase();
		}
	}

	/**
	 * Set currentTestCase to previous testCase.
	 */
	public void prevTest() {
		if (currTCTuple == null && tcTuples.size() > 0) {
			currTCTuple = tcTuples.get(0);
		} else if (currTCTuple != null && tcTuples.size() > 0) {

			int j = 0;
			for (int i = 0; i < tcTuples.size(); i++) {
				if (currTCTuple == tcTuples.get(i)) {
					if (i == 0) {
						j = tcTuples.size() - 1;
					} else {
						j = i - 1;
					}
				}
			}
			currTCTuple = tcTuples.get(j);
		} else {
			createNewTestCase();
		}
	}

	/**
	 * Return source code of class.
	 * 
	 * @return Iterable of String
	 */
	public Iterable<String> getSourceCode() {
		return sourceCode;
	}

	/**
	 * Create new TestCase that can be insert in population. Without coverage
	 * information. Set current TestCase to this.
	 * 
	 */
	public void createNewTestCase() {
		TCTuple newTestCaseTuple = new TCTuple(new DefaultTestCase(),
				new HashSet<Integer>());
		currTCTuple = newTestCaseTuple;
	}

	/**
	 * Delete from testSuiteChromosome currentTestCase. Set current TestCase to
	 * the next.
	 */
	public void delCurrTC() {
		TestCase testCaseForDeleting = currTCTuple.getTestCase();
		testSuiteChr.deleteTest(testCaseForDeleting);
		tcTuples.remove(currTCTuple);
		updateCoverage();
		nextTest();
		writeTransaction();
	}

	/**
	 * Rebuild set of covered lines.
	 */
	private void updateCoverage() {
		suiteCoveredLines.clear();
		for (TCTuple tct : tcTuples) {
			suiteCoveredLines.addAll(tct.getCoverage());
		}

	}

	/**
	 * Retrieve the covered lines from EvoSuite (slow). Executed only 1 time at
	 * init.
	 * 
	 * @param testCase
	 * @return Set of Integers
	 */
	private Set<Integer> retrieveCoverage(TestCase testCase) {
		ExecutionTrace trace = statistics.executeTest(testCase,
				Properties.TARGET_CLASS);
		Set<Integer> result = statistics.getCoveredLines(trace,
				Properties.TARGET_CLASS);

		return result;
	}

	/**
	 * Return the coverage of the current TestCase.
	 * 
	 * @return Set of Integers
	 */
	public Set<Integer> getCurrCoverage() {
		return currTCTuple.getCoverage();
	}

	/**
	 * Return covered lines of the whole TestSuite.
	 * 
	 * @return Set of Integers
	 */
	public Set<Integer> getSuiteCoveredLines() {
		return suiteCoveredLines;
	}

	/**
	 * Return testSuit's coverage value.
	 * 
	 * @return int
	 */
	public int getSuiteCoveratgeVal() {
		gaInstance.getFitnessFunction().getFitness(testSuiteChr);
		int newValue = (int) (testSuiteChr.getCoverage() * 100);
		System.out.println("\nprevSuiteCoverage: " + prevSuiteCoverage);
		System.out.println("newValue: " + newValue);
		if (newValue < prevSuiteCoverage) {
			showWarning("New coverage is smaller!.");
		} else {
			prevSuiteCoverage = newValue;
		}
		return newValue;
	}

	@Override
	public void showParseException(String message) {
		JOptionPane.showMessageDialog(sguiTE.mainFrame, message,
				"Parsing error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public File chooseTargetFile(String fileName) {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Where is: " + fileName);
		int returnVal = fc.showOpenDialog(sguiTE.mainFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}

		return null;
	}

	public static String enterClassName(String className) {
		return JOptionPane.showInputDialog(null, "Where is class " + className
				+ "?", "Please enter full name", JOptionPane.QUESTION_MESSAGE);
	}

	public static String chooseClassName(String[] choices) {
		return (String) JOptionPane.showInputDialog(null, "Choose now...",
				"The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
				choices, choices[0]);
	}

	/**
	 * Show warning
	 * 
	 * @param message
	 */
	public static void showWarning(String message) {
		JOptionPane.showMessageDialog(null, message, "Warning",
				JOptionPane.ERROR_MESSAGE);
	}

	private void writeTransaction() {
		transactions.push(tcTuples, currTCTuple);
	}

	public void undo() {
		Record res = transactions.prev();
		updateAfterTransaction(res);
	}

	public void redo() {
		Record res = transactions.next();
		updateAfterTransaction(res);
	}

	public void reset() {
		Record res = transactions.reset();
		updateAfterTransaction(res);
	}

	private void updateAfterTransaction(Record res) {
		tcTuples = res.getTestCases();
		nextTest();
		ArrayList<TestCase> tests = new ArrayList<TestCase>();
		for (TCTuple tcTupel : tcTuples) {
			tests.add(tcTupel.getTestCase());
		}
		testSuiteChr.restoreTests(tests);
		updateCoverage();
		System.out.println(transactions);
	}

}
