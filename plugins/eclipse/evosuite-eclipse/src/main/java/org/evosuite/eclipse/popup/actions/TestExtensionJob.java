package org.evosuite.eclipse.popup.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.evosuite.Properties;
import org.evosuite.eclipse.Activator;
import org.evosuite.eclipse.properties.EvoSuitePropertyPage;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.utils.Utils;

public class TestExtensionJob extends TestGenerationJob {

	private File tempDir;
	private ArrayList<String> newTests;
	
	public TestExtensionJob(Shell shell, final IResource target, String targetClass,
	        String testClass) {
		super(shell, target, targetClass, testClass);
		newTests = new ArrayList<String>();
		writeAllMarkers = false;
		try {
			tempDir = setupTempDir();
		} catch (IOException e) {
			e.printStackTrace();
			tempDir = new File("/tmp");
		}

	}

	protected CompilationUnit parseJavaFile(String unitName, String fileName)
	        throws IOException {
		String fileContents = Utils.readFileToString(fileName);
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setStatementsRecovery(true);
		
		Map<String, String> COMPILER_OPTIONS = new HashMap<String, String>(JavaCore.getOptions());
		COMPILER_OPTIONS.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		COMPILER_OPTIONS.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		COMPILER_OPTIONS.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		
		//parser.setResolveBindings(true);
		//parser.setBindingsRecovery(true);
		parser.setUnitName(unitName);
		String[] encodings = { ENCODING };
		String[] classpaths = { classPath };
		String[] sources = { new File(suiteClass).getParent() };
		parser.setEnvironment(classpaths, sources, encodings, true);
		parser.setSource(fileContents.toCharArray());		
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		Set<String> problems = new HashSet<String>();
		for (IProblem problem : compilationUnit.getProblems()) {
			
			problems.add(problem.getSourceLineNumber() +": "+problem.toString());
		}
		if (!problems.isEmpty()) {
			System.out.println("Got " + problems.size()
			        + " problems compiling the source file: ");
			for (String problem : problems) {
				System.out.println(problem);
			}
		}
		return compilationUnit;
	}

	protected String getTestClassName() {
		String path = tempDir.getAbsolutePath();
		path += File.separator;
		path += targetClass.replace(".", "/");
		int pos = path.lastIndexOf(File.separator);
		// path = path.substring(0, pos+1) + "Test" + path.substring(pos+1);
		path += Properties.JUNIT_SUFFIX;
		path += ".java";
		return path;
	}

	protected List<MethodDeclaration> getTestContent(String fileName) throws IOException {
		System.out.println("Trying to parse file: "+fileName);

		CompilationUnit compilationUnit = parseJavaFile(suiteClass, fileName);
		MethodExtractingVisitor visitor = new MethodExtractingVisitor();
		compilationUnit.accept(visitor);
		return visitor.getMethods();
	}

	protected File setupTempDir() throws IOException {
		File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "
			        + temp.getAbsolutePath());
		}
		return temp;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.eclipse.popup.actions.TestGenerationJob#getAdditionalParameters()
	 */
	@Override
	public List<String> getAdditionalParameters() {

		List<String> parameters = new ArrayList<String>();
		parameters.add("-Dtest_dir=" + tempDir.getAbsolutePath());
		parameters.add("-Djunit_extend="+suiteClass);
		parameters.add("-Dselected_junit="+suiteClass);
		parameters.add("-Dtest_factory=JUnit");
		System.out.println("Providing output dir: "+tempDir.getAbsolutePath());
		return parameters;
	}
	
	private boolean hasMethod(IType classType, String methodName) throws JavaModelException {
		for(IMethod method : classType.getMethods()) {
			if(method.getElementName().equals(methodName))
				return true;
		}
		return false;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IStatus status = super.run(monitor);
		
		newTests.clear();
		IJavaElement element = JavaCore.create(target);
		if (element.getElementType() == IJavaElement.COMPILATION_UNIT) {
			ICompilationUnit compilationUnit = (ICompilationUnit) element;
			CodeFormatter formatter = ToolFactory.createCodeFormatter(null);

			try {
				IType classType = compilationUnit.getTypes()[0];
				// new tests
				List<MethodDeclaration> newMethods = getTestContent(getTestClassName());
				for(MethodDeclaration newMethod : newMethods) {
					
					if(hasMethod(classType, newMethod.getName().toString())) {
						if(newMethod.getName().toString().equals("initEvoSuiteFramework"))
							continue;
						
						System.out.println("Test suite already contains method: " + newMethod.getName());
						int num = 1;
						newMethod.setName(newMethod.getAST().newSimpleName(newMethod.getName().toString()+"_"+num));
						while(hasMethod(classType, newMethod.getName().toString())) {
							num += 1;
							String name = newMethod.getName().toString();
							newMethod.setName(newMethod.getAST().newSimpleName(name.substring(0, name.length() - 2)+"_"+num));
						}
					}
					String testContent = newMethod.toString();
						
					IMethod methodToAdd = classType.createMethod(testContent, null, false,
								new NullProgressMonitor());
					ISourceRange range = methodToAdd.getSourceRange();
					TextEdit indent_edit = formatter.format(CodeFormatter.K_COMPILATION_UNIT, 
						    classType.getCompilationUnit().getSource(), range.getOffset(), range.getLength(), 0, null);
					classType.getCompilationUnit().applyTextEdit(indent_edit, null);
					newTests.add(newMethod.getName().toString());
				}
				classType.getCompilationUnit().commitWorkingCopy(false, null);
				// write markers
				writeMarkersExtendedTestSuite();
				
			} catch (JavaModelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	private void writeMarkersExtendedTestSuite() {
		System.out.println("**********  Writing markers in test suite" + suiteClass);

		String testClassFileName = getSuiteFileName(suiteClass);

		final IFile fileTestClass = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(testClassFileName));

		String fileContents = readFileToString(testClassFileName);
		
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setStatementsRecovery(true);
		
		Map<String, String> COMPILER_OPTIONS = new HashMap<String, String>(JavaCore.getOptions());
		 COMPILER_OPTIONS.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7);
		    COMPILER_OPTIONS.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
		    COMPILER_OPTIONS.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		
		parser.setUnitName(suiteClass);
		String[] encodings = { ENCODING };
		String[] classpaths = { classPath };
		String[] sources = { new File(testClassFileName).getParent() };
		parser.setEnvironment(classpaths, sources, encodings, true);
		parser.setSource(fileContents.toCharArray());
		
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		MethodExtractingVisitor visitor = new MethodExtractingVisitor();
		compilationUnit.accept(visitor);
		List<MethodDeclaration> methods = visitor.getMethods();
		
		Display.getDefault().syncExec(new Runnable() {
		    @Override
		    public void run() {
		        IWorkbenchWindow iw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		        IWorkbenchPage page = iw.getActivePage();
		        try {
					IDE.openEditor(page, fileTestClass, true);
				} catch (PartInitException e1) {
					System.out.println("Could not open test suite");
					e1.printStackTrace();
				}
		    }
		});
		if (Activator.markersEnabled()) {
			for (MethodDeclaration m : methods) {
				if (newTests.contains(m.getName().toString())) {
					int lineNumber = compilationUnit.getLineNumber(m.getStartPosition());
					try {
						IMarker mark = fileTestClass.createMarker("EvoSuiteQuickFixes.newtestmarker");
						mark.setAttribute(IMarker.MESSAGE, "This test case needs to be verified.");
						mark.setAttribute(IMarker.LINE_NUMBER, lineNumber);
						mark.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
						mark.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
						mark.setAttribute(IMarker.LOCATION, fileTestClass.getName());
						mark.setAttribute(IMarker.CHAR_START, m.getStartPosition());
						mark.setAttribute(IMarker.CHAR_END, m.getStartPosition() + 1);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
