package org.evosuite.idNaming;

import org.apache.commons.lang3.StringUtils;

public class AssertionExtraction
{
    private int total_assertions;

    private double av_assertions;
    
    private int additional_assertions;
    
    private int has_assertions;
    
    
    public static final String[] ASSERTION_TYPES = {"assertEquals",
    												"assertArrayEquals",
    												"assertFalse",
    												"assertTrue",
    												"assertNotNull",
    												"assertNull",
    												"assertNotSame",
    												"assertSame",
    												"assertThat",
    												"fail"};

    public AssertionExtraction(String s) {
        total_assertions = 0;
        av_assertions = 0.0;
        additional_assertions=0;
        has_assertions=0;

        this.setAssertion(s);
    }

    private void setAssertion(String test_code) {
        test_code = test_code.replaceAll("[\\p{Punct}]", " ");
        String line[] = test_code.split("\n");
		
        for (String assertionType : ASSERTION_TYPES) {
            total_assertions += StringUtils.countMatches(test_code, assertionType);
        }
        
        if (total_assertions>1){
        	additional_assertions=total_assertions-1;
        }
        if (total_assertions>0){
        	has_assertions=1;
        }

        av_assertions = (double) total_assertions / (double) line.length;
    }

    public int get_total_assertion() {
        return total_assertions;
    }

    public double get_av_assertions() {
        return av_assertions;
    }
    public int getAdditionalAssertions(){
    	return additional_assertions;
    }
    public int getHasAssertions(){
    	return has_assertions;
    }
    
}
