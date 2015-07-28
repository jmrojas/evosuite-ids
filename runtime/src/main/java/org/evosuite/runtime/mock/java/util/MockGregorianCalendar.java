package org.evosuite.runtime.mock.java.util;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.evosuite.runtime.mock.OverrideMock;

public class MockGregorianCalendar extends GregorianCalendar  implements OverrideMock{

	private static final long serialVersionUID = 4768096296715665262L;
	
	public MockGregorianCalendar() {
		this.setTimeInMillis(org.evosuite.runtime.System.currentTimeMillis());
	}
	
	public MockGregorianCalendar(int year, int month, int dayOfMonth) {
		super(year, month, dayOfMonth);
	}
	
	public MockGregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		super(year, month, dayOfMonth, hourOfDay, minute);
	}

	public MockGregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		super(year, month, dayOfMonth, hourOfDay, minute, second);
	}
	
	public MockGregorianCalendar(Locale aLocale) {
		super(aLocale);
		this.setTimeInMillis(org.evosuite.runtime.System.currentTimeMillis());
	}
	
	public MockGregorianCalendar(TimeZone zone) {
		super(zone);
		this.setTimeInMillis(org.evosuite.runtime.System.currentTimeMillis());
	}
	
	public MockGregorianCalendar(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
		this.setTimeInMillis(org.evosuite.runtime.System.currentTimeMillis());
	}
	
	// TODO: This code in Calendar seems to cause access to time
	//       but I don't understand how.
	//    public long getTimeInMillis() {
	//        if (!isTimeSet) {
	//            updateTime();
	//        }
	//        return time;
	//    }
    public long getTimeInMillis() {
        return time;
    }
}
