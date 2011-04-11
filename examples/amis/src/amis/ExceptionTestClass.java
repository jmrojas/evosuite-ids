package amis;

/**
 * @author Andre Mis
 *
 */
public class ExceptionTestClass {

	private int field;
	
	public ExceptionTestClass(int anInt) {
		field = anInt;
	}
	
	public void setField(int val) {
		field = val;
	}
	
	public int getField() {
		return field;
	}
	
	public int returnInTryCatch(int val) {
		try {
			return val;
		} catch(Exception e) {
			return val;
		}
	}
	
	public void illegalArgumentThrower(int val)  throws Exception {
		if(val<0)
			throw new IllegalArgumentException("not >=0");
		field = val;
	}
	
	public void defThenThrow(int val)  throws Exception {
		field=val;
		throw new IllegalStateException("error");
	}
	
	public void useThenThrow()  throws Exception {
		if(field%2==0)
			getField();
		throw new IllegalStateException("error");
	}

	public void throwInIf()  throws Exception {
		if(field%2 == 0)
			throw new IllegalStateException("error");
		
		if(field+3 < 2) {
			field++;
			throw new Exception("error");
		}
	}

	public void tryCatchDef() {
		try {
			throw new Exception("");
		}catch(Exception e) { 
			field = e.toString().length();
		}
	}
	
	public void tryCatchUse() {
		try {
			throw new Exception("");
		}catch(Exception e) { 
			setField(field);
		}
	}
	
	public void ifTryCatchDef() {
		if(field%2==0)
			try {
				throw new Exception("");
			}catch(Exception e) { 
				field = e.toString().length();
			}
	}
	
	public void ifTryCatchUse() {
		if(field%2==0)
			try {
				throw new Exception("");
			}catch(Exception e) { 
				setField(field);
			}
	}
	
	public void alwaysThrowIllegalState() throws IllegalStateException {
		throw new IllegalStateException("error");
	}

	public void alwaysThrowError() throws Error {
		throw new Error("error");
	}
	
	public void alwaysThrowException()  throws Exception{
		throw new Exception("error");
	}


	
}
