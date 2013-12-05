package org.evosuite.continuous.persistency;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.evosuite.utils.ReportGenerator.RuntimeVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;


/**
 * This class is used to read the CSV files generated by EvoSuite.
 * 
 * <p>
 * Note: as this class is used only for CTG, it assumes the
 * CSV contains only one single data row.
 * 
 * @author arcuri
 *
 */
public class CsvData {

	private static Logger logger = LoggerFactory.getLogger(CsvData.class);

	private String targetClass;	
	private double branchCoverage;	
	private int totalNumberOfStatements;	
	private int numberOfTests;	
	private int totalNumberOfFailures;
	private int durationInSeconds;
	private int configurationId;
	
	/**
	 * Apart from testing, shouldn't be allowed to instantiate
	 * this class directly
	 */
	protected CsvData(){		
	}

	/**
	 * Open an extract all data from the give csv file.
	 * 
	 * @param file the csv file, having 1 line of header, and 1 line of data
	 * @return <code>null</code> in case of any problem in reading the file
	 */
	public static CsvData openFile(File file){
		if(!file.getName().endsWith("csv")){
			logger.error("Not a csv file: "+file.getAbsolutePath());
			return null;
		}

		List<String[]> rows = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			rows = reader.readAll();
			reader.close();
		} catch (Exception e) {
			logger.error("Exception while parsing CSV file "+file.getAbsolutePath()+" , "+e.getMessage(),e);
			return null;
		}

		if(rows.size() != 2){
			logger.error("Cannot parse "+file.getAbsolutePath()+" as it has "+rows.size()+" rows");
			return null;
		}

		CsvData data = new CsvData();
		try{
			data.targetClass = getValue(rows,"TARGET_CLASS").trim();		
			data.configurationId = 0; //TODO. note: it has nothing to do with configuration_id, need refactoring			
			data.branchCoverage = Double.parseDouble(getValue(rows,RuntimeVariable.BranchCoverage.toString()));	
			data.totalNumberOfStatements = Integer.parseInt(getValue(rows,RuntimeVariable.Minimized_Size.toString()));
			data.durationInSeconds = Integer.parseInt(getValue(rows,RuntimeVariable.Total_Time.toString())) / 1000;
			data.numberOfTests = Integer.parseInt(getValue(rows,RuntimeVariable.NumberOfGeneratedTestCases.toString()));
			
			data.totalNumberOfFailures = 0; //TODO
		} catch(Exception e){
			logger.error("Error while parsing CSV file: "+e,e);
			return null; 
		}
		
		return data; 
	}

	private static String getValue(List<String[]> rows, String columnName){
		String[] names = rows.get(0);
		String[] values = rows.get(1);

		for(int i=0; i<names.length; i++){
			if(names[i].trim().equalsIgnoreCase(columnName.trim())){
				return values[i].trim();
			}
		}
		return null;
	}

	public String getTargetClass(){
		return targetClass; 
	}

	public double getBranchCoverage(){
		return branchCoverage; 
	}

	public int getTotalNumberOfStatements(){
		return totalNumberOfStatements; 
	}

	public int getNumberOfTests(){
		return numberOfTests; 
	}

	public int getTotalNumberOfFailures(){
		return totalNumberOfFailures; 
	}

	public int getDurationInSeconds(){
		return durationInSeconds; 
	}

	public int getConfigurationId() {
		return configurationId;
	}
}
