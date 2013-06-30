package org.evosuite.continuous.persistency;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.evosuite.continuous.project.ProjectStaticData;
import org.evosuite.utils.Utils;
import org.evosuite.xsd.ProjectInfo;
import org.evosuite.xsd.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to store all CTG info on disk
 * 
 * @author arcuri
 *
 */
public class StorageManager {

	private static Logger logger = LoggerFactory.getLogger(StorageManager.class);

	public static final String junitSuffix = "ContinuousEvoSuiteTest"; 
	
	private final String rootFolderName;

	private final String projectFileName = "project_info.xml";

	private File tmpFolder;
	private File tmpLogs;
	private File tmpReports;
	private File tmpTests;

	private File testsFolder;
	
	public StorageManager(String rootFolderName) {
		super();
		this.rootFolderName = rootFolderName;
		this.tmpFolder = null;
	}

	public StorageManager(){
		this(".continuous_evosuite");
	}

	/**
	 * Open connection to Storage Manager
	 * 
	 * @return
	 */
	public boolean openForWriting(){

		/*
		 * Note: here we just make sure we can write on disk
		 */

		File root = new File(rootFolderName);
		if(root.exists()){
			if(root.isDirectory()){
				if(root.canWrite()){
					return true;
				} else {
					logger.error("Cannot write in "+root.getAbsolutePath());
					return false;
				}
			} else {
				//it exists but not a folder...
				boolean deleted = root.delete();
				if(!deleted){
					logger.error("Folder "+root+" is a file, and we cannot delete it");
					return false;
				} else {
					// same as "else" of !exist
				}
			}
		}

		boolean created = root.mkdir();
		if(!created){
			logger.error("Failed to mkdir "+root.getAbsolutePath());
			return false;
		}

		testsFolder = new File(root.getAbsolutePath()+File.separator+"evosuite-tests");
		if(!testsFolder.exists()){
			created = testsFolder.mkdirs();
			if(created){
				return false;
			}
		}
		
		return true;		
	}

	/**
	 * Create a new tmp folder for this CTG session
	 * 
	 * @return
	 */
	public boolean createNewTmpFolders(){
		String tmpPath = rootFolderName+"/tmp/";
		Date now = new Date();
		String time = DateFormatUtils.format(
				now, "yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
		File tmp = new File(tmpPath+"/tmp_"+time);
		boolean created = tmp.mkdirs();

		if(created){
			tmpFolder = tmp;
		} else {
			tmpFolder = null;
			return false;
		}

		//if we created the "tmp" folder, then it should be fine to create new folders in it

		tmpLogs = new File(tmpFolder.getAbsolutePath()+"/logs");
		tmpLogs.mkdirs();
		tmpReports = new File(tmpFolder.getAbsolutePath()+"/reports");
		tmpReports.mkdirs();
		tmpTests = new File(tmpFolder.getAbsolutePath()+"/tests");
		tmpTests.mkdirs();

		return true;
	}


	/**
	 * Delete all CTG files 
	 * @return
	 */
	public boolean clean(){
		try {
			FileUtils.deleteDirectory(new File(rootFolderName));
		} catch (IOException e) {
			logger.error("Cannot delete folder "+rootFolderName+": "+e,e);
			return false;
		}
		return true;
	}

	public static class TestsOnDisk{
		public final File testSuite;
		public final String cut;
		public final CsvData csvData;
		
		
		public TestsOnDisk(File testSuite, CsvData csvData) {
			super();
			this.testSuite = testSuite;
			this.csvData = csvData;
			this.cut = csvData.getTargetClass();
		}
		
		public TestsOnDisk(File testSuite, File csvFile, String cut) {
			super();
			this.testSuite = testSuite;
			this.cut = cut;
			csvData = CsvData.openFile(csvFile);
		}
		
		public boolean isValid(){
			return testSuite!=null && testSuite.exists() &&
					cut!=null && !cut.isEmpty() &&
					csvData!=null && 
					cut.equals(csvData.getTargetClass());
		}
	}
	
	/**
	 * Compare the results of this CTG run with what was in
	 * the database. Keep/update the best results. 
	 * 
	 * @param data
	 * @return
	 */
	public String mergeAndCommitChanges(ProjectStaticData current){

		ProjectInfo db = getDatabaseProjectInfo();
		String info = removeNoMoreExistentData(db,current);

		/*
		 * Check what test cases have been actually generated
		 * in this CTG run
		 */
		List<TestsOnDisk> suites = gatherGeneratedTestsOnDisk();
		info += "\nNew test suites: "+suites.size();
		
		int better = 0;
		for(TestsOnDisk suite : suites){
			if(isBetterThanOldOne(suite,db)){
				updateDatabase(suite,db);
				better++;
			}
		}
		info += "Better test suites: "+better;
		
		updateProjectStatistics(db,current);
		commitDatabase(db);

		return info;
	}

	/**
	 * Not only we need the generated JUnit files, but also the statistics
	 * on their execution.
	 * Note: in theory we could re-execute the test cases to extract/recalculate
	 * those statistics, but it would be pretty inefficient
	 * 
	 * @return
	 */
	private List<TestsOnDisk> gatherGeneratedTestsOnDisk(){
		
		List<TestsOnDisk> list = new LinkedList<TestsOnDisk>();
		List<File> generatedTests = Utils.getAllFilesInSubFolder(tmpTests.getAbsolutePath(), ".java");
		List<File> generatedReports = Utils.getAllFilesInSubFolder(tmpReports.getAbsolutePath(), ".csv");
		
		/*
		 * Key -> name of CUT
		 * Value -> data extracted from CSV file 
		 * 
		 * We use a map, otherwise we could have 2 inner loops going potentially on thousands
		 * of classes, ie, O(n^2) complexity
		 */
		Map<String,CsvData> reports = new LinkedHashMap<String,CsvData>();
		for(File file : generatedReports){
			CsvData data = CsvData.openFile(file);
			if(data==null){
				logger.warn("Cannot process "+file.getAbsolutePath());
			} else {
				reports.put(data.getTargetClass(), data);
			}
		}
		
		/*
		 * Try to extract info for each generated JUnit test suite
		 */
		for(File test : generatedTests){
			
			String testName = extractClassName(tmpTests,test);
			String cut = testName.substring(0, testName.length() - junitSuffix.length());
						
			CsvData data = reports.get(cut); 
			
			if(data==null){
				logger.warn("No CSV file for CUT "+cut+" with test suite at "+test.getAbsolutePath());
				continue;
			}
			
			TestsOnDisk info = new TestsOnDisk(test, data);
			if(info.isValid()){
				list.add(info);
			} else {
				logger.warn("Invalid info for "+test.getAbsolutePath());
			}
		}
		
		return list; 
	}
	
	/**
	 * Example: </br>
	 * base   = /some/where/in/file/system  </br>
	 * target = /some/where/in/file/system/com/name/of/a/package/AClass.java  </br>
	 * </br>
	 * We want "com.name.of.a.package.AClass" as a result
	 * 
	 */
	private String extractClassName(File base, File target){		
		int len = base.getAbsolutePath().length();
		String path = target.getAbsolutePath(); 
		String name = path.substring(len,path.length()-".java".length());
		name = name.replaceAll(File.separator,".");
		return name;
	}
	
	
	private void commitDatabase(ProjectInfo db) {

		StringWriter writer = null;
		try{
			writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(ProjectInfo.class);            
			Marshaller m = context.createMarshaller();
			m.marshal(db, writer);
		} catch(Exception e){
			logger.error("Failed to create XML representation: "+e.getMessage(),e);
		}

		String xml = writer.toString();
		
		/*
		 * TODO: to be safe, we should first write to tmp file, delete original, and then
		 * rename the tmp
		 */
		File current = new File(rootFolderName + File.separator + projectFileName);
		current.delete();
		try {
			FileUtils.write(current, xml);
		} catch (IOException e) {
			logger.error("Failed to write to database: "+e.getMessage(),e);
		}
	}

	private void updateProjectStatistics(ProjectInfo db, ProjectStaticData current) {

		db.setTotalNumberOfClasses(BigInteger.valueOf(current.getTotalNumberOfClasses()));
		int n = current.getTotalNumberOfTestableCUTs();
		db.setTotalNumberOfTestableClasses(BigInteger.valueOf(n));

		double coverage = 0d;
		for(TestSuite suite : db.getGeneratedTestSuites()){
			coverage += suite.getBranchCoverage();
		}

		coverage = coverage / (double) n;
		db.setAverageBranchCoverage(coverage);
	}

	/**
	 * Not only modify the state of <code>db</code>, but
	 * also copy/replace new test cases on file disk
	 * 
	 * @param ondisk
	 * @param db
	 */
	private void updateDatabase(TestsOnDisk ondisk, ProjectInfo db) {

		assert ondisk.isValid();
		
		TestSuite suite = new TestSuite();
		CsvData csv = ondisk.csvData;
		suite.setBranchCoverage(csv.getBranchCoverage());
		suite.setFullNameOfTargetClass(csv.getTargetClass());
		suite.setNumberOfTests(BigInteger.valueOf(csv.getNumberOfTests()));
		suite.setTotalNumberOfStatements(BigInteger.valueOf(csv.getTotalNumberOfStatements()));
		
		TestSuite old = null;
		Iterator<TestSuite> iter = db.getGeneratedTestSuites().iterator();
		while(iter.hasNext()){
			TestSuite tmp = iter.next();
			if(tmp.getFullNameOfTargetClass().equals(csv.getTargetClass())){
				old = tmp;
				iter.remove();
				break;
			}
		}

		int oldTotalEffort = 0;
		int oldEffortFromModification = 0;
		
		if(old != null){
			oldTotalEffort = old.getTotalEffortInSeconds().intValue();
			oldEffortFromModification = old.getEffortFromLastModificationInSeconds().intValue();
		}
		
		int duration = oldTotalEffort+csv.getDurationInSeconds();
		suite.setEffortFromLastModificationInSeconds(BigInteger.valueOf(oldEffortFromModification+duration));
		suite.setTotalEffortInSeconds(BigInteger.valueOf(oldTotalEffort+duration));

		//TODO need also to update actual tests
		String testName = extractClassName(tmpTests,ondisk.testSuite);
		suite.setFullNameOfTestSuite(testName); 
		
		db.getGeneratedTestSuites().add(suite);
		
		/*
		 * TODO to properly update failure data, we will first need
		 * to change how we output such info in EvoSuite (likely
		 * we will need something more than statistics.csv)
		 */
	}

	private boolean isBetterThanOldOne(TestsOnDisk suite, ProjectInfo db) {
		if(suite.csvData == null) {
			// no data available
			return false; 
		}
		
		TestSuite old = null;
		for(TestSuite tmp : db.getGeneratedTestSuites()){
			if(tmp.getFullNameOfTargetClass().equals(suite.cut)){
				old = tmp;
				break;
			}
		}
		
		if(old == null){
			// there is no old test suite, so accept new one
			return true;
		}

		double oldCov = old.getBranchCoverage();
		double newCov = suite.csvData.getBranchCoverage();
		double covDif = Math.abs(newCov - oldCov); 
		
		if(covDif > 0.0001){
			/*
			 * this check is to avoid issues with double truncation 
			 */
			return newCov > oldCov;
		}
		
		//here coverage seems the same, so look at failures
		int oldFail = old.getFailures().size();
		int newFail = suite.csvData.getTotalNumberOfFailures();
		if(newFail != oldFail){
			return newFail > oldFail;
		}
		
		//TODO: here we could check other things, like mutation score
		
		//everything seems same, so look at size 
		int oldSize = old.getTotalNumberOfStatements().intValue();
		int newSize = suite.csvData.getTotalNumberOfStatements();
		
		return newSize < oldSize; 
	}

	/**
	 * Some classes could had been removed/renamed.
	 * So just delete all info regarding them
	 * 
	 * @param data
	 */
	private String removeNoMoreExistentData(ProjectInfo db,
			ProjectStaticData current) {

		int removed = 0;
		Iterator<TestSuite> iter = db.getGeneratedTestSuites().iterator();
		while(iter.hasNext()){
			TestSuite suite = iter.next();
			String cut = suite.getFullNameOfTargetClass();
			if(! current.containsClass(cut)){
				iter.remove();
				removed++;
			}
			
			//TODO remove test suite
		}
		
		return "Removed test suites: "+removed; 
	}


	/**
	 * Get current representation of the test cases in the database
	 * 
	 * @return
	 */
	public ProjectInfo getDatabaseProjectInfo(){

		File current = new File(rootFolderName + File.separator + projectFileName);
		if(!current.exists()){
			return null;
		}

		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(ProjectInfo.class);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(
					new File(ClassLoader.getSystemResource("/xsd/ctg_project_report.xsd").toURI())));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setSchema(schema);
			ProjectInfo project = (ProjectInfo) jaxbUnmarshaller.unmarshal(current);
			return project;
		} catch(Exception e){
			logger.error("Error in reading "+current.getAbsolutePath()+". "+e,e);
			return null;
		}
	}

	public File getTmpFolder() {
		return tmpFolder;
	}

	public File getTmpLogs() {
		return tmpLogs;
	}

	public File getTmpReports() {
		return tmpReports;
	}

	public File getTmpTests() {
		return tmpTests;
	}
}
