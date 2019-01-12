/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Servo;

/**
 * Add your docs here.
 */
public class DriveBase {


    public static final DriveBase instance = new DriveBase();

    //TODO SET CAN ADDRESSES (int deviceID)
    private final CANSparkMax leftFrontSpark = new  CANSparkMax(1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightFrontSpark = new  CANSparkMax(2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax leftBackSpark = new  CANSparkMax(3, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightBackSpark = new  CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private boolean brake = false;
    private boolean drivingStep = false;
    //private AHRS navx;
  
    private DriveBase() {
        // Master-Slave?
        //leftBackSpark.follow(leftFrontSpark);
        //rightBackSpark.follow(rightFrontSpark);

    }

    public static DriveBase getInstance() {
        return instance;
    }

    public void setSpeed(double leftSpeed, double rightSpeed) {
        leftFrontSpark.set(leftSpeed);
        leftBackSpark.set(leftSpeed);
        rightBackSpark.set(rightSpeed);
        rightFrontSpark.set(rightSpeed);
    }

    /**
     * Sets left and right drives to same speed 
     * @param speed
     */
    public void setSpeed(double speed) {
        leftFrontSpark.set(speed);
        leftBackSpark.set(speed);
        rightBackSpark.set(speed);
        rightFrontSpark.set(speed);
    }

    /**
     * Sets whether the brakes are on (true) or off (false)
     * @param doBrake Brake state
     */
    public void brake(boolean doBrake) {
        brake = doBrake;
        //leftbrake.set(brake ? Constants.LEFT_BRAKES_ON : Constants.LEFT_BRAKES_OFF);
        //rightbrake.set(brake ? Constants.RIGHT_BRAKES_ON : Constants.RIGHT_BRAKES_OFF);
        setSpeed(0);
    }

    public boolean brake() {
        return brake;
    }

    	/**
	 * Have the robot drive to a specific distance at a specified speed. 
	 * Uses the sign of distance, not the speed
	 * 
	 * @param distance
	 *            - The distance (in inches) you would like the robot to drive
	 *            to.
	 * @param speed
	 *            - The speed at which you would like the robot to drive.
	 * @return True, when complete.
	 */
    /*
    public boolean driveStraight(double distance, double speed, boolean slowDown) {
		if (!m_drivingStep) {
			brakesOff();
			resetSensors();
			m_drivingStep = true;
		} else {
			speed = Math.copySign(speed, distance);
			double leftSpeed = speed;
			double rightSpeed = speed;
			if (Math.abs(getAverageEncoderDistance() - distance) < 
					// Keeping expected and observed values 
					// within the same absolute value
					// because if they differ in sign, 
					// that's more than a coding problem
					Constants.DRIVE_STRAIGHT_DISTANCE_TOLERANCE) {
				setSpeed(0.0);
				m_drivingStep = false;
			} 
			else if (slowDown) { // If within slow range, set to slow speed
				if (getAverageEncoderDistance() 
						>= distance - Constants.DRIVE_STRAIGHT_SLOW_RANGE)
					setToSlowSpeed(speed > 0.0);
				else if (getAverageEncoderDistance() 
						>= distance + Constants.DRIVE_STRAIGHT_DISTANCE_TOLERANCE)
					setToSlowSpeed(speed < 0.0);
			}
			 else if (Math.abs(getGyroAngle()) 
					 > Constants.VEER_TOLERANCE) {
					// Adjust speeds for in case of veering
					if (getGyroAngle() > 0) { // Too far clockwise
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
		}
		return (!m_drivingStep);
    }
    */


    /**
	 * Have the robot turn to a specific angle at a specified speed.
	 * Uses the sign of the angle
	 * 
	 * @param angle
	 *            - The angle (in degrees) you would like to turn the robot to.
	 * @param speed
	 *            - The speed at which you would like the robot to turn.
	 * @return Whether the turn has completed
	 */
    /*public boolean turn(double angle, double speed) {
		if (!drivingStep) {
			brake(false);
			resetSensors();
			drivingStep = true;
		} else {
			speed = Math.copySign(speed,angle);
			setSpeed(speed, -speed);
			if (Math.abs( getGyroAngle() - angle ) <  Constants.TURN_TOLERANCE) {
				drivingStep = false;
				setSpeed(0.0);
			}
		}

		return (!drivingStep);
    }*/
    
    /*public void resetSensors() {
		// Put all resets for sensors in the drivebase here.
		yaw = navX.getAngle();

	}*/

}
