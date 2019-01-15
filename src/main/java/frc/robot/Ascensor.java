package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.CANSparkMax;

public class Ascensor {
	
	//TODO CAN Ids
	CANSparkMax left = new CANSparkMax(Constants.LEFT_ELEVATOR_PWM, MotorType.kBrushless);
	CANSparkMax right = new CANSparkMax(Constants.RIGHT_ELEVATOR_PWM, MotorType.kBrushless);
	
	private final Encoder encoder = new Encoder(Constants.ELEVATOR_ENCODER_PWM_A, Constants.ELEVATOR_ENCODER_PWM_B,
			Constants.ELEVATOR_ENCODER_INVERTED);
	
	private boolean elevStep = false;
	
	private Ascensor() {
	}
	
	/**
	 * Sets mast up/down speed, automatically keeps mast within bounds
	 * @param speed
	 */
	public void setSpeed(double speed) {
		double currentHeight = height();
		if ( ( speed < 0 && currentHeight <= Constants.ELEVATOR_BOTTOM )
				|| ( speed > 0 && currentHeight >= Constants.ELEVATOR_TOP ) )
			speed = 0;
		if (currentHeight <= Constants.ELEVATOR_BOTTOM) reset();
		left.set(speed);
		right.set(speed);
	}
	
	/**
	 * Function to go to height based
	 * 
	 * @param height - height in inches
	 * 
	 * @return true, when complete and stopped
	 */
	public boolean height(double height) {
		double delta = height - height(); //Which way?
		double speed = 0; //Default
		elevStep = Math.abs(delta) <= Constants.ELEV_TOLERANCE; //Are we there yet?
		if (!elevStep)
			speed = delta / Constants.ELEVATOR_TOP; 
		//Calculus, similar to Newton's Law of Cooling. Integrate this. 

		setSpeed(speed);

		return !elevStep;
	}
	
	/**
	 * @return The mast height, in inches.
	 */
	public double height() {
		double current = height();
		return current;
		/*return current > Constants.ELEVATOR_HEIGHT_THRESHOLD
				? 2 * current - Constants.ELEVATOR_HEIGHT_THRESHOLD
				: current ;*/ //No idea how this works
	}

	private void reset() {
		encoder.reset();
	}

}
