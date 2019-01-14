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
	
	public void setSpeed(double speed) {
		left.set(speed);
		right.set(speed);
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
	}
	
	public boolean height(double height) {

		double current = encoder.getDistance();
		if ( Math.abs(height-current) <= Constants.ELEV_TOLERNCE ) {
			setSpeed(0);
			return true;
		}
		
		double speed = 0.6 * Math.signum(height-current);
		
		setSpeed(speed);
		
		return false;
	}

	private void reset() {
		encoder.reset();
	}

}
