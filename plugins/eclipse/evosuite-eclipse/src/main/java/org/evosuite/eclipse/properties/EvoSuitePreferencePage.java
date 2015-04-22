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
package org.evosuite.eclipse.properties;

import java.io.IOException;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.evosuite.Properties;

public class EvoSuitePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final PreferenceStore PREFERENCE_STORE;
	
	public static final String MARKERS_ENABLED = "markersEnabled";
	public static final String RUNTIME = "runtime";
	public static final String ROAMTIME = "roamtime";
	public static final String UNCOVERED_MARKER = "uncovered";
	public static final String REMOVED_MARKER = "removed";
	public static final String AUTOMATIC_TEST_ON_SAVE = "automatic";
	static {
		PREFERENCE_STORE = new PreferenceStore("evosuite-quickfixes-properties");	
	}
	
	public EvoSuitePreferencePage(){
		super("CATS Generation Properties", FieldEditorPreferencePage.GRID);
		setPreferenceStore(PREFERENCE_STORE);
	}

	@Override
	public PreferenceStore getPreferenceStore() {
		return PREFERENCE_STORE;
	}

	@Override
	protected void createFieldEditors() {
		
		BooleanFieldEditor markersEnabled = new BooleanFieldEditor(MARKERS_ENABLED, "Enable Markers and Quick-fixes", getFieldEditorParent());
		addField(markersEnabled);
		
		IntegerFieldEditor runtime = new IntegerFieldEditor(RUNTIME, "Time for EvoSuite to improve code coverage (s)", getFieldEditorParent());
		runtime.setEmptyStringAllowed(false);
		runtime.setValidRange(0, Integer.MAX_VALUE);
		addField(runtime);
		
		IntegerFieldEditor roamtime = new IntegerFieldEditor(ROAMTIME, "Inactive time before other classes will be tested (s)", getFieldEditorParent());
		addField(roamtime);
		
		BooleanFieldEditor uncoveredLines = new BooleanFieldEditor(UNCOVERED_MARKER, "Show lines EvoSuite couldn't cover", getFieldEditorParent());
		addField(uncoveredLines);
		
		BooleanFieldEditor removedLines = new BooleanFieldEditor(REMOVED_MARKER, "Show lines the compiler may have removed", getFieldEditorParent());
		addField(removedLines);
		
		BooleanFieldEditor auto = new BooleanFieldEditor(AUTOMATIC_TEST_ON_SAVE, "Automatic test on save", getFieldEditorParent());
		addField(auto);
	}
	
	

	@Override
	protected void performDefaults() {
		getPreferenceStore().setToDefault(MARKERS_ENABLED);
		getPreferenceStore().setToDefault(RUNTIME);
		getPreferenceStore().setToDefault(ROAMTIME);
		getPreferenceStore().setToDefault(UNCOVERED_MARKER);
		getPreferenceStore().setToDefault(REMOVED_MARKER);
		
		super.performDefaults();
	}

	@Override
	public void init(IWorkbench arg0) {
		try {
			PreferenceStore prefStore = getPreferenceStore();
			prefStore.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getPreferenceStore().setDefault(MARKERS_ENABLED, false);
		getPreferenceStore().setDefault("runtime", 30);
		getPreferenceStore().setDefault("roamtime", 240);
		getPreferenceStore().setDefault("uncovered", false);
		getPreferenceStore().setDefault("removed", false);
		getPreferenceStore().setDefault("automatic", false);
		
		storeDefaults();
		//getPreferenceStore().
	}
	
	public void storeDefaults(){
		if (!getPreferenceStore().contains(MARKERS_ENABLED)){
			getPreferenceStore().setValue(MARKERS_ENABLED, false);
		}
		
		if (!getPreferenceStore().contains(RUNTIME)){
			getPreferenceStore().setValue(RUNTIME, 30);
		}
		
		if (!getPreferenceStore().contains(ROAMTIME)){
			getPreferenceStore().setValue(ROAMTIME, 240);
		}
		
		if (!getPreferenceStore().contains(UNCOVERED_MARKER)){
			getPreferenceStore().setValue(UNCOVERED_MARKER, false);
		}
		
		if (!getPreferenceStore().contains(REMOVED_MARKER)){
			getPreferenceStore().setValue(REMOVED_MARKER, false);
		}
		
		if (!getPreferenceStore().contains(AUTOMATIC_TEST_ON_SAVE)){
			getPreferenceStore().setValue(AUTOMATIC_TEST_ON_SAVE, false);
		}
		try {
			getPreferenceStore().save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		try {
			getPreferenceStore().save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}