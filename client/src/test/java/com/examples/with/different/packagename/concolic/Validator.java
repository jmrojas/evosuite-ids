package com.examples.with.different.packagename.concolic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Defines the structure required to be a validation class
 * @author Tom Nolan (tom.nolan@sinbadsoftware.com)
 * @version 1.0
 * @since 28/05/2003 
 */

public abstract class Validator implements  java.io.Serializable
{
    
    /** Defines that the FieldParams must be input */    
    public static final boolean MANDATORY = true;
    /** Defines that the FieldParams doesn't have to be input but 
     * any input will be hopefully validated
     */    
    public static final boolean NONMANDATORY = false;
    
    private static final String PROP_MANDATORY_PROPERTY = "Mandatory";
    private static final String PROP_ERRORTEXT_PROPERTY = "ErrorText";
        
    /** PropertyChange Support for use with
     * XBuilder GUI
     */
    protected PropertyChangeSupport propertySupport;
    
    
    
    private boolean blnMandatory = false;    
    private String errorText = ""; // the standard error text for the validator
    
    /** Checks whether the input is valid
     * @param str The input
     * @return True if the input is valid
     */    
    public abstract boolean isValid(String str);
  
    /**
     * whether validator is MANDATORY or NONMANDATORY
     * Retrieves the mandatory value
     * @return Whether this validator is MANDATORY or not
     */    
    public boolean getMandatory() 
    {
        return blnMandatory;
    }    
  
    /** Modifies the value of the Mandatory field
     * @param blnMand Whether or not Mandatory
     */    
     public void setMandatory(boolean blnMand) 
    {
      
        final boolean oldValue = getMandatory();
        blnMandatory = blnMand;
        propertySupport.firePropertyChange(PROP_MANDATORY_PROPERTY, 
                                           oldValue, 
                                           blnMand);  
      
     }    
   
     
     /** Allows the setting of the error text
      * for a validator
      * @param str The new error text
      */     
    public void setErrorText(String str)
    {
          String oldValue = getErrorText();
          errorText = str;   
          propertySupport.firePropertyChange(PROP_ERRORTEXT_PROPERTY, 
                                             oldValue, 
                                             str);  
    }
    
    /** The error message for this validator
     * @return The error message
     */    
    public String getErrorText()
    {
         return errorText;
    }
    
    /** returns the class name
     * @return the classname
     */    
    public String toString()
    {
     return getClass().getName();   
    }
    
    
    /** Property Change support for XBuilder
     * @param listener The PropertyChangeListener
     */    
     public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
     /** Property Change support for XBuilder
      * @param listener The PropertyChangeListener
      */     
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
