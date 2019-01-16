/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SerialPort.Port;

import com.kauailabs.navx.frc.AHRS;

/**
 * Add your docs here.
 */
public class DriveBase {

	public static final DriveBase instance = new DriveBase();

	// TODO SET CAN ADDRESSES (int deviceID)
	private final CANSparkMax leftFrontSpark = new CANSparkMax(1, MotorType.kBrushless);
	private final CANSparkMax rightFrontSpark = new CANSparkMax(2, MotorType.kBrushless);
	private final CANSparkMax leftBackSpark = new CANSparkMax(3, MotorType.kBrushless);
	private final CANSparkMax rightBackSpark = new CANSparkMax(4, MotorType.kBrushless);

	private final CANEncoder leftFrontEncoder = leftFrontSpark.getEncoder();
	private final CANEncoder rightFrontEncoder = leftFrontSpark.getEncoder();
	private final CANEncoder leftBackEncoder = leftFrontSpark.getEncoder();
	private final CANEncoder rightBackEncoder = leftFrontSpark.getEncoder();

	private boolean brake = false;
	private boolean drivingStep = false;
	private double offsetYaw = 0;
	private final AHRS navx = new AHRS(Port.kMXP);

	private DriveBase() {
		leftBackSpark.follow(leftFrontSpark);
		rightBackSpark.follow(rightFrontSpark);

	}

	public static DriveBase getInstance() {
		return instance;
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftFrontSpark.set(leftSpeed);
		rightFrontSpark.set(rightSpeed);
	}

	/**
	 * Sets left and right drives to same speed
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		leftFrontSpark.set(speed);
		rightFrontSpark.set(speed);
	}

	/**
	 * Sets whether the brakes are on (true) or off (false)
	 * 
	 * @param doBrake Brake state
	 */
	public void setBrake(boolean doBrake) {
		brake = doBrake;
		// leftbrake.set(brake ? Constants.LEFT_BRAKES_ON : Constants.LEFT_BRAKES_OFF);
		// rightbrake.set(brake ? Constants.RIGHT_BRAKES_ON :
		// Constants.RIGHT_BRAKES_OFF);
		setSpeed(0.0);
	}

	public boolean brake() {
		return brake;
	}

	/**
	 * Have the robot drive to a specific distance at a specified speed. Uses the
	 * sign of distance, not the speed
	 * 
	 * @param distance - The distance (in inches) you would like the robot to drive
	 *                 to.
	 * @param speed    - The speed at which you would like the robot to drive.
	 * @return True, when complete.
	 */

	public boolean driveStraight(double distance, double speed, boolean slowDown) {
		if (!drivingStep) {
			setBrake(false);
			resetSensors();
			drivingStep = true;
		}
		speed = Math.copySign(speed, distance);
		double leftSpeed = speed;
		double rightSpeed = speed;
		if (Math.abs(encoderDistance() - distance) <=
		// Keeping expected and observed values within the same absolute value
		// because if they differ in sign, that's more than a coding problem
		Constants.DRIVE_TOLERANCE) {
			setSpeed(0.0);
			return !(drivingStep = false);
		} else if (slowDown) { // If within slow range, set to slow speed
			if (encoderDistance() >= distance - Constants.DRIVE_STRAIGHT_SLOW_RANGE)
				slow(speed > 0.0);
			else if (encoderDistance() >= distance + Constants.DRIVE_TOLERANCE)
				slow(speed < 0.0);
		} else if (Math.abs(getAngle()) > Constants.VEER_TOLERANCE) { // Adjust speeds in case of veering
			if (getAngle() > 0) { // Too far clockwise
				if (distance > 0)
					leftSpeed -= Constants.DRIVE_SPEED_CORRECTION;
				else
					rightSpeed += Constants.DRIVE_SPEED_CORRECTION;
			} else { // Too far anti-clockwise
				if (distance > 0)
					rightSpeed -= Constants.DRIVE_SPEED_CORRECTION;
				else
					leftSpeed += Constants.DRIVE_SPEED_CORRECTION;
			}
			setSpeed(leftSpeed, rightSpeed);
		}
		return (!drivingStep);
	}

	public void slow(boolean slowDown) {
		if (slowDown) {
			setSpeed(Constants.SLOW_SPEED_STRONG, Constants.SLOW_SPEED_WEAK);
		} else {
			setSpeed(-Constants.SLOW_SPEED_WEAK, -Constants.SLOW_SPEED_STRONG);
		}
	}

	/**
	 * Have the robot turn to a specific angle at a specified speed. Uses the sign
	 * of the angle
	 * 
	 * @param angle - The angle (in degrees) you would like to turn the robot to.
	 * @param speed - The speed at which you would like the robot to turn.
	 * @return Whether the turn has completed
	 */

	public boolean turn(double angle, double speed) {
		if (!drivingStep) {
			setBrake(false);
			resetSensors();
			drivingStep = true;
		}
		speed = Math.copySign(speed, angle);
		setSpeed(speed, -speed);
		if (Math.abs(getAngle() - angle) <= Constants.TURN_TOLERANCE) {
			drivingStep = false;
			setSpeed(0.0);
		}

		return (!drivingStep);
	}
	/**
	 * Resets drivebase sensors.
	 */
	private void resetSensors() {
		navx.zeroYaw();
	}

	private double getAngle() {
		return navx.getYaw() - offsetYaw;
	}

	private double encoderDistance() {
		//double navxD = Math.hypot(navx.getDisplacementX(), navx.getDisplacementY()) / 39.3701;
		return (
				leftFrontEncoder.getPosition() 
				+ rightFrontEncoder.getPosition()
				+ leftBackEncoder.getPosition() // Are these extra Encoder
				+rightBackEncoder.getPosition() // checks necessary?
				) 
				/ 4.0;
		
	}

}
