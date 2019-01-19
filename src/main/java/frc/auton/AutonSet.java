package frc.auton;

/**
 * The class was created solely for serialization purposes, 
 * by using it class attribute (AutonSet.class)
 * @author Varun Chari
 *
 */
public class AutonSet extends java.util.ArrayList<AutonMode>{

	/**
	 * Appease the serialization gods
	 */
	private static final long serialVersionUID = -5992523654311439328L;
	
	@Override
	public boolean add(AutonMode e) {
		final int MAXSIZE = 64;// The switchbox only has 64 states
		return this.size() < MAXSIZE && super.add(e); 
	}
	
	
	
}
