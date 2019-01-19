package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class Ascensor {
	
	public static final Ascensor instance = new Ascensor();
	
	//TODO CAN Ids
	private final CANSparkMax left = new CANSparkMax(Constants.LEFT_ELEVATOR_CAN, MotorType.kBrushless);
	private final CANSparkMax right = new CANSparkMax(Constants.RIGHT_ELEVATOR_CAN, MotorType.kBrushless);
	
	private final CANEncoder leftEncoder = left.getEncoder();
	private final CANEncoder rightEncoder = right.getEncoder();
	
	
	private boolean elevStep = false;
	
	private Ascensor() {
	}
	
	public static Ascensor getInstance() {
		return instance;
	}
	
	/**
	 * Sets mast up/down speed, automatically keeps mast within bounds
	 * @param speed
	 */
	public void setSpeed(double speed) {
		double currentHeight = getHeight();
		if ((speed < 0 && currentHeight <= Constants.ELEVATOR_LOWER_LIMIT)
				|| (speed > 0 && currentHeight >= Constants.ELEVATOR_UPPER_LIMIT)) {
			speed = 0.0;
		}
		if (currentHeight <= Constants.ELEVATOR_LOWER_LIMIT) reset();
		left.set(speed);
		right.set(speed);
	}
	
	/**
	 * Go to height based
	 * 
	 * @param height - height in inches
	 * 
	 * @return true, when complete and stopped
	 */
	public boolean toHeight(double height) {
		double delta = height - getHeight(); //Which way?
		double speed = 0; //Default
		elevStep = Math.abs(delta) <= Constants.ALLOWED_ELEVATOR_DEVIATION; //Are we there yet?
		if (!elevStep) {
			speed = delta / Constants.ELEVATOR_UPPER_LIMIT; 
		//Calculus, similar to Newton's Law of Cooling. Integrate this. 
		}

		setSpeed(speed);

		return !elevStep;
	}
	
	/**
	 * @return The mast height, in inches.
	 */
	public double getHeight() {
		return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2.0;
		//TODO Set encoder scaling, no method setDistancePerPulse
	}

	/**
	 * Resets elevator sensors. Currently does nothing
	 */
	private void reset() {
	}

}
