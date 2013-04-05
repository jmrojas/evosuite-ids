/**
 * 
 */
package org.evosuite.primitives;

/**
 * @author Gordon Fraser
 * 
 */
public interface ConstantPool {

	/**
	 * <p>
	 * getRandomString
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getRandomString();

	/**
	 * <p>
	 * getRandomInt
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getRandomInt();

	/**
	 * <p>
	 * getRandomFloat
	 * </p>
	 * 
	 * @return a float.
	 */
	public float getRandomFloat();

	/**
	 * <p>
	 * getRandomDouble
	 * </p>
	 * 
	 * @return a double.
	 */
	public double getRandomDouble();

	/**
	 * <p>
	 * getRandomLong
	 * </p>
	 * 
	 * @return a long.
	 */
	public long getRandomLong();

	/**
	 * <p>
	 * add
	 * </p>
	 * 
	 * @param object
	 *            a {@link java.lang.Object} object.
	 */
	public void add(Object object);
}
